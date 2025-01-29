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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, ESCQ=7, ESQS=8, ATTRS=9, 
		SYNTH_CODE=10, RULE_NAME=11, TOKEN_NAME=12, WHITESPACE=13, INCLUDES=14, 
		RESTQ=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "ESCQ", "ESQS", "ATTRS", 
			"SYNTH_CODE", "RULE_NAME", "TOKEN_NAME", "WHITESPACE", "INCLUDES", "RESTQ"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'|'", "'->'", "'\\u03B5'", "'\"'", "'r'", "'\\\"'", "'\\ '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "ESCQ", "ESQS", "ATTRS", "SYNTH_CODE", 
			"RULE_NAME", "TOKEN_NAME", "WHITESPACE", "INCLUDES", "RESTQ"
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
		"\u0004\u0000\u000ff\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0005\b5\b\b\n\b\f\b8\t\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0005\t>\b\t\n\t\f\tA\t\t\u0001\t\u0001\t\u0001\n\u0001\n\u0005"+
		"\nG\b\n\n\n\f\nJ\t\n\u0001\u000b\u0001\u000b\u0005\u000bN\b\u000b\n\u000b"+
		"\f\u000bQ\t\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0005\r\\\b\r\n\r\f\r_\t\r\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\u000e\u0001\u000e\u00036?]\u0000\u000f\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u0001\u0000"+
		"\u0006\u0001\u0000az\u0002\u0000__az\u0001\u0000AZ\u0002\u0000AZ__\u0003"+
		"\u0000\t\n\r\r  \u0001\u0000\"\"j\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b"+
		"\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0001\u001f"+
		"\u0001\u0000\u0000\u0000\u0003!\u0001\u0000\u0000\u0000\u0005#\u0001\u0000"+
		"\u0000\u0000\u0007&\u0001\u0000\u0000\u0000\t(\u0001\u0000\u0000\u0000"+
		"\u000b*\u0001\u0000\u0000\u0000\r,\u0001\u0000\u0000\u0000\u000f/\u0001"+
		"\u0000\u0000\u0000\u00112\u0001\u0000\u0000\u0000\u0013;\u0001\u0000\u0000"+
		"\u0000\u0015D\u0001\u0000\u0000\u0000\u0017K\u0001\u0000\u0000\u0000\u0019"+
		"R\u0001\u0000\u0000\u0000\u001bV\u0001\u0000\u0000\u0000\u001dd\u0001"+
		"\u0000\u0000\u0000\u001f \u0005:\u0000\u0000 \u0002\u0001\u0000\u0000"+
		"\u0000!\"\u0005|\u0000\u0000\"\u0004\u0001\u0000\u0000\u0000#$\u0005-"+
		"\u0000\u0000$%\u0005>\u0000\u0000%\u0006\u0001\u0000\u0000\u0000&\'\u0005"+
		"\u03b5\u0000\u0000\'\b\u0001\u0000\u0000\u0000()\u0005\"\u0000\u0000)"+
		"\n\u0001\u0000\u0000\u0000*+\u0005r\u0000\u0000+\f\u0001\u0000\u0000\u0000"+
		",-\u0005\\\u0000\u0000-.\u0005\"\u0000\u0000.\u000e\u0001\u0000\u0000"+
		"\u0000/0\u0005\\\u0000\u000001\u0005 \u0000\u00001\u0010\u0001\u0000\u0000"+
		"\u000026\u0005[\u0000\u000035\t\u0000\u0000\u000043\u0001\u0000\u0000"+
		"\u000058\u0001\u0000\u0000\u000067\u0001\u0000\u0000\u000064\u0001\u0000"+
		"\u0000\u000079\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u00009:\u0005"+
		"]\u0000\u0000:\u0012\u0001\u0000\u0000\u0000;?\u0005{\u0000\u0000<>\t"+
		"\u0000\u0000\u0000=<\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000"+
		"?@\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000@B\u0001\u0000\u0000"+
		"\u0000A?\u0001\u0000\u0000\u0000BC\u0005}\u0000\u0000C\u0014\u0001\u0000"+
		"\u0000\u0000DH\u0007\u0000\u0000\u0000EG\u0007\u0001\u0000\u0000FE\u0001"+
		"\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000"+
		"HI\u0001\u0000\u0000\u0000I\u0016\u0001\u0000\u0000\u0000JH\u0001\u0000"+
		"\u0000\u0000KO\u0007\u0002\u0000\u0000LN\u0007\u0003\u0000\u0000ML\u0001"+
		"\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000"+
		"OP\u0001\u0000\u0000\u0000P\u0018\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000RS\u0007\u0004\u0000\u0000ST\u0001\u0000\u0000\u0000TU\u0006"+
		"\f\u0000\u0000U\u001a\u0001\u0000\u0000\u0000VW\u0005#\u0000\u0000WX\u0005"+
		"#\u0000\u0000XY\u0005#\u0000\u0000Y]\u0001\u0000\u0000\u0000Z\\\t\u0000"+
		"\u0000\u0000[Z\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000]^\u0001"+
		"\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^`\u0001\u0000\u0000\u0000"+
		"_]\u0001\u0000\u0000\u0000`a\u0005#\u0000\u0000ab\u0005#\u0000\u0000b"+
		"c\u0005#\u0000\u0000c\u001c\u0001\u0000\u0000\u0000de\b\u0005\u0000\u0000"+
		"e\u001e\u0001\u0000\u0000\u0000\u0006\u00006?HO]\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}