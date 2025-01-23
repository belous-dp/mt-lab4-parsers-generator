import generated.GrammarBaseVisitor;
import generated.GrammarParser;
import generated.GrammarVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class CollectRulesVisitor extends GrammarBaseVisitor<Void> implements GrammarVisitor<Void> {
    private final List<Terminal> terminals = new ArrayList<>();
    private final List<NonTerminal> nonterminals = new ArrayList<>();

    private String ctxToText(ParseTree ctx) {
        return ctx == null ? null : ctx.getText();
    }

    @Override public Void visitRule_(GrammarParser.Rule_Context ctx) {
        nonterminals.add(
                new NonTerminal(
                        ctx.RULE_NAME().getText(),
                        ctxToText(ctx.inhAttrs()),
                        ctx.synthAttrs() == null ? null : ctx.synthAttrs().ATTRS().getText()));
        return visitChildren(ctx);
    }

    @Override public Void visitEpsBranch(GrammarParser.EpsBranchContext ctx) {
        nonterminals.getLast().branches.add(null);
        return null;
    }
    @Override public Void visitNonEpsBranch(GrammarParser.NonEpsBranchContext ctx) {
        nonterminals.getLast().branches.add(
                new NonTerminal.Branch(
                        ctxToText(ctx.SYNTH_CODE())));
        return visitChildren(ctx);
    }

    @Override public Void visitSymbr(GrammarParser.SymbrContext ctx) {
        nonterminals.getLast().branches.getLast().symbs.add(
                new NonTerminal.Branch.Symb(
                        ctx.RULE_NAME().getText(),
                        ctxToText(ctx.inhAttrs())));
        return null;
    }
    @Override public Void visitSymbt(GrammarParser.SymbtContext ctx) {
        nonterminals.getLast().branches.getLast().symbs.add(
                new NonTerminal.Branch.Symb(
                        ctx.TOKEN_NAME().getText(),
                        null));
        return null;
    }

    @Override public Void visitToken(GrammarParser.TokenContext ctx) {
        terminals.add(new Terminal(ctx.TOKEN_NAME().getText(), ctx.tokenValue().getText(), ctx.regex() != null));
        return null;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }
    public List<NonTerminal> getNonterminals() {
        return nonterminals;
    }
}
