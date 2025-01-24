import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class RecursiveDescentGenerator {
    private final List<NonTerminal> nonterminals;
    private final BufferedWriter out;
    private final BiFunction<NonTerminal.Branch, String, Set<String>> first1;

    public RecursiveDescentGenerator(List<NonTerminal> nonterminals,
                                     BufferedWriter out,
                                     BiFunction<NonTerminal.Branch, String, Set<String>> first1) {
        this.nonterminals = nonterminals;
        this.out = out;
        this.first1 = first1;
    }

    public void generate() throws IOException {
        for (var r : nonterminals) {
            visitRule(r);
        }
    }

    // NOTE: synthesizable attributes types must be default-constructible
    // this can be avoided with additional complexity and restrictions on SYNTH_CODE
    public void visitRule(NonTerminal rule) throws IOException {
        out.write(String.format("  struct %s : node {\n", rule.name));
        out.write(String.format("    %s() : node(\"%s\") {};\n", rule.name, rule.name));
        emplace_code(rule.synthAttrs, 2);
        out.write("  };\n");
        out.write(String.format("  %s %s(%s) {\n", rule.name, rule.name, rule.inhAttrs));
        out.write(String.format("    auto _res = (struct %s){};\n", rule.name));
        out.write(             ("    switch (lexer.cur_token()) {"));
        assert(!rule.branches.isEmpty());
        for (var br : rule.branches) {
            visitBranch(br, rule.name);
        }
        out.write("\n    default:\n");
        out.write(String.format("      throw invalid_token(\"%s\");\n", rule.name));
        out.write("    }\n    return _res;\n  }\n\n");
    }

    private void visitBranch(NonTerminal.Branch br, String ruleName) throws IOException {
        for (var cas : first1.apply(br, ruleName)) {
            out.write(String.format("\n    case token::%s:", cas));
        }
        out.write(" {\n");
        if (br == null) { // eps
            out.write("      _res.empty = true;\n      break;\n    }");
            return;
        }
        assert(br.symbs != null);
        assert(!br.symbs.isEmpty());
        for (int i = 0; i < br.symbs.size(); i++) {
            var s = br.symbs.get(i);
            var cn = String.format("_%d", i + 1);
            if (isTerminal(s)) {
                out.write(String.format("      auto %s = lexer.cur_token_val();\n", cn));
            } else {
                out.write(String.format("      auto %s = %s(%s);\n", cn, s.name, interpolate(s.inhAttrsCall)));
            }
            out.write(String.format("      _res.children.emplace_back(%s);\n", cn));
            if (isTerminal(s)) {
                out.write("      lexer.next_token();\n");
            }
        }
        emplace_code(br.synthCode, 3);
        out.write("      break;\n    }");
    }

    private void emplace_code(String code, int tabs) throws IOException {
        if (code.isEmpty()) {
            return;
        }
        var lines = code.split("\\s*;\\s*");
        for (var l : lines) {
            out.write("  ".repeat(tabs) + interpolate(l) + ";\n");
        }
    }

    private String interpolate(String code) {
        return code.replace('$', '_'); // slightly incorrect ('\$' ignored)
    }


    private boolean isTerminal(NonTerminal.Branch.Symb symb) {
        return 'A' <= symb.name.charAt(0) && symb.name.charAt(0) <= 'Z';
    }
}
