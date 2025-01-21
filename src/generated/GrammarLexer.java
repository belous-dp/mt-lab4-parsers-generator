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
		T__0=1, T__1=2, T__2=3, T__3=4, ESCQ=5, ESQS=6, ATTRS=7, SYNTH_CODE=8, 
		RULE_NAME=9, TOKEN_NAME=10, WHITESPACE=11, EPS=12, RESTQ=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "ESCQ", "ESQS", "ATTRS", "SYNTH_CODE", 
			"RULE_NAME", "TOKEN_NAME", "WHITESPACE", "EPS", "RESTQ"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'|'", "'->'", "'\"'", "'\\\"'", "'\\ '", null, null, null, 
			null, null, "'\\u03B5'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "ESCQ", "ESQS", "ATTRS", "SYNTH_CODE", 
			"RULE_NAME", "TOKEN_NAME", "WHITESPACE", "EPS", "RESTQ"
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
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0005\u0006-\b\u0006\n\u0006\f\u00060\t\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0005\u00076\b\u0007\n\u0007"+
		"\f\u00079\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0005\b?\b\b"+
		"\n\b\f\bB\t\b\u0001\t\u0001\t\u0005\tF\b\t\n\t\f\tI\t\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0002.7\u0000"+
		"\r\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u0001\u0000"+
		"\u0006\u0001\u0000az\u0002\u0000__az\u0001\u0000AZ\u0002\u0000AZ__\u0003"+
		"\u0000\t\n\r\r  \u0001\u0000\"\"U\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0001\u001b"+
		"\u0001\u0000\u0000\u0000\u0003\u001d\u0001\u0000\u0000\u0000\u0005\u001f"+
		"\u0001\u0000\u0000\u0000\u0007\"\u0001\u0000\u0000\u0000\t$\u0001\u0000"+
		"\u0000\u0000\u000b\'\u0001\u0000\u0000\u0000\r*\u0001\u0000\u0000\u0000"+
		"\u000f3\u0001\u0000\u0000\u0000\u0011<\u0001\u0000\u0000\u0000\u0013C"+
		"\u0001\u0000\u0000\u0000\u0015J\u0001\u0000\u0000\u0000\u0017N\u0001\u0000"+
		"\u0000\u0000\u0019P\u0001\u0000\u0000\u0000\u001b\u001c\u0005:\u0000\u0000"+
		"\u001c\u0002\u0001\u0000\u0000\u0000\u001d\u001e\u0005|\u0000\u0000\u001e"+
		"\u0004\u0001\u0000\u0000\u0000\u001f \u0005-\u0000\u0000 !\u0005>\u0000"+
		"\u0000!\u0006\u0001\u0000\u0000\u0000\"#\u0005\"\u0000\u0000#\b\u0001"+
		"\u0000\u0000\u0000$%\u0005\\\u0000\u0000%&\u0005\"\u0000\u0000&\n\u0001"+
		"\u0000\u0000\u0000\'(\u0005\\\u0000\u0000()\u0005 \u0000\u0000)\f\u0001"+
		"\u0000\u0000\u0000*.\u0005[\u0000\u0000+-\t\u0000\u0000\u0000,+\u0001"+
		"\u0000\u0000\u0000-0\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000"+
		".,\u0001\u0000\u0000\u0000/1\u0001\u0000\u0000\u00000.\u0001\u0000\u0000"+
		"\u000012\u0005]\u0000\u00002\u000e\u0001\u0000\u0000\u000037\u0005{\u0000"+
		"\u000046\t\u0000\u0000\u000054\u0001\u0000\u0000\u000069\u0001\u0000\u0000"+
		"\u000078\u0001\u0000\u0000\u000075\u0001\u0000\u0000\u00008:\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u0000:;\u0005}\u0000\u0000;\u0010\u0001"+
		"\u0000\u0000\u0000<@\u0007\u0000\u0000\u0000=?\u0007\u0001\u0000\u0000"+
		">=\u0001\u0000\u0000\u0000?B\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000"+
		"\u0000@A\u0001\u0000\u0000\u0000A\u0012\u0001\u0000\u0000\u0000B@\u0001"+
		"\u0000\u0000\u0000CG\u0007\u0002\u0000\u0000DF\u0007\u0003\u0000\u0000"+
		"ED\u0001\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000"+
		"\u0000GH\u0001\u0000\u0000\u0000H\u0014\u0001\u0000\u0000\u0000IG\u0001"+
		"\u0000\u0000\u0000JK\u0007\u0004\u0000\u0000KL\u0001\u0000\u0000\u0000"+
		"LM\u0006\n\u0000\u0000M\u0016\u0001\u0000\u0000\u0000NO\u0005\u03b5\u0000"+
		"\u0000O\u0018\u0001\u0000\u0000\u0000PQ\b\u0005\u0000\u0000Q\u001a\u0001"+
		"\u0000\u0000\u0000\u0005\u0000.7@G\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}