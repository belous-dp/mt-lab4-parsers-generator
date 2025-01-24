import generated.GrammarLexer;
import generated.GrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ParserGenerator {
    private final Path grammarPath, outDir;
    private final Map<String, Set<String>> FIRST = new HashMap<>();
    private final Map<String, Set<String>> FOLLOW = new HashMap<>();
    private BufferedWriter out;

    ParserGenerator(String grammarPath, String outDir) throws FileNotFoundException {
        this.grammarPath = Path.of(grammarPath);
        if (!Files.exists(this.grammarPath)) {
            throw new FileNotFoundException("Grammar file not found: " + grammarPath);
        }
        this.outDir = Path.of(outDir);
    }

    public void run() throws IOException {
        var inpParser = new GrammarParser(
                new CommonTokenStream(new GrammarLexer(CharStreams.fromPath(grammarPath)))
        );
        var initVisitor = new CollectRulesVisitor();
        initVisitor.visit(inpParser.lang());

        if (!checkLL1(initVisitor.getNonterminals())) {
            throw new InvalidGrammarException("grammar is not an LL(1)");
        }

        initializeOutputStreams();
        try {
            includeHeaders();
            createTokensEnum(initVisitor.getTerminals());
            createLexer(initVisitor.getTerminals());
            createParser(initVisitor.getNonterminals());
        } finally {
            out.close();
        }
    }

    private void initializeOutputStreams() throws IOException {
        Files.createDirectories(outDir);
        var justCopy = List.of("CMakeLists.txt", "main.cpp", "tree.hpp");
        for (var fn : justCopy) {
            var src = getClass().getResourceAsStream(fn);
            if (src == null) {
                throw new FileNotFoundException("unable to find resource file " + fn);
            }
            Files.copy(src, outDir.resolve(fn), REPLACE_EXISTING);
        }
        out = Files.newBufferedWriter(outDir.resolve("parser.hpp"));
    }

    private void includeHeaders() throws IOException {
        out.write(
                """
                #pragma once
                
                #include "tree.hpp"
                
                #include <cctype>
                #include <format>
                #include <istream>
                #include <stdexcept>
                #include <string>
                #include <regex>
                #include <vector>
                
                using namespace std::string_literals;
                
                """);
    }

    private void createTokensEnum(List<Terminal> terminals) throws IOException {
        out.write("enum class token { ");
        out.write(terminals.stream().map(Terminal::name).collect(Collectors.joining(", ")));
        out.write(", _END };\n\n");
    }

    private void createLexer(List<Terminal> terminals) throws IOException {
        out.write("""
                struct lexer_exception : std::runtime_error {
                  using std::runtime_error::runtime_error;
                };
                
                struct lexer {
                  static constexpr size_t MAX_BUF_SIZE = 10000;
                
                  lexer(std::istream& is) : is(is) {
                    if (!is) {
                      throw lexer_exception{"stream is not opened"};
                    }
                    next_char();
                    buf = ch;
                  }
                
                  token cur_token() const noexcept {
                    return t;
                  }
                
                  std::string cur_token_val() const noexcept {
                    return lexeme;
                  }
                
                  std::string info() const noexcept {
                    return "'" + buf + "' at pos " + std::to_string(pos - 1);
                  }
                
                  token next_token() {
                    while (is.good() && std::isspace(static_cast<unsigned char>(ch))) {
                      next_char();
                    }
                    if (is.eof()) {
                      t = token::_END;
                      return t;
                    }
                    if (!is) {
                      throw lexer_exception{"the stream is corrupted or closed unexpectedly "
                                            "(pos=" + std::to_string(pos) + ')'};
                    }
                """);
        var hasRegexp = terminals.stream().anyMatch(Terminal::isRegex);
        if (hasRegexp) {
            out.write("    auto match = std::smatch{};\n");
        }
        out.write("    do {\n      buf += ch;\n");
        for (var tok : terminals) {
            out.write("      if (");
            if (tok.isRegex()) {
                out.write("std::regex_match(buf, match, r" + tok.name() + ")");
            } else {
                out.write("buf == \"" + tok.value() + "\"");
            }
            out.write(") {\n        t = token::" + tok.name() + ";\n        break;\n      }\n");
        }
        out.write("""
                      next_char();
                      if (is.good() && std::isspace(static_cast<unsigned char>(ch))) {
                        throw lexer_exception{"expected token, got space at pos " + std::to_string(pos)};
                      }
                      if (is.eof()) {
                        throw lexer_exception{"unknown lexeme " + info()};
                      }
                    } while (buf.size() < MAX_BUF_SIZE);
                    if (buf.size() >= MAX_BUF_SIZE) {
                      throw lexer_exception{"unmatched lexeme size exceeded " + std::to_string(MAX_BUF_SIZE)};
                    }
                    lexeme = std::move(buf);
                    buf = "";
                    next_char();
                    return t;
                  }
                
                private:
                  void next_char() {
                    if (is.get(ch)) {
                      pos++;
                    }
                  }
                
                """);
        for (var tok : terminals) {
            if (tok.isRegex()) {
                out.write("  const std::regex r" + tok.name() + "{\"" + tok.value() + "\"};\n");
                // NOTE: the field can be static, but that would require less elegant code
            }
        }
        out.write("""
                
                  std::istream& is;
                  std::string lexeme;
                  std::string buf;
                  size_t pos{0};
                  token t;
                  char ch;
                };
                
                """);
    }

    private void createParser(List<NonTerminal> nonterminals) throws IOException {
        out.write("""
                struct parser_exception : std::runtime_error {
                  using std::runtime_error::runtime_error;
                };
                
                class parser {
                  lexer lexer;
                
                """);
        new RecursiveDescentGenerator(nonterminals, out, this::first1).generate();
        out.write("""
                public:
                  parser(std::istream& is) : lexer(is) {}
                
                  node parse() {
                    lexer.next_token();
                """);
        out.write("    return " + nonterminals.getFirst().name + "();\n  }\n};\n");
    }

    private boolean checkLL1(List<NonTerminal> nonterminalRules) {
        buildFirstFollow(nonterminalRules);
        for (var nt : nonterminalRules) {
            var A = nt.name;
            for (int i = 0; i < nt.branches.size(); i++) {
                var a = nt.branches.get(i);
                for (int j = 0; j < nt.branches.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    var b = nt.branches.get(j);
                    var first1a = first1(a, A);
                    var firstb = first(b);
                    if (first1a.stream().anyMatch(firstb::contains)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private void buildFirstFollow(List<NonTerminal> nonterminals) {
        nonterminals.forEach(nt -> FIRST.put(nt.name, new HashSet<>()));

        var changed = true;
        while (changed) {
            changed = false;
            for (var nt : nonterminals) {
                var set = FIRST.get(nt.name);
                for (var br : nt.branches) {
                    changed |= set.addAll(first(br));
                }
            }
        }

        nonterminals.forEach(nt -> FOLLOW.put(nt.name, new HashSet<>()));
        FOLLOW.get(nonterminals.getFirst().name).add("_END"); // '$'/EOF
        changed = true;
        while (changed) {
            changed = false;
            for (var nt : nonterminals) {
                var A = nt.name;
                for (var br : nt.branches) {
                    if (br != null) {
                        var brs = br.symbs;
                        assert(brs != null);
                        for (int i = 0; i < brs.size(); i++) {
                            var B = brs.get(i);
                            if (!isTerminal(B)) {
                                changed |= FOLLOW.get(B.name).addAll(first1(brs.subList(i + 1, brs.size()), A));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isTerminal(NonTerminal.Branch.Symb symb) {
        return 'A' <= symb.name.charAt(0) && symb.name.charAt(0) <= 'Z';
    }

    private Set<String> first(NonTerminal.Branch alpha) {
        return alpha == null ? first((List<NonTerminal.Branch.Symb>) null) : first(alpha.symbs);
    }

    private Set<String> first(List<NonTerminal.Branch.Symb> alpha) {
        var res = new HashSet<String>();
        if (alpha == null || alpha.isEmpty()) {
            res.add(null);
        } else {
            var e = alpha.getFirst();
            if (isTerminal(e)) {
                res.add(e.name);
            } else {
                var eps = false;
                for (var a : FIRST.get(e.name)) {
                    if (a == null) {
                        eps = true;
                    } else {
                        res.add(a);
                    }
                }
                if (eps) {
                    res.addAll(first(alpha.subList(1, alpha.size())));
                }
            }
        }
        return res;
    }

    private Set<String> first1(NonTerminal.Branch alpha, String A) {
        return alpha == null ? first1((List<NonTerminal.Branch.Symb>) null, A) : first1(alpha.symbs, A);
    }

    private Set<String> first1(List<NonTerminal.Branch.Symb> alpha, String A) {
        var res = first(alpha);
        if (res.contains(null)) {
            res.remove(null);
            res.addAll(FOLLOW.get(A));
        }
        return res;
    }

}
