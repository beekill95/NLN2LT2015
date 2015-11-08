// Generated from BKOOL.g4 by ANTLR 4.5.1

	package bkool.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BKOOLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, LP=3, RP=4, LSB=5, RSB=6, LB=7, RB=8, SEMICOLON=9, COLON=10, 
		COMMA=11, DOT=12, WS=13, LINE_COMMENT=14, BLOCK_COMMENT=15, INTLIT=16, 
		FLOATLIT=17, BOOLEANLIT=18, STRINGLIT=19, ADDOP=20, SUBOP=21, FDIVOP=22, 
		MULOP=23, MODOP=24, EQOP=25, MOREOP=26, MOREEQOP=27, IDIVOP=28, NEQOP=29, 
		LESSOP=30, LESSEQOP=31, LOGOROP=32, ANDOP=33, NOTOP=34, CONOP=35, NEWOP=36, 
		ASSIGNOP=37, BOOL=38, INTEGER=39, FLOAT=40, STRING=41, VOID=42, BREAK=43, 
		CLASS=44, CONTINUE=45, DO=46, ELSE=47, EXTENDS=48, IF=49, THEN=50, WHILE=51, 
		RETURN=52, NULL=53, SELF=54, FINAL=55, STATIC=56, ID=57, WRONG_STRINGLIT=58, 
		UNCLOSED_STRINGLIT=59, ERRORTOKEN=60;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "LP", "RP", "LSB", "RSB", "LB", "RB", "SEMICOLON", "COLON", 
		"COMMA", "DOT", "WS", "LINE_COMMENT", "BLOCK_COMMENT", "ALPHA", "DIGIT", 
		"INTLIT", "FLOATLIT", "EXPONENT", "BOOLEANLIT", "STRINGLIT", "ESCSEQ", 
		"SPECIAL", "ADDOP", "SUBOP", "FDIVOP", "MULOP", "MODOP", "EQOP", "MOREOP", 
		"MOREEQOP", "IDIVOP", "NEQOP", "LESSOP", "LESSEQOP", "LOGOROP", "ANDOP", 
		"NOTOP", "CONOP", "NEWOP", "ASSIGNOP", "BOOL", "INTEGER", "FLOAT", "STRING", 
		"VOID", "BREAK", "CLASS", "CONTINUE", "DO", "ELSE", "EXTENDS", "IF", "THEN", 
		"WHILE", "RETURN", "NULL", "SELF", "FINAL", "STATIC", "ID", "WRONG_STRINGLIT", 
		"ILLESCSEQ", "UNCLOSED_STRINGLIT", "ERRORTOKEN"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "':='", "'{'", "'}'", "'['", "']'", "'('", "')'", "';'", 
		"':'", "','", "'.'", null, null, null, null, null, null, null, "'+'", 
		"'-'", "'/'", "'*'", "'%'", "'=='", "'>'", "'>='", "'\\'", "'<>'", "'<'", 
		"'<='", "'||'", "'&&'", "'!'", "'^'", "'new'", null, "'bool'", "'integer'", 
		"'float'", "'string'", "'void'", "'break'", "'class'", "'continue'", "'do'", 
		"'else'", "'extends'", "'if'", "'then'", "'while'", "'return'", "'null'", 
		"'self'", "'final'", "'static'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "LP", "RP", "LSB", "RSB", "LB", "RB", "SEMICOLON", "COLON", 
		"COMMA", "DOT", "WS", "LINE_COMMENT", "BLOCK_COMMENT", "INTLIT", "FLOATLIT", 
		"BOOLEANLIT", "STRINGLIT", "ADDOP", "SUBOP", "FDIVOP", "MULOP", "MODOP", 
		"EQOP", "MOREOP", "MOREEQOP", "IDIVOP", "NEQOP", "LESSOP", "LESSEQOP", 
		"LOGOROP", "ANDOP", "NOTOP", "CONOP", "NEWOP", "ASSIGNOP", "BOOL", "INTEGER", 
		"FLOAT", "STRING", "VOID", "BREAK", "CLASS", "CONTINUE", "DO", "ELSE", 
		"EXTENDS", "IF", "THEN", "WHILE", "RETURN", "NULL", "SELF", "FINAL", "STATIC", 
		"ID", "WRONG_STRINGLIT", "UNCLOSED_STRINGLIT", "ERRORTOKEN"
	};
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


	public BKOOLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BKOOL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 62:
			WRONG_STRINGLIT_action((RuleContext)_localctx, actionIndex);
			break;
		case 64:
			UNCLOSED_STRINGLIT_action((RuleContext)_localctx, actionIndex);
			break;
		case 65:
			ERRORTOKEN_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void WRONG_STRINGLIT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			if (true) throw new bkool.parser.IllegalEscape(getText());
			break;
		}
	}
	private void UNCLOSED_STRINGLIT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			if(true) throw new bkool.parser.UncloseString(getText());
			break;
		}
	}
	private void ERRORTOKEN_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			if(true) throw new bkool.parser.ErrorToken(getText());
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2>\u01cd\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3"+
		"\16\6\16\u00a2\n\16\r\16\16\16\u00a3\3\16\3\16\3\17\3\17\7\17\u00aa\n"+
		"\17\f\17\16\17\u00ad\13\17\3\17\5\17\u00b0\n\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\7\20\u00ba\n\20\f\20\16\20\u00bd\13\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\21\5\21\u00c5\n\21\3\22\3\22\3\23\6\23\u00ca\n\23\r\23"+
		"\16\23\u00cb\3\24\6\24\u00cf\n\24\r\24\16\24\u00d0\3\24\5\24\u00d4\n\24"+
		"\3\24\7\24\u00d7\n\24\f\24\16\24\u00da\13\24\3\24\5\24\u00dd\n\24\3\25"+
		"\3\25\5\25\u00e1\n\25\3\25\6\25\u00e4\n\25\r\25\16\25\u00e5\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u00f1\n\26\3\27\3\27\3\27\3\27"+
		"\3\27\7\27\u00f8\n\27\f\27\16\27\u00fb\13\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37"+
		"\3\37\3 \3 \3!\3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'"+
		"\3\'\3(\3(\3)\3)\3*\3*\3*\3*\3+\3+\3+\5+\u0131\n+\3,\3,\3,\3,\3,\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3"+
		"\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3"+
		"\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\65\3"+
		"\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3"+
		"\67\38\38\38\38\38\39\39\39\39\39\39\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\3;"+
		"\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>\3>\3>\3?\3?\5?\u01a5"+
		"\n?\3?\3?\3?\7?\u01aa\n?\f?\16?\u01ad\13?\3@\3@\3@\3@\3@\7@\u01b4\n@\f"+
		"@\16@\u01b7\13@\3@\3@\3@\3A\3A\3A\3B\3B\3B\3B\3B\7B\u01c4\nB\fB\16B\u01c7"+
		"\13B\3B\3B\3C\3C\3C\3\u00bb\2D\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\2#\2%\22\'\23)\2+\24-\25/\2\61\2\63"+
		"\26\65\27\67\309\31;\32=\33?\34A\35C\36E\37G I!K\"M#O$Q%S&U\'W(Y)[*]+"+
		"_,a-c.e/g\60i\61k\62m\63o\64q\65s\66u\67w8y9{:};\177<\u0081\2\u0083=\u0085"+
		">\3\2\n\5\2\13\f\17\17\"\"\4\2\f\f\17\17\4\2C\\c|\3\2\62;\4\2GGgg\4\2"+
		"--//\t\2$$^^ddhhppttvv\t\2\n\f\16\17$$\62;C\\^^c|\u01e3\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2"+
		"\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U"+
		"\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2"+
		"\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2"+
		"\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{"+
		"\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\3\u0087"+
		"\3\2\2\2\5\u0089\3\2\2\2\7\u008c\3\2\2\2\t\u008e\3\2\2\2\13\u0090\3\2"+
		"\2\2\r\u0092\3\2\2\2\17\u0094\3\2\2\2\21\u0096\3\2\2\2\23\u0098\3\2\2"+
		"\2\25\u009a\3\2\2\2\27\u009c\3\2\2\2\31\u009e\3\2\2\2\33\u00a1\3\2\2\2"+
		"\35\u00a7\3\2\2\2\37\u00b5\3\2\2\2!\u00c4\3\2\2\2#\u00c6\3\2\2\2%\u00c9"+
		"\3\2\2\2\'\u00ce\3\2\2\2)\u00de\3\2\2\2+\u00f0\3\2\2\2-\u00f2\3\2\2\2"+
		"/\u00fe\3\2\2\2\61\u0101\3\2\2\2\63\u0103\3\2\2\2\65\u0105\3\2\2\2\67"+
		"\u0107\3\2\2\29\u0109\3\2\2\2;\u010b\3\2\2\2=\u010d\3\2\2\2?\u0110\3\2"+
		"\2\2A\u0112\3\2\2\2C\u0115\3\2\2\2E\u0117\3\2\2\2G\u011a\3\2\2\2I\u011c"+
		"\3\2\2\2K\u011f\3\2\2\2M\u0122\3\2\2\2O\u0125\3\2\2\2Q\u0127\3\2\2\2S"+
		"\u0129\3\2\2\2U\u0130\3\2\2\2W\u0132\3\2\2\2Y\u0137\3\2\2\2[\u013f\3\2"+
		"\2\2]\u0145\3\2\2\2_\u014c\3\2\2\2a\u0151\3\2\2\2c\u0157\3\2\2\2e\u015d"+
		"\3\2\2\2g\u0166\3\2\2\2i\u0169\3\2\2\2k\u016e\3\2\2\2m\u0176\3\2\2\2o"+
		"\u0179\3\2\2\2q\u017e\3\2\2\2s\u0184\3\2\2\2u\u018b\3\2\2\2w\u0190\3\2"+
		"\2\2y\u0195\3\2\2\2{\u019b\3\2\2\2}\u01a4\3\2\2\2\177\u01ae\3\2\2\2\u0081"+
		"\u01bb\3\2\2\2\u0083\u01be\3\2\2\2\u0085\u01ca\3\2\2\2\u0087\u0088\7?"+
		"\2\2\u0088\4\3\2\2\2\u0089\u008a\7<\2\2\u008a\u008b\7?\2\2\u008b\6\3\2"+
		"\2\2\u008c\u008d\7}\2\2\u008d\b\3\2\2\2\u008e\u008f\7\177\2\2\u008f\n"+
		"\3\2\2\2\u0090\u0091\7]\2\2\u0091\f\3\2\2\2\u0092\u0093\7_\2\2\u0093\16"+
		"\3\2\2\2\u0094\u0095\7*\2\2\u0095\20\3\2\2\2\u0096\u0097\7+\2\2\u0097"+
		"\22\3\2\2\2\u0098\u0099\7=\2\2\u0099\24\3\2\2\2\u009a\u009b\7<\2\2\u009b"+
		"\26\3\2\2\2\u009c\u009d\7.\2\2\u009d\30\3\2\2\2\u009e\u009f\7\60\2\2\u009f"+
		"\32\3\2\2\2\u00a0\u00a2\t\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2"+
		"\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6"+
		"\b\16\2\2\u00a6\34\3\2\2\2\u00a7\u00ab\7%\2\2\u00a8\u00aa\n\3\2\2\u00a9"+
		"\u00a8\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2"+
		"\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\7\17\2\2\u00af"+
		"\u00ae\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\f"+
		"\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b4\b\17\2\2\u00b4\36\3\2\2\2\u00b5\u00b6"+
		"\7*\2\2\u00b6\u00b7\7,\2\2\u00b7\u00bb\3\2\2\2\u00b8\u00ba\13\2\2\2\u00b9"+
		"\u00b8\3\2\2\2\u00ba\u00bd\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bb\u00b9\3\2"+
		"\2\2\u00bc\u00be\3\2\2\2\u00bd\u00bb\3\2\2\2\u00be\u00bf\7,\2\2\u00bf"+
		"\u00c0\7+\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\b\20\2\2\u00c2 \3\2\2\2"+
		"\u00c3\u00c5\t\4\2\2\u00c4\u00c3\3\2\2\2\u00c5\"\3\2\2\2\u00c6\u00c7\t"+
		"\5\2\2\u00c7$\3\2\2\2\u00c8\u00ca\5#\22\2\u00c9\u00c8\3\2\2\2\u00ca\u00cb"+
		"\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc&\3\2\2\2\u00cd"+
		"\u00cf\5#\22\2\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00ce\3\2"+
		"\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00d4\7\60\2\2\u00d3"+
		"\u00d2\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d8\3\2\2\2\u00d5\u00d7\5#"+
		"\22\2\u00d6\u00d5\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8"+
		"\u00d9\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00db\u00dd\5)"+
		"\25\2\u00dc\u00db\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd(\3\2\2\2\u00de\u00e0"+
		"\t\6\2\2\u00df\u00e1\t\7\2\2\u00e0\u00df\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1"+
		"\u00e3\3\2\2\2\u00e2\u00e4\5#\22\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6*\3\2\2\2\u00e7\u00e8"+
		"\7v\2\2\u00e8\u00e9\7t\2\2\u00e9\u00ea\7w\2\2\u00ea\u00f1\7g\2\2\u00eb"+
		"\u00ec\7h\2\2\u00ec\u00ed\7c\2\2\u00ed\u00ee\7n\2\2\u00ee\u00ef\7u\2\2"+
		"\u00ef\u00f1\7g\2\2\u00f0\u00e7\3\2\2\2\u00f0\u00eb\3\2\2\2\u00f1,\3\2"+
		"\2\2\u00f2\u00f9\7$\2\2\u00f3\u00f8\5!\21\2\u00f4\u00f8\5#\22\2\u00f5"+
		"\u00f8\5/\30\2\u00f6\u00f8\5\61\31\2\u00f7\u00f3\3\2\2\2\u00f7\u00f4\3"+
		"\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f6\3\2\2\2\u00f8\u00fb\3\2\2\2\u00f9"+
		"\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fc\3\2\2\2\u00fb\u00f9\3\2"+
		"\2\2\u00fc\u00fd\7$\2\2\u00fd.\3\2\2\2\u00fe\u00ff\7^\2\2\u00ff\u0100"+
		"\t\b\2\2\u0100\60\3\2\2\2\u0101\u0102\n\t\2\2\u0102\62\3\2\2\2\u0103\u0104"+
		"\7-\2\2\u0104\64\3\2\2\2\u0105\u0106\7/\2\2\u0106\66\3\2\2\2\u0107\u0108"+
		"\7\61\2\2\u01088\3\2\2\2\u0109\u010a\7,\2\2\u010a:\3\2\2\2\u010b\u010c"+
		"\7\'\2\2\u010c<\3\2\2\2\u010d\u010e\7?\2\2\u010e\u010f\7?\2\2\u010f>\3"+
		"\2\2\2\u0110\u0111\7@\2\2\u0111@\3\2\2\2\u0112\u0113\7@\2\2\u0113\u0114"+
		"\7?\2\2\u0114B\3\2\2\2\u0115\u0116\7^\2\2\u0116D\3\2\2\2\u0117\u0118\7"+
		">\2\2\u0118\u0119\7@\2\2\u0119F\3\2\2\2\u011a\u011b\7>\2\2\u011bH\3\2"+
		"\2\2\u011c\u011d\7>\2\2\u011d\u011e\7?\2\2\u011eJ\3\2\2\2\u011f\u0120"+
		"\7~\2\2\u0120\u0121\7~\2\2\u0121L\3\2\2\2\u0122\u0123\7(\2\2\u0123\u0124"+
		"\7(\2\2\u0124N\3\2\2\2\u0125\u0126\7#\2\2\u0126P\3\2\2\2\u0127\u0128\7"+
		"`\2\2\u0128R\3\2\2\2\u0129\u012a\7p\2\2\u012a\u012b\7g\2\2\u012b\u012c"+
		"\7y\2\2\u012cT\3\2\2\2\u012d\u0131\7?\2\2\u012e\u012f\7<\2\2\u012f\u0131"+
		"\7?\2\2\u0130\u012d\3\2\2\2\u0130\u012e\3\2\2\2\u0131V\3\2\2\2\u0132\u0133"+
		"\7d\2\2\u0133\u0134\7q\2\2\u0134\u0135\7q\2\2\u0135\u0136\7n\2\2\u0136"+
		"X\3\2\2\2\u0137\u0138\7k\2\2\u0138\u0139\7p\2\2\u0139\u013a\7v\2\2\u013a"+
		"\u013b\7g\2\2\u013b\u013c\7i\2\2\u013c\u013d\7g\2\2\u013d\u013e\7t\2\2"+
		"\u013eZ\3\2\2\2\u013f\u0140\7h\2\2\u0140\u0141\7n\2\2\u0141\u0142\7q\2"+
		"\2\u0142\u0143\7c\2\2\u0143\u0144\7v\2\2\u0144\\\3\2\2\2\u0145\u0146\7"+
		"u\2\2\u0146\u0147\7v\2\2\u0147\u0148\7t\2\2\u0148\u0149\7k\2\2\u0149\u014a"+
		"\7p\2\2\u014a\u014b\7i\2\2\u014b^\3\2\2\2\u014c\u014d\7x\2\2\u014d\u014e"+
		"\7q\2\2\u014e\u014f\7k\2\2\u014f\u0150\7f\2\2\u0150`\3\2\2\2\u0151\u0152"+
		"\7d\2\2\u0152\u0153\7t\2\2\u0153\u0154\7g\2\2\u0154\u0155\7c\2\2\u0155"+
		"\u0156\7m\2\2\u0156b\3\2\2\2\u0157\u0158\7e\2\2\u0158\u0159\7n\2\2\u0159"+
		"\u015a\7c\2\2\u015a\u015b\7u\2\2\u015b\u015c\7u\2\2\u015cd\3\2\2\2\u015d"+
		"\u015e\7e\2\2\u015e\u015f\7q\2\2\u015f\u0160\7p\2\2\u0160\u0161\7v\2\2"+
		"\u0161\u0162\7k\2\2\u0162\u0163\7p\2\2\u0163\u0164\7w\2\2\u0164\u0165"+
		"\7g\2\2\u0165f\3\2\2\2\u0166\u0167\7f\2\2\u0167\u0168\7q\2\2\u0168h\3"+
		"\2\2\2\u0169\u016a\7g\2\2\u016a\u016b\7n\2\2\u016b\u016c\7u\2\2\u016c"+
		"\u016d\7g\2\2\u016dj\3\2\2\2\u016e\u016f\7g\2\2\u016f\u0170\7z\2\2\u0170"+
		"\u0171\7v\2\2\u0171\u0172\7g\2\2\u0172\u0173\7p\2\2\u0173\u0174\7f\2\2"+
		"\u0174\u0175\7u\2\2\u0175l\3\2\2\2\u0176\u0177\7k\2\2\u0177\u0178\7h\2"+
		"\2\u0178n\3\2\2\2\u0179\u017a\7v\2\2\u017a\u017b\7j\2\2\u017b\u017c\7"+
		"g\2\2\u017c\u017d\7p\2\2\u017dp\3\2\2\2\u017e\u017f\7y\2\2\u017f\u0180"+
		"\7j\2\2\u0180\u0181\7k\2\2\u0181\u0182\7n\2\2\u0182\u0183\7g\2\2\u0183"+
		"r\3\2\2\2\u0184\u0185\7t\2\2\u0185\u0186\7g\2\2\u0186\u0187\7v\2\2\u0187"+
		"\u0188\7w\2\2\u0188\u0189\7t\2\2\u0189\u018a\7p\2\2\u018at\3\2\2\2\u018b"+
		"\u018c\7p\2\2\u018c\u018d\7w\2\2\u018d\u018e\7n\2\2\u018e\u018f\7n\2\2"+
		"\u018fv\3\2\2\2\u0190\u0191\7u\2\2\u0191\u0192\7g\2\2\u0192\u0193\7n\2"+
		"\2\u0193\u0194\7h\2\2\u0194x\3\2\2\2\u0195\u0196\7h\2\2\u0196\u0197\7"+
		"k\2\2\u0197\u0198\7p\2\2\u0198\u0199\7c\2\2\u0199\u019a\7n\2\2\u019az"+
		"\3\2\2\2\u019b\u019c\7u\2\2\u019c\u019d\7v\2\2\u019d\u019e\7c\2\2\u019e"+
		"\u019f\7v\2\2\u019f\u01a0\7k\2\2\u01a0\u01a1\7e\2\2\u01a1|\3\2\2\2\u01a2"+
		"\u01a5\7a\2\2\u01a3\u01a5\5!\21\2\u01a4\u01a2\3\2\2\2\u01a4\u01a3\3\2"+
		"\2\2\u01a5\u01ab\3\2\2\2\u01a6\u01aa\7a\2\2\u01a7\u01aa\5!\21\2\u01a8"+
		"\u01aa\5#\22\2\u01a9\u01a6\3\2\2\2\u01a9\u01a7\3\2\2\2\u01a9\u01a8\3\2"+
		"\2\2\u01aa\u01ad\3\2\2\2\u01ab\u01a9\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac"+
		"~\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ae\u01b5\7$\2\2\u01af\u01b4\5!\21\2\u01b0"+
		"\u01b4\5#\22\2\u01b1\u01b4\5/\30\2\u01b2\u01b4\5\61\31\2\u01b3\u01af\3"+
		"\2\2\2\u01b3\u01b0\3\2\2\2\u01b3\u01b1\3\2\2\2\u01b3\u01b2\3\2\2\2\u01b4"+
		"\u01b7\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6\u01b8\3\2"+
		"\2\2\u01b7\u01b5\3\2\2\2\u01b8\u01b9\5\u0081A\2\u01b9\u01ba\b@\3\2\u01ba"+
		"\u0080\3\2\2\2\u01bb\u01bc\7^\2\2\u01bc\u01bd\n\b\2\2\u01bd\u0082\3\2"+
		"\2\2\u01be\u01c5\7$\2\2\u01bf\u01c4\5!\21\2\u01c0\u01c4\5#\22\2\u01c1"+
		"\u01c4\5/\30\2\u01c2\u01c4\5\61\31\2\u01c3\u01bf\3\2\2\2\u01c3\u01c0\3"+
		"\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c2\3\2\2\2\u01c4\u01c7\3\2\2\2\u01c5"+
		"\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6\u01c8\3\2\2\2\u01c7\u01c5\3\2"+
		"\2\2\u01c8\u01c9\bB\4\2\u01c9\u0084\3\2\2\2\u01ca\u01cb\13\2\2\2\u01cb"+
		"\u01cc\bC\5\2\u01cc\u0086\3\2\2\2\32\2\u00a3\u00ab\u00af\u00bb\u00c4\u00cb"+
		"\u00d0\u00d3\u00d8\u00dc\u00e0\u00e5\u00f0\u00f7\u00f9\u0130\u01a4\u01a9"+
		"\u01ab\u01b3\u01b5\u01c3\u01c5\6\b\2\2\3@\2\3B\3\3C\4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}