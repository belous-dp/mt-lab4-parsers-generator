// Generated from U:/5th semester/TM/lab4/src/Grammar.g4 by ANTLR 4.13.2
package generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#lang}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLang(GrammarParser.LangContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#rule_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRule_(GrammarParser.Rule_Context ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#inhAttrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInhAttrs(GrammarParser.InhAttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#synthAttrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSynthAttrs(GrammarParser.SynthAttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#branch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranch(GrammarParser.BranchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code symbr}
	 * labeled alternative in {@link GrammarParser#symb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbr(GrammarParser.SymbrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code symbt}
	 * labeled alternative in {@link GrammarParser#symb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbt(GrammarParser.SymbtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(GrammarParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#regex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegex(GrammarParser.RegexContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#tokenValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTokenValue(GrammarParser.TokenValueContext ctx);
}