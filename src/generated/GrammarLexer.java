// Generated from U:/5th semester/TM/lab4/src/Grammar.g4 by ANTLR 4.13.2
package generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ESCQ=6, ESQS=7, ATTRS=8, SYNTH_CODE=9, 
		RULE_NAME=10, TOKEN_NAME=11, WHITESPACE=12, RESTQ=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "ESCQ", "ESQS", "ATTRS", "SYNTH_CODE", 
			"RULE_NAME", "TOKEN_NAME", "WHITESPACE", "RESTQ"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\rR\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0005\u0007/\b\u0007\n\u0007"+
		"\f\u00072\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0005\b8\b\b"+
		"\n\b\f\b;\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0005\tA\b\t\n\t\f\tD\t"+
		"\t\u0001\n\u0001\n\u0005\nH\b\n\n\n\f\nK\t\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u000209\u0000\r\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u0001\u0000\u0006\u0001\u0000az\u0002\u0000"+
		"__az\u0001\u0000AZ\u0002\u0000AZ__\u0003\u0000\t\n\r\r  \u0001\u0000\""+
		"\"U\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000"+
		"\u0019\u0001\u0000\u0000\u0000\u0001\u001b\u0001\u0000\u0000\u0000\u0003"+
		"\u001d\u0001\u0000\u0000\u0000\u0005\u001f\u0001\u0000\u0000\u0000\u0007"+
		"\"\u0001\u0000\u0000\u0000\t$\u0001\u0000\u0000\u0000\u000b&\u0001\u0000"+
		"\u0000\u0000\r)\u0001\u0000\u0000\u0000\u000f,\u0001\u0000\u0000\u0000"+
		"\u00115\u0001\u0000\u0000\u0000\u0013>\u0001\u0000\u0000\u0000\u0015E"+
		"\u0001\u0000\u0000\u0000\u0017L\u0001\u0000\u0000\u0000\u0019P\u0001\u0000"+
		"\u0000\u0000\u001b\u001c\u0005:\u0000\u0000\u001c\u0002\u0001\u0000\u0000"+
		"\u0000\u001d\u001e\u0005|\u0000\u0000\u001e\u0004\u0001\u0000\u0000\u0000"+
		"\u001f \u0005-\u0000\u0000 !\u0005>\u0000\u0000!\u0006\u0001\u0000\u0000"+
		"\u0000\"#\u0005\u03b5\u0000\u0000#\b\u0001\u0000\u0000\u0000$%\u0005\""+
		"\u0000\u0000%\n\u0001\u0000\u0000\u0000&\'\u0005\\\u0000\u0000\'(\u0005"+
		"\"\u0000\u0000(\f\u0001\u0000\u0000\u0000)*\u0005\\\u0000\u0000*+\u0005"+
		" \u0000\u0000+\u000e\u0001\u0000\u0000\u0000,0\u0005[\u0000\u0000-/\t"+
		"\u0000\u0000\u0000.-\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u0000"+
		"01\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u000013\u0001\u0000\u0000"+
		"\u000020\u0001\u0000\u0000\u000034\u0005]\u0000\u00004\u0010\u0001\u0000"+
		"\u0000\u000059\u0005{\u0000\u000068\t\u0000\u0000\u000076\u0001\u0000"+
		"\u0000\u00008;\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u000097\u0001"+
		"\u0000\u0000\u0000:<\u0001\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000"+
		"<=\u0005}\u0000\u0000=\u0012\u0001\u0000\u0000\u0000>B\u0007\u0000\u0000"+
		"\u0000?A\u0007\u0001\u0000\u0000@?\u0001\u0000\u0000\u0000AD\u0001\u0000"+
		"\u0000\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000C\u0014"+
		"\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000EI\u0007\u0002\u0000"+
		"\u0000FH\u0007\u0003\u0000\u0000GF\u0001\u0000\u0000\u0000HK\u0001\u0000"+
		"\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J\u0016"+
		"\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000LM\u0007\u0004\u0000"+
		"\u0000MN\u0001\u0000\u0000\u0000NO\u0006\u000b\u0000\u0000O\u0018\u0001"+
		"\u0000\u0000\u0000PQ\b\u0005\u0000\u0000Q\u001a\u0001\u0000\u0000\u0000"+
		"\u0005\u000009BI\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}