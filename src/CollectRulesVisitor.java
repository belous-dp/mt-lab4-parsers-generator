import generated.GrammarBaseVisitor;
import generated.GrammarParser;
import generated.GrammarVisitor;

import java.util.ArrayList;
import java.util.List;

public class CollectRulesVisitor extends GrammarBaseVisitor<Void> implements GrammarVisitor<Void> {
    private final List<Terminal> terminals = new ArrayList<>();
    private final List<NonTerminal> nonterminals = new ArrayList<>();

    public record Terminal(
        String name,
        String value
    ) {}
    public class NonTerminal {
        String name;
        List<ArrayList<String>> branches = new ArrayList<>();

        NonTerminal(String name) { this.name = name; }
    }

    @Override public Void visitRule_(GrammarParser.Rule_Context ctx) {
        nonterminals.add(new NonTerminal(ctx.RULE_NAME().getText()));
        return visitChildren(ctx);
    }

    @Override public Void visitEpsBranch(GrammarParser.EpsBranchContext ctx) {
        nonterminals.getLast().branches.add(null);
        return null;
    }
    @Override public Void visitNonEpsBranch(GrammarParser.NonEpsBranchContext ctx) {
        nonterminals.getLast().branches.add(new ArrayList<>());
        return visitChildren(ctx);
    }

    @Override public Void visitSymbr(GrammarParser.SymbrContext ctx) {
        nonterminals.getLast().branches.getLast().add(ctx.RULE_NAME().getText());
        return null;
    }
    @Override public Void visitSymbt(GrammarParser.SymbtContext ctx) {
        nonterminals.getLast().branches.getLast().add(ctx.TOKEN_NAME().getText());
        return null;
    }

    @Override public Void visitToken(GrammarParser.TokenContext ctx) {
        terminals.add(new Terminal(ctx.TOKEN_NAME().getText(), ctx.tokenValue().getText()));
        return null;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }
    public List<NonTerminal> getNonterminals() {
        return nonterminals;
    }
}
