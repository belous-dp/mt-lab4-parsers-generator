// Generated from U:/5th semester/TM/lab4/src/Grammar.g4 by ANTLR 4.13.2
package generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ESCQ=6, ESQS=7, ATTRS=8, SYNTH_CODE=9, 
		RULE_NAME=10, TOKEN_NAME=11, WHITESPACE=12, RESTQ=13;
	public static final int
		RULE_lang = 0, RULE_rule_ = 1, RULE_inhAttrs = 2, RULE_synthAttrs = 3, 
		RULE_branch = 4, RULE_symb = 5, RULE_token = 6, RULE_tokenValue = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"lang", "rule_", "inhAttrs", "synthAttrs", "branch", "symb", "token", 
			"tokenValue"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'|'", "'->'", "'\\u03B5'", "'\"'", "'\\\"'", "'\\ '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "ESCQ", "ESQS", "ATTRS", "SYNTH_CODE", 
			"RULE_NAME", "TOKEN_NAME", "WHITESPACE", "RESTQ"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LangContext extends ParserRuleContext {
		public List<Rule_Context> rule_() {
			return getRuleContexts(Rule_Context.class);
		}
		public Rule_Context rule_(int i) {
			return getRuleContext(Rule_Context.class,i);
		}
		public List<TokenContext> token() {
			return getRuleContexts(TokenContext.class);
		}
		public TokenContext token(int i) {
			return getRuleContext(TokenContext.class,i);
		}
		public LangContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lang; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitLang(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LangContext lang() throws RecognitionException {
		LangContext _localctx = new LangContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_lang);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(16);
				rule_();
				}
				}
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==RULE_NAME );
			setState(22); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(21);
				token();
				}
				}
				setState(24); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TOKEN_NAME );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Rule_Context extends ParserRuleContext {
		public TerminalNode RULE_NAME() { return getToken(GrammarParser.RULE_NAME, 0); }
		public List<BranchContext> branch() {
			return getRuleContexts(BranchContext.class);
		}
		public BranchContext branch(int i) {
			return getRuleContext(BranchContext.class,i);
		}
		public InhAttrsContext inhAttrs() {
			return getRuleContext(InhAttrsContext.class,0);
		}
		public SynthAttrsContext synthAttrs() {
			return getRuleContext(SynthAttrsContext.class,0);
		}
		public Rule_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rule_; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitRule_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rule_Context rule_() throws RecognitionException {
		Rule_Context _localctx = new Rule_Context(_ctx, getState());
		enterRule(_localctx, 2, RULE_rule_);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(RULE_NAME);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ATTRS) {
				{
				setState(27);
				inhAttrs();
				}
			}

			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(30);
				synthAttrs();
				}
			}

			setState(33);
			match(T__0);
			setState(34);
			branch();
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(35);
				match(T__1);
				setState(36);
				branch();
				}
				}
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InhAttrsContext extends ParserRuleContext {
		public TerminalNode ATTRS() { return getToken(GrammarParser.ATTRS, 0); }
		public InhAttrsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inhAttrs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitInhAttrs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InhAttrsContext inhAttrs() throws RecognitionException {
		InhAttrsContext _localctx = new InhAttrsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_inhAttrs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(ATTRS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SynthAttrsContext extends ParserRuleContext {
		public TerminalNode ATTRS() { return getToken(GrammarParser.ATTRS, 0); }
		public SynthAttrsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_synthAttrs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitSynthAttrs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SynthAttrsContext synthAttrs() throws RecognitionException {
		SynthAttrsContext _localctx = new SynthAttrsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_synthAttrs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(T__2);
			setState(45);
			match(ATTRS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BranchContext extends ParserRuleContext {
		public BranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_branch; }
	 
		public BranchContext() { }
		public void copyFrom(BranchContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EpsBranchContext extends BranchContext {
		public EpsBranchContext(BranchContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitEpsBranch(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NonEpsBranchContext extends BranchContext {
		public List<SymbContext> symb() {
			return getRuleContexts(SymbContext.class);
		}
		public SymbContext symb(int i) {
			return getRuleContext(SymbContext.class,i);
		}
		public TerminalNode SYNTH_CODE() { return getToken(GrammarParser.SYNTH_CODE, 0); }
		public NonEpsBranchContext(BranchContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitNonEpsBranch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BranchContext branch() throws RecognitionException {
		BranchContext _localctx = new BranchContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_branch);
		int _la;
		try {
			int _alt;
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				_localctx = new EpsBranchContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				match(T__3);
				}
				break;
			case RULE_NAME:
			case TOKEN_NAME:
				_localctx = new NonEpsBranchContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(49); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(48);
						symb();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(51); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SYNTH_CODE) {
					{
					setState(53);
					match(SYNTH_CODE);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SymbContext extends ParserRuleContext {
		public SymbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symb; }
	 
		public SymbContext() { }
		public void copyFrom(SymbContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SymbtContext extends SymbContext {
		public TerminalNode TOKEN_NAME() { return getToken(GrammarParser.TOKEN_NAME, 0); }
		public SymbtContext(SymbContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitSymbt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SymbrContext extends SymbContext {
		public TerminalNode RULE_NAME() { return getToken(GrammarParser.RULE_NAME, 0); }
		public InhAttrsContext inhAttrs() {
			return getRuleContext(InhAttrsContext.class,0);
		}
		public SymbrContext(SymbContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitSymbr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SymbContext symb() throws RecognitionException {
		SymbContext _localctx = new SymbContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_symb);
		int _la;
		try {
			setState(63);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RULE_NAME:
				_localctx = new SymbrContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				match(RULE_NAME);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ATTRS) {
					{
					setState(59);
					inhAttrs();
					}
				}

				}
				break;
			case TOKEN_NAME:
				_localctx = new SymbtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(TOKEN_NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TokenContext extends ParserRuleContext {
		public TerminalNode TOKEN_NAME() { return getToken(GrammarParser.TOKEN_NAME, 0); }
		public TokenValueContext tokenValue() {
			return getRuleContext(TokenValueContext.class,0);
		}
		public TokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitToken(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TokenContext token() throws RecognitionException {
		TokenContext _localctx = new TokenContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_token);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(TOKEN_NAME);
			setState(66);
			match(T__0);
			setState(67);
			match(T__4);
			setState(68);
			tokenValue();
			setState(69);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TokenValueContext extends ParserRuleContext {
		public List<TerminalNode> ESCQ() { return getTokens(GrammarParser.ESCQ); }
		public TerminalNode ESCQ(int i) {
			return getToken(GrammarParser.ESCQ, i);
		}
		public List<TerminalNode> ESQS() { return getTokens(GrammarParser.ESQS); }
		public TerminalNode ESQS(int i) {
			return getToken(GrammarParser.ESQS, i);
		}
		public List<TerminalNode> ATTRS() { return getTokens(GrammarParser.ATTRS); }
		public TerminalNode ATTRS(int i) {
			return getToken(GrammarParser.ATTRS, i);
		}
		public List<TerminalNode> SYNTH_CODE() { return getTokens(GrammarParser.SYNTH_CODE); }
		public TerminalNode SYNTH_CODE(int i) {
			return getToken(GrammarParser.SYNTH_CODE, i);
		}
		public List<TerminalNode> RULE_NAME() { return getTokens(GrammarParser.RULE_NAME); }
		public TerminalNode RULE_NAME(int i) {
			return getToken(GrammarParser.RULE_NAME, i);
		}
		public List<TerminalNode> TOKEN_NAME() { return getTokens(GrammarParser.TOKEN_NAME); }
		public TerminalNode TOKEN_NAME(int i) {
			return getToken(GrammarParser.TOKEN_NAME, i);
		}
		public List<TerminalNode> RESTQ() { return getTokens(GrammarParser.RESTQ); }
		public TerminalNode RESTQ(int i) {
			return getToken(GrammarParser.RESTQ, i);
		}
		public TokenValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tokenValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTokenValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TokenValueContext tokenValue() throws RecognitionException {
		TokenValueContext _localctx = new TokenValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tokenValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(71);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 12224L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(74); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 12224L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\rM\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0004\u0000\u0012\b\u0000\u000b\u0000\f\u0000\u0013\u0001\u0000"+
		"\u0004\u0000\u0017\b\u0000\u000b\u0000\f\u0000\u0018\u0001\u0001\u0001"+
		"\u0001\u0003\u0001\u001d\b\u0001\u0001\u0001\u0003\u0001 \b\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001&\b\u0001\n\u0001"+
		"\f\u0001)\t\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0004\u00042\b\u0004\u000b\u0004\f\u0004"+
		"3\u0001\u0004\u0003\u00047\b\u0004\u0003\u00049\b\u0004\u0001\u0005\u0001"+
		"\u0005\u0003\u0005=\b\u0005\u0001\u0005\u0003\u0005@\b\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0004\u0007I\b\u0007\u000b\u0007\f\u0007J\u0001\u0007\u0000\u0000\b\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0000\u0001\u0002\u0000\u0006\u000b\r\r"+
		"O\u0000\u0011\u0001\u0000\u0000\u0000\u0002\u001a\u0001\u0000\u0000\u0000"+
		"\u0004*\u0001\u0000\u0000\u0000\u0006,\u0001\u0000\u0000\u0000\b8\u0001"+
		"\u0000\u0000\u0000\n?\u0001\u0000\u0000\u0000\fA\u0001\u0000\u0000\u0000"+
		"\u000eH\u0001\u0000\u0000\u0000\u0010\u0012\u0003\u0002\u0001\u0000\u0011"+
		"\u0010\u0001\u0000\u0000\u0000\u0012\u0013\u0001\u0000\u0000\u0000\u0013"+
		"\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000\u0014"+
		"\u0016\u0001\u0000\u0000\u0000\u0015\u0017\u0003\f\u0006\u0000\u0016\u0015"+
		"\u0001\u0000\u0000\u0000\u0017\u0018\u0001\u0000\u0000\u0000\u0018\u0016"+
		"\u0001\u0000\u0000\u0000\u0018\u0019\u0001\u0000\u0000\u0000\u0019\u0001"+
		"\u0001\u0000\u0000\u0000\u001a\u001c\u0005\n\u0000\u0000\u001b\u001d\u0003"+
		"\u0004\u0002\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001c\u001d\u0001"+
		"\u0000\u0000\u0000\u001d\u001f\u0001\u0000\u0000\u0000\u001e \u0003\u0006"+
		"\u0003\u0000\u001f\u001e\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000"+
		"\u0000 !\u0001\u0000\u0000\u0000!\"\u0005\u0001\u0000\u0000\"\'\u0003"+
		"\b\u0004\u0000#$\u0005\u0002\u0000\u0000$&\u0003\b\u0004\u0000%#\u0001"+
		"\u0000\u0000\u0000&)\u0001\u0000\u0000\u0000\'%\u0001\u0000\u0000\u0000"+
		"\'(\u0001\u0000\u0000\u0000(\u0003\u0001\u0000\u0000\u0000)\'\u0001\u0000"+
		"\u0000\u0000*+\u0005\b\u0000\u0000+\u0005\u0001\u0000\u0000\u0000,-\u0005"+
		"\u0003\u0000\u0000-.\u0005\b\u0000\u0000.\u0007\u0001\u0000\u0000\u0000"+
		"/9\u0005\u0004\u0000\u000002\u0003\n\u0005\u000010\u0001\u0000\u0000\u0000"+
		"23\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000"+
		"\u000046\u0001\u0000\u0000\u000057\u0005\t\u0000\u000065\u0001\u0000\u0000"+
		"\u000067\u0001\u0000\u0000\u000079\u0001\u0000\u0000\u00008/\u0001\u0000"+
		"\u0000\u000081\u0001\u0000\u0000\u00009\t\u0001\u0000\u0000\u0000:<\u0005"+
		"\n\u0000\u0000;=\u0003\u0004\u0002\u0000<;\u0001\u0000\u0000\u0000<=\u0001"+
		"\u0000\u0000\u0000=@\u0001\u0000\u0000\u0000>@\u0005\u000b\u0000\u0000"+
		"?:\u0001\u0000\u0000\u0000?>\u0001\u0000\u0000\u0000@\u000b\u0001\u0000"+
		"\u0000\u0000AB\u0005\u000b\u0000\u0000BC\u0005\u0001\u0000\u0000CD\u0005"+
		"\u0005\u0000\u0000DE\u0003\u000e\u0007\u0000EF\u0005\u0005\u0000\u0000"+
		"F\r\u0001\u0000\u0000\u0000GI\u0007\u0000\u0000\u0000HG\u0001\u0000\u0000"+
		"\u0000IJ\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000"+
		"\u0000\u0000K\u000f\u0001\u0000\u0000\u0000\u000b\u0013\u0018\u001c\u001f"+
		"\'368<?J";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}