import generated.GrammarLexer;
import generated.GrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ParserGenerator {
    private final Path grammarPath, outDir;
    private final Map<String, String> terminals = new HashMap<>();
    private final Map<String, Set<String>> FIRST = new HashMap<>();
    private final Map<String, Set<String>> FOLLOW = new HashMap<>();

    ParserGenerator(String grammarPath, String outDir) throws FileNotFoundException {
        this.grammarPath = Path.of(grammarPath);
        if (!Files.exists(this.grammarPath)) {
            throw new FileNotFoundException("Grammar file not found: " + grammarPath);
        }
        this.outDir = Path.of(outDir);
        if (!Files.exists(this.outDir)) {
            throw new FileNotFoundException("Output directory not found: " + outDir);
        }
    }

    public void run() throws IOException {
        var inpParser = new GrammarParser(
                new CommonTokenStream(new GrammarLexer(CharStreams.fromPath(grammarPath)))
        );
        var initVisitor = new CollectRulesVisitor();
        initVisitor.visit(inpParser.lang());

        if (!checkLL1(initVisitor.getTerminals(), initVisitor.getNonterminals())) {
            throw new InvalidGrammarException("grammar is not an LL(1)");
        }

        createTokensEnum();
        createLexer();
        createParser();
    }

    private void createTokensEnum() {

    }

    private void createParser() {

    }

    private void createLexer() {

    }

    private boolean checkLL1(List<CollectRulesVisitor.Terminal> terminalRules,
                            List<CollectRulesVisitor.NonTerminal> nonterminalRules) {
        buildFirstFollow(terminalRules, nonterminalRules);
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


    private void buildFirstFollow(List<CollectRulesVisitor.Terminal> terminals,
                                  List<CollectRulesVisitor.NonTerminal> nonterminals) {
        terminals.forEach(t -> this.terminals.put(t.name(), t.value()));
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
        FOLLOW.get(nonterminals.getFirst().name).add(null); // '$'/EOF
        changed = true;
        while (changed) {
            changed = false;
            for (var nt : nonterminals) {
                var A = nt.name;
                for (var br : nt.branches) {
                    for (int i = 0; i < br.size(); i++) {
                        var B = br.get(i);
                        if (!isTerminal(B)) {
                            changed |= FOLLOW.get(B).addAll(first1(br.subList(i + 1, br.size()), A));
                        }
                    }
                }
            }
        }
    }

    private boolean isTerminal(String name) {
        return 'A' <= name.charAt(0) && name.charAt(0) <= 'Z';
    }

    private Set<String> first(List<String> alpha) {
        var res = new HashSet<String>();
        if (alpha == null || alpha.isEmpty()) {
            res.add(null);
        } else {
            for (var e : alpha) {
                if (isTerminal(e)) {
                    res.add(terminals.get(e));
                    break;
                } else {
                    var eps = false;
                    for (var a : FIRST.get(e)) {
                        if (a == null) {
                            eps = true;
                        } else {
                            res.add(a);
                        }
                    }
                    if (!eps) {
                        break;
                    }
                }
            }
        }
        return res;
    }

    private Set<String> first1(List<String> alpha, String A) {
        var res = first(alpha);
        if (res.contains(null)) {
            res.remove(null);
            res.addAll(FOLLOW.get(A));
        }
        return res;
    }

}
