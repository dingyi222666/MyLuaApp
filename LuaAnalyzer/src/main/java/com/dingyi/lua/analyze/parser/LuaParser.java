// Generated from D:/android studio project/MyLuaApp2/LuaAnalyzer/src/main/java/com/dingyi/lua/analyzer/parser\Lua.g4 by ANTLR 4.9.1
package com.dingyi.lua.analyze.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LuaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, NAME=65, NORMALSTRING=66, 
		CHARSTRING=67, LONGSTRING=68, INT=69, HEX=70, FLOAT=71, HEX_FLOAT=72, 
		COMMENT=73, LINE_COMMENT=74, WS=75, SHEBANG=76;
	public static final int
		RULE_chunk = 0, RULE_block = 1, RULE_stat = 2, RULE_ifbody = 3, RULE_elseifbody = 4, 
		RULE_elsebody = 5, RULE_defaultbody = 6, RULE_casebody = 7, RULE_lambdabody = 8, 
		RULE_attnamelist = 9, RULE_attrib = 10, RULE_retstat = 11, RULE_label = 12, 
		RULE_funcname = 13, RULE_varlist = 14, RULE_namelist = 15, RULE_explist = 16, 
		RULE_exp = 17, RULE_prefixexp = 18, RULE_functioncall = 19, RULE_varOrExp = 20, 
		RULE_var = 21, RULE_varSuffix = 22, RULE_nameAndArgs = 23, RULE_args = 24, 
		RULE_functiondef = 25, RULE_lambdadef = 26, RULE_funcbody = 27, RULE_parlist = 28, 
		RULE_tableconstructor = 29, RULE_fieldlist = 30, RULE_field = 31, RULE_fieldsep = 32, 
		RULE_operatorOr = 33, RULE_operatorAnd = 34, RULE_operatorComparison = 35, 
		RULE_operatorStrcat = 36, RULE_operatorAddSub = 37, RULE_operatorMulDivMod = 38, 
		RULE_operatorBitwise = 39, RULE_operatorUnary = 40, RULE_operatorPower = 41, 
		RULE_number = 42, RULE_string = 43;
	private static String[] makeRuleNames() {
		return new String[] {
			"chunk", "block", "stat", "ifbody", "elseifbody", "elsebody", "defaultbody", 
			"casebody", "lambdabody", "attnamelist", "attrib", "retstat", "label", 
			"funcname", "varlist", "namelist", "explist", "exp", "prefixexp", "functioncall", 
			"varOrExp", "var", "varSuffix", "nameAndArgs", "args", "functiondef", 
			"lambdadef", "funcbody", "parlist", "tableconstructor", "fieldlist", 
			"field", "fieldsep", "operatorOr", "operatorAnd", "operatorComparison", 
			"operatorStrcat", "operatorAddSub", "operatorMulDivMod", "operatorBitwise", 
			"operatorUnary", "operatorPower", "number", "string"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'='", "'break'", "'continue'", "'goto'", "'do'", "'end'", 
			"'while'", "'repeat'", "'until'", "'if'", "'then'", "'for'", "','", "'in'", 
			"'function'", "'local'", "'$'", "'switch'", "'when'", "'else'", "'lambda'", 
			"'defer'", "'elseif'", "'default'", "'case'", "':'", "'<'", "'>'", "'return'", 
			"'::'", "'@'", "'.'", "'nil'", "'false'", "'true'", "'...'", "'('", "')'", 
			"'['", "']'", "'{'", "'}'", "'or'", "'and'", "'<='", "'>='", "'~='", 
			"'=='", "'..'", "'+'", "'-'", "'*'", "'/'", "'%'", "'//'", "'&'", "'|'", 
			"'~'", "'<<'", "'>>'", "'not'", "'#'", "'^'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "NAME", "NORMALSTRING", "CHARSTRING", "LONGSTRING", 
			"INT", "HEX", "FLOAT", "HEX_FLOAT", "COMMENT", "LINE_COMMENT", "WS", 
			"SHEBANG"
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
	public String getGrammarFileName() { return "Lua.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LuaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ChunkContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(LuaParser.EOF, 0); }
		public ChunkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chunk; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterChunk(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitChunk(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitChunk(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChunkContext chunk() throws RecognitionException {
		ChunkContext _localctx = new ChunkContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_chunk);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			block();
			setState(89);
			match(EOF);
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

	public static class BlockContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public RetstatContext retstat() {
			return getRuleContext(RetstatContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__7) | (1L << T__8) | (1L << T__10) | (1L << T__12) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__21) | (1L << T__22) | (1L << T__30) | (1L << T__31) | (1L << T__37))) != 0) || _la==NAME) {
				{
				{
				setState(91);
				stat();
				}
				}
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__29) {
				{
				setState(97);
				retstat();
				}
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

	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IfStatContext extends StatContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IfbodyContext ifbody() {
			return getRuleContext(IfbodyContext.class,0);
		}
		public List<ElseifbodyContext> elseifbody() {
			return getRuleContexts(ElseifbodyContext.class);
		}
		public ElseifbodyContext elseifbody(int i) {
			return getRuleContext(ElseifbodyContext.class,i);
		}
		public ElsebodyContext elsebody() {
			return getRuleContext(ElsebodyContext.class,0);
		}
		public IfStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterIfStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitIfStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitIfStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DefaultStatContext extends StatContext {
		public DefaultStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterDefaultStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitDefaultStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitDefaultStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SwitchStatContext extends StatContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<CasebodyContext> casebody() {
			return getRuleContexts(CasebodyContext.class);
		}
		public CasebodyContext casebody(int i) {
			return getRuleContext(CasebodyContext.class,i);
		}
		public DefaultbodyContext defaultbody() {
			return getRuleContext(DefaultbodyContext.class,0);
		}
		public SwitchStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterSwitchStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitSwitchStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitSwitchStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ContinueStatContext extends StatContext {
		public ContinueStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterContinueStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitContinueStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitContinueStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BreakStatContext extends StatContext {
		public BreakStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterBreakStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitBreakStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitBreakStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhenStatContext extends StatContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<PrefixexpContext> prefixexp() {
			return getRuleContexts(PrefixexpContext.class);
		}
		public PrefixexpContext prefixexp(int i) {
			return getRuleContext(PrefixexpContext.class,i);
		}
		public WhenStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterWhenStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitWhenStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitWhenStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LabelStatContext extends StatContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LabelStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLabelStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLabelStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLabelStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForStatContext extends StatContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterForStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitForStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitForStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionCallStatContext extends StatContext {
		public FunctioncallContext functioncall() {
			return getRuleContext(FunctioncallContext.class,0);
		}
		public FunctionCallStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFunctionCallStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFunctionCallStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFunctionCallStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LocalFunctionDefStatContext extends StatContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public LocalFunctionDefStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLocalFunctionDefStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLocalFunctionDefStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLocalFunctionDefStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RepeatStatContext extends StatContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public RepeatStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterRepeatStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitRepeatStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitRepeatStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeferStatContext extends StatContext {
		public FunctioncallContext functioncall() {
			return getRuleContext(FunctioncallContext.class,0);
		}
		public DeferStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterDeferStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitDeferStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitDeferStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LambdaStatContext extends StatContext {
		public LambdabodyContext lambdabody() {
			return getRuleContext(LambdabodyContext.class,0);
		}
		public LambdaStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLambdaStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLambdaStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLambdaStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GotoStatContext extends StatContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public GotoStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterGotoStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitGotoStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitGotoStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForInStatContext extends StatContext {
		public NamelistContext namelist() {
			return getRuleContext(NamelistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForInStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterForInStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitForInStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitForInStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DoStatContext extends StatContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public DoStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterDoStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitDoStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitDoStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LocalVarListStatContext extends StatContext {
		public AttnamelistContext attnamelist() {
			return getRuleContext(AttnamelistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public LocalVarListStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLocalVarListStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLocalVarListStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLocalVarListStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionDefStatContext extends StatContext {
		public FuncnameContext funcname() {
			return getRuleContext(FuncnameContext.class,0);
		}
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public FunctionDefStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFunctionDefStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFunctionDefStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFunctionDefStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarListStatContext extends StatContext {
		public VarlistContext varlist() {
			return getRuleContext(VarlistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public VarListStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarListStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarListStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarListStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileStatContext extends StatContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterWhileStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitWhileStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitWhileStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stat);
		int _la;
		try {
			setState(212);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new DefaultStatContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(100);
				match(T__0);
				}
				break;
			case 2:
				_localctx = new VarListStatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				varlist();
				setState(102);
				match(T__1);
				setState(103);
				explist();
				}
				break;
			case 3:
				_localctx = new FunctionCallStatContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(105);
				functioncall();
				}
				break;
			case 4:
				_localctx = new LabelStatContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(106);
				label();
				}
				break;
			case 5:
				_localctx = new BreakStatContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(107);
				match(T__2);
				}
				break;
			case 6:
				_localctx = new ContinueStatContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(108);
				match(T__3);
				}
				break;
			case 7:
				_localctx = new GotoStatContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(109);
				match(T__4);
				setState(110);
				match(NAME);
				}
				break;
			case 8:
				_localctx = new DoStatContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(111);
				match(T__5);
				setState(112);
				block();
				setState(113);
				match(T__6);
				}
				break;
			case 9:
				_localctx = new WhileStatContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(115);
				match(T__7);
				setState(116);
				exp(0);
				setState(117);
				match(T__5);
				setState(118);
				block();
				setState(119);
				match(T__6);
				}
				break;
			case 10:
				_localctx = new RepeatStatContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(121);
				match(T__8);
				setState(122);
				block();
				setState(123);
				match(T__9);
				setState(124);
				exp(0);
				}
				break;
			case 11:
				_localctx = new IfStatContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(126);
				match(T__10);
				setState(127);
				exp(0);
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(128);
					match(T__11);
					}
				}

				setState(131);
				ifbody();
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__23) {
					{
					{
					setState(132);
					elseifbody();
					}
					}
					setState(137);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__20) {
					{
					setState(138);
					elsebody();
					}
				}

				setState(141);
				match(T__6);
				}
				break;
			case 12:
				_localctx = new ForStatContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(143);
				match(T__12);
				setState(144);
				match(NAME);
				setState(145);
				match(T__1);
				setState(146);
				exp(0);
				setState(147);
				match(T__13);
				setState(148);
				exp(0);
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(149);
					match(T__13);
					setState(150);
					exp(0);
					}
				}

				setState(154);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(153);
					match(T__5);
					}
					break;
				}
				setState(156);
				block();
				setState(157);
				match(T__6);
				}
				break;
			case 13:
				_localctx = new ForInStatContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(159);
				match(T__12);
				setState(160);
				namelist();
				setState(161);
				match(T__14);
				setState(162);
				explist();
				setState(164);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
				case 1:
					{
					setState(163);
					match(T__5);
					}
					break;
				}
				setState(166);
				block();
				setState(167);
				match(T__6);
				}
				break;
			case 14:
				_localctx = new FunctionDefStatContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(169);
				match(T__15);
				setState(170);
				funcname();
				setState(171);
				funcbody();
				}
				break;
			case 15:
				_localctx = new LocalFunctionDefStatContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(173);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(174);
				match(T__15);
				setState(175);
				match(NAME);
				setState(176);
				funcbody();
				}
				break;
			case 16:
				_localctx = new LocalVarListStatContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(177);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(178);
				attnamelist();
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(179);
					match(T__1);
					setState(180);
					explist();
					}
				}

				}
				break;
			case 17:
				_localctx = new SwitchStatContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(183);
				match(T__18);
				setState(184);
				exp(0);
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(185);
					match(T__5);
					}
				}

				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__25) {
					{
					{
					setState(188);
					casebody();
					}
					}
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__24) {
					{
					setState(194);
					defaultbody();
					}
				}

				setState(197);
				match(T__6);
				}
				break;
			case 18:
				_localctx = new WhenStatContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(199);
				match(T__19);
				setState(200);
				exp(0);
				setState(201);
				prefixexp();
				setState(206);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(203);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__20) {
						{
						setState(202);
						match(T__20);
						}
					}

					setState(205);
					prefixexp();
					}
					break;
				}
				}
				break;
			case 19:
				_localctx = new LambdaStatContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(208);
				match(T__21);
				setState(209);
				lambdabody();
				}
				break;
			case 20:
				_localctx = new DeferStatContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(210);
				match(T__22);
				setState(211);
				functioncall();
				}
				break;
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

	public static class IfbodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifbody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterIfbody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitIfbody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitIfbody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfbodyContext ifbody() throws RecognitionException {
		IfbodyContext _localctx = new IfbodyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ifbody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			block();
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

	public static class ElseifbodyContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElseifbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseifbody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterElseifbody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitElseifbody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitElseifbody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseifbodyContext elseifbody() throws RecognitionException {
		ElseifbodyContext _localctx = new ElseifbodyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_elseifbody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(T__23);
			setState(217);
			exp(0);
			setState(218);
			match(T__11);
			setState(219);
			block();
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

	public static class ElsebodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElsebodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elsebody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterElsebody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitElsebody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitElsebody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElsebodyContext elsebody() throws RecognitionException {
		ElsebodyContext _localctx = new ElsebodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elsebody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(T__20);
			setState(222);
			block();
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

	public static class DefaultbodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public DefaultbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultbody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterDefaultbody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitDefaultbody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitDefaultbody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultbodyContext defaultbody() throws RecognitionException {
		DefaultbodyContext _localctx = new DefaultbodyContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_defaultbody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			match(T__24);
			setState(225);
			block();
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

	public static class CasebodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParlistContext parlist() {
			return getRuleContext(ParlistContext.class,0);
		}
		public CasebodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_casebody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterCasebody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitCasebody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitCasebody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CasebodyContext casebody() throws RecognitionException {
		CasebodyContext _localctx = new CasebodyContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_casebody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			match(T__25);
			{
			setState(228);
			parlist();
			}
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11 || _la==T__26) {
				{
				setState(229);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__26) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(232);
			block();
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

	public static class LambdabodyContext extends ParserRuleContext {
		public ParlistContext parlist() {
			return getRuleContext(ParlistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public LambdabodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdabody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLambdabody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLambdabody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLambdabody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdabodyContext lambdabody() throws RecognitionException {
		LambdabodyContext _localctx = new LambdabodyContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_lambdabody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(234);
			parlist();
			}
			setState(235);
			match(T__26);
			{
			setState(236);
			explist();
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

	public static class AttnamelistContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(LuaParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(LuaParser.NAME, i);
		}
		public List<AttribContext> attrib() {
			return getRuleContexts(AttribContext.class);
		}
		public AttribContext attrib(int i) {
			return getRuleContext(AttribContext.class,i);
		}
		public AttnamelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attnamelist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterAttnamelist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitAttnamelist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitAttnamelist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttnamelistContext attnamelist() throws RecognitionException {
		AttnamelistContext _localctx = new AttnamelistContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_attnamelist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(NAME);
			setState(239);
			attrib();
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(240);
				match(T__13);
				setState(241);
				match(NAME);
				setState(242);
				attrib();
				}
				}
				setState(247);
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

	public static class AttribContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public AttribContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrib; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterAttrib(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitAttrib(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitAttrib(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttribContext attrib() throws RecognitionException {
		AttribContext _localctx = new AttribContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_attrib);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__27) {
				{
				setState(248);
				match(T__27);
				setState(249);
				match(NAME);
				setState(250);
				match(T__28);
				}
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

	public static class RetstatContext extends ParserRuleContext {
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public RetstatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retstat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterRetstat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitRetstat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitRetstat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetstatContext retstat() throws RecognitionException {
		RetstatContext _localctx = new RetstatContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_retstat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(T__29);
			setState(255);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (T__15 - 16)) | (1L << (T__21 - 16)) | (1L << (T__33 - 16)) | (1L << (T__34 - 16)) | (1L << (T__35 - 16)) | (1L << (T__36 - 16)) | (1L << (T__37 - 16)) | (1L << (T__41 - 16)) | (1L << (T__51 - 16)) | (1L << (T__58 - 16)) | (1L << (T__61 - 16)) | (1L << (T__62 - 16)) | (1L << (NAME - 16)) | (1L << (NORMALSTRING - 16)) | (1L << (CHARSTRING - 16)) | (1L << (LONGSTRING - 16)) | (1L << (INT - 16)) | (1L << (HEX - 16)) | (1L << (FLOAT - 16)) | (1L << (HEX_FLOAT - 16)))) != 0)) {
				{
				setState(254);
				explist();
				}
			}

			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(257);
				match(T__0);
				}
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

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_label);
		try {
			setState(265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__30:
				enterOuterAlt(_localctx, 1);
				{
				setState(260);
				match(T__30);
				setState(261);
				match(NAME);
				setState(262);
				match(T__30);
				}
				break;
			case T__31:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				match(T__31);
				setState(264);
				match(NAME);
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

	public static class FuncnameContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(LuaParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(LuaParser.NAME, i);
		}
		public FuncnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFuncname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFuncname(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFuncname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncnameContext funcname() throws RecognitionException {
		FuncnameContext _localctx = new FuncnameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_funcname);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(NAME);
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__32) {
				{
				{
				setState(268);
				match(T__32);
				setState(269);
				match(NAME);
				}
				}
				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26) {
				{
				setState(275);
				match(T__26);
				setState(276);
				match(NAME);
				}
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

	public static class VarlistContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public VarlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarlistContext varlist() throws RecognitionException {
		VarlistContext _localctx = new VarlistContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_varlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			var();
			setState(284);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(280);
				match(T__13);
				setState(281);
				var();
				}
				}
				setState(286);
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

	public static class NamelistContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(LuaParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(LuaParser.NAME, i);
		}
		public NamelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namelist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterNamelist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitNamelist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitNamelist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamelistContext namelist() throws RecognitionException {
		NamelistContext _localctx = new NamelistContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_namelist);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			match(NAME);
			setState(292);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(288);
					match(T__13);
					setState(289);
					match(NAME);
					}
					} 
				}
				setState(294);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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

	public static class ExplistContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ExplistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExplist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExplist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExplist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplistContext explist() throws RecognitionException {
		ExplistContext _localctx = new ExplistContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_explist);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			exp(0);
			setState(300);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(296);
					match(T__13);
					setState(297);
					exp(0);
					}
					} 
				}
				setState(302);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
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

	public static class ExpContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public FunctiondefContext functiondef() {
			return getRuleContext(FunctiondefContext.class,0);
		}
		public PrefixexpContext prefixexp() {
			return getRuleContext(PrefixexpContext.class,0);
		}
		public TableconstructorContext tableconstructor() {
			return getRuleContext(TableconstructorContext.class,0);
		}
		public OperatorUnaryContext operatorUnary() {
			return getRuleContext(OperatorUnaryContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public LambdadefContext lambdadef() {
			return getRuleContext(LambdadefContext.class,0);
		}
		public OperatorPowerContext operatorPower() {
			return getRuleContext(OperatorPowerContext.class,0);
		}
		public OperatorMulDivModContext operatorMulDivMod() {
			return getRuleContext(OperatorMulDivModContext.class,0);
		}
		public OperatorAddSubContext operatorAddSub() {
			return getRuleContext(OperatorAddSubContext.class,0);
		}
		public OperatorStrcatContext operatorStrcat() {
			return getRuleContext(OperatorStrcatContext.class,0);
		}
		public OperatorComparisonContext operatorComparison() {
			return getRuleContext(OperatorComparisonContext.class,0);
		}
		public OperatorAndContext operatorAnd() {
			return getRuleContext(OperatorAndContext.class,0);
		}
		public OperatorOrContext operatorOr() {
			return getRuleContext(OperatorOrContext.class,0);
		}
		public OperatorBitwiseContext operatorBitwise() {
			return getRuleContext(OperatorBitwiseContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_exp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(304);
				match(T__33);
				}
				break;
			case 2:
				{
				setState(305);
				match(T__34);
				}
				break;
			case 3:
				{
				setState(306);
				match(T__35);
				}
				break;
			case 4:
				{
				setState(307);
				number();
				}
				break;
			case 5:
				{
				setState(308);
				string();
				}
				break;
			case 6:
				{
				setState(309);
				match(T__36);
				}
				break;
			case 7:
				{
				setState(310);
				functiondef();
				}
				break;
			case 8:
				{
				setState(311);
				prefixexp();
				}
				break;
			case 9:
				{
				setState(312);
				tableconstructor();
				}
				break;
			case 10:
				{
				setState(313);
				operatorUnary();
				setState(314);
				exp(9);
				}
				break;
			case 11:
				{
				setState(316);
				lambdadef();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(353);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(351);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(319);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(320);
						operatorPower();
						setState(321);
						exp(10);
						}
						break;
					case 2:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(323);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(324);
						operatorMulDivMod();
						setState(325);
						exp(9);
						}
						break;
					case 3:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(327);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(328);
						operatorAddSub();
						setState(329);
						exp(8);
						}
						break;
					case 4:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(331);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(332);
						operatorStrcat();
						setState(333);
						exp(6);
						}
						break;
					case 5:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(335);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(336);
						operatorComparison();
						setState(337);
						exp(6);
						}
						break;
					case 6:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(339);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(340);
						operatorAnd();
						setState(341);
						exp(5);
						}
						break;
					case 7:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(343);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(344);
						operatorOr();
						setState(345);
						exp(4);
						}
						break;
					case 8:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(347);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(348);
						operatorBitwise();
						setState(349);
						exp(3);
						}
						break;
					}
					} 
				}
				setState(355);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrefixexpContext extends ParserRuleContext {
		public VarOrExpContext varOrExp() {
			return getRuleContext(VarOrExpContext.class,0);
		}
		public List<NameAndArgsContext> nameAndArgs() {
			return getRuleContexts(NameAndArgsContext.class);
		}
		public NameAndArgsContext nameAndArgs(int i) {
			return getRuleContext(NameAndArgsContext.class,i);
		}
		public PrefixexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterPrefixexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitPrefixexp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitPrefixexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixexpContext prefixexp() throws RecognitionException {
		PrefixexpContext _localctx = new PrefixexpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_prefixexp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			varOrExp();
			setState(360);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(357);
					nameAndArgs();
					}
					} 
				}
				setState(362);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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

	public static class FunctioncallContext extends ParserRuleContext {
		public VarOrExpContext varOrExp() {
			return getRuleContext(VarOrExpContext.class,0);
		}
		public List<NameAndArgsContext> nameAndArgs() {
			return getRuleContexts(NameAndArgsContext.class);
		}
		public NameAndArgsContext nameAndArgs(int i) {
			return getRuleContext(NameAndArgsContext.class,i);
		}
		public FunctioncallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functioncall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFunctioncall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFunctioncall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFunctioncall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctioncallContext functioncall() throws RecognitionException {
		FunctioncallContext _localctx = new FunctioncallContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_functioncall);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			varOrExp();
			setState(365); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(364);
					nameAndArgs();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(367); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class VarOrExpContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VarOrExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varOrExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarOrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarOrExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarOrExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarOrExpContext varOrExp() throws RecognitionException {
		VarOrExpContext _localctx = new VarOrExpContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_varOrExp);
		try {
			setState(374);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(369);
				var();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(370);
				match(T__37);
				setState(371);
				exp(0);
				setState(372);
				match(T__38);
				}
				break;
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

	public static class VarContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<VarSuffixContext> varSuffix() {
			return getRuleContexts(VarSuffixContext.class);
		}
		public VarSuffixContext varSuffix(int i) {
			return getRuleContext(VarSuffixContext.class,i);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_var);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(376);
				match(NAME);
				}
				break;
			case T__37:
				{
				setState(377);
				match(T__37);
				setState(378);
				exp(0);
				setState(379);
				match(T__38);
				setState(380);
				varSuffix();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(387);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(384);
					varSuffix();
					}
					} 
				}
				setState(389);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
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

	public static class VarSuffixContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public List<NameAndArgsContext> nameAndArgs() {
			return getRuleContexts(NameAndArgsContext.class);
		}
		public NameAndArgsContext nameAndArgs(int i) {
			return getRuleContext(NameAndArgsContext.class,i);
		}
		public VarSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarSuffixContext varSuffix() throws RecognitionException {
		VarSuffixContext _localctx = new VarSuffixContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_varSuffix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 27)) & ~0x3f) == 0 && ((1L << (_la - 27)) & ((1L << (T__26 - 27)) | (1L << (T__37 - 27)) | (1L << (T__41 - 27)) | (1L << (NAME - 27)) | (1L << (NORMALSTRING - 27)) | (1L << (CHARSTRING - 27)) | (1L << (LONGSTRING - 27)))) != 0)) {
				{
				{
				setState(390);
				nameAndArgs();
				}
				}
				setState(395);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(402);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__39:
				{
				setState(396);
				match(T__39);
				setState(397);
				exp(0);
				setState(398);
				match(T__40);
				}
				break;
			case T__32:
				{
				setState(400);
				match(T__32);
				setState(401);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class NameAndArgsContext extends ParserRuleContext {
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public NameAndArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameAndArgs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterNameAndArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitNameAndArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitNameAndArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameAndArgsContext nameAndArgs() throws RecognitionException {
		NameAndArgsContext _localctx = new NameAndArgsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_nameAndArgs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26) {
				{
				setState(404);
				match(T__26);
				setState(405);
				match(NAME);
				}
			}

			setState(408);
			args();
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

	public static class ArgsContext extends ParserRuleContext {
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public TableconstructorContext tableconstructor() {
			return getRuleContext(TableconstructorContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_args);
		int _la;
		try {
			setState(417);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__37:
				enterOuterAlt(_localctx, 1);
				{
				setState(410);
				match(T__37);
				setState(412);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (T__15 - 16)) | (1L << (T__21 - 16)) | (1L << (T__33 - 16)) | (1L << (T__34 - 16)) | (1L << (T__35 - 16)) | (1L << (T__36 - 16)) | (1L << (T__37 - 16)) | (1L << (T__41 - 16)) | (1L << (T__51 - 16)) | (1L << (T__58 - 16)) | (1L << (T__61 - 16)) | (1L << (T__62 - 16)) | (1L << (NAME - 16)) | (1L << (NORMALSTRING - 16)) | (1L << (CHARSTRING - 16)) | (1L << (LONGSTRING - 16)) | (1L << (INT - 16)) | (1L << (HEX - 16)) | (1L << (FLOAT - 16)) | (1L << (HEX_FLOAT - 16)))) != 0)) {
					{
					setState(411);
					explist();
					}
				}

				setState(414);
				match(T__38);
				}
				break;
			case T__41:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(415);
				tableconstructor();
				}
				break;
			case NORMALSTRING:
			case CHARSTRING:
			case LONGSTRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(416);
				string();
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

	public static class FunctiondefContext extends ParserRuleContext {
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public FunctiondefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functiondef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFunctiondef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFunctiondef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFunctiondef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctiondefContext functiondef() throws RecognitionException {
		FunctiondefContext _localctx = new FunctiondefContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_functiondef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			match(T__15);
			setState(420);
			funcbody();
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

	public static class LambdadefContext extends ParserRuleContext {
		public LambdabodyContext lambdabody() {
			return getRuleContext(LambdabodyContext.class,0);
		}
		public LambdadefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdadef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLambdadef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLambdadef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLambdadef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdadefContext lambdadef() throws RecognitionException {
		LambdadefContext _localctx = new LambdadefContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_lambdadef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			match(T__21);
			setState(423);
			lambdabody();
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

	public static class FuncbodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParlistContext parlist() {
			return getRuleContext(ParlistContext.class,0);
		}
		public FuncbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcbody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFuncbody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFuncbody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFuncbody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncbodyContext funcbody() throws RecognitionException {
		FuncbodyContext _localctx = new FuncbodyContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_funcbody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(425);
			match(T__37);
			setState(427);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__36 || _la==NAME) {
				{
				setState(426);
				parlist();
				}
			}

			setState(429);
			match(T__38);
			setState(430);
			block();
			setState(431);
			match(T__6);
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

	public static class ParlistContext extends ParserRuleContext {
		public NamelistContext namelist() {
			return getRuleContext(NamelistContext.class,0);
		}
		public ParlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterParlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitParlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitParlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParlistContext parlist() throws RecognitionException {
		ParlistContext _localctx = new ParlistContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_parlist);
		int _la;
		try {
			setState(439);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(433);
				namelist();
				setState(436);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(434);
					match(T__13);
					setState(435);
					match(T__36);
					}
				}

				}
				break;
			case T__36:
				enterOuterAlt(_localctx, 2);
				{
				setState(438);
				match(T__36);
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

	public static class TableconstructorContext extends ParserRuleContext {
		public FieldlistContext fieldlist() {
			return getRuleContext(FieldlistContext.class,0);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TableconstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableconstructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterTableconstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitTableconstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitTableconstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableconstructorContext tableconstructor() throws RecognitionException {
		TableconstructorContext _localctx = new TableconstructorContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_tableconstructor);
		int _la;
		try {
			setState(452);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__41:
				enterOuterAlt(_localctx, 1);
				{
				setState(441);
				match(T__41);
				setState(443);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (T__15 - 16)) | (1L << (T__21 - 16)) | (1L << (T__33 - 16)) | (1L << (T__34 - 16)) | (1L << (T__35 - 16)) | (1L << (T__36 - 16)) | (1L << (T__37 - 16)) | (1L << (T__39 - 16)) | (1L << (T__41 - 16)) | (1L << (T__51 - 16)) | (1L << (T__58 - 16)) | (1L << (T__61 - 16)) | (1L << (T__62 - 16)) | (1L << (NAME - 16)) | (1L << (NORMALSTRING - 16)) | (1L << (CHARSTRING - 16)) | (1L << (LONGSTRING - 16)) | (1L << (INT - 16)) | (1L << (HEX - 16)) | (1L << (FLOAT - 16)) | (1L << (HEX_FLOAT - 16)))) != 0)) {
					{
					setState(442);
					fieldlist();
					}
				}

				setState(445);
				match(T__42);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(446);
				match(NAME);
				setState(447);
				match(T__39);
				setState(449);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (INT - 69)) | (1L << (HEX - 69)) | (1L << (FLOAT - 69)) | (1L << (HEX_FLOAT - 69)))) != 0)) {
					{
					setState(448);
					number();
					}
				}

				setState(451);
				match(T__40);
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

	public static class FieldlistContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public List<FieldsepContext> fieldsep() {
			return getRuleContexts(FieldsepContext.class);
		}
		public FieldsepContext fieldsep(int i) {
			return getRuleContext(FieldsepContext.class,i);
		}
		public FieldlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFieldlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFieldlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFieldlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldlistContext fieldlist() throws RecognitionException {
		FieldlistContext _localctx = new FieldlistContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_fieldlist);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(454);
			field();
			setState(460);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(455);
					fieldsep();
					setState(456);
					field();
					}
					} 
				}
				setState(462);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			}
			setState(464);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0 || _la==T__13) {
				{
				setState(463);
				fieldsep();
				}
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

	public static class FieldContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_field);
		try {
			setState(476);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(466);
				match(T__39);
				setState(467);
				exp(0);
				setState(468);
				match(T__40);
				setState(469);
				match(T__1);
				setState(470);
				exp(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(472);
				match(NAME);
				setState(473);
				match(T__1);
				setState(474);
				exp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(475);
				exp(0);
				}
				break;
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

	public static class FieldsepContext extends ParserRuleContext {
		public FieldsepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldsep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFieldsep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFieldsep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFieldsep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldsepContext fieldsep() throws RecognitionException {
		FieldsepContext _localctx = new FieldsepContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_fieldsep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==T__13) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OperatorOrContext extends ParserRuleContext {
		public OperatorOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorOr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorOrContext operatorOr() throws RecognitionException {
		OperatorOrContext _localctx = new OperatorOrContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_operatorOr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(480);
			match(T__43);
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

	public static class OperatorAndContext extends ParserRuleContext {
		public OperatorAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAndContext operatorAnd() throws RecognitionException {
		OperatorAndContext _localctx = new OperatorAndContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_operatorAnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(482);
			match(T__44);
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

	public static class OperatorComparisonContext extends ParserRuleContext {
		public OperatorComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorComparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorComparisonContext operatorComparison() throws RecognitionException {
		OperatorComparisonContext _localctx = new OperatorComparisonContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_operatorComparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(484);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OperatorStrcatContext extends ParserRuleContext {
		public OperatorStrcatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorStrcat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorStrcat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorStrcat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorStrcat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorStrcatContext operatorStrcat() throws RecognitionException {
		OperatorStrcatContext _localctx = new OperatorStrcatContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_operatorStrcat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(486);
			match(T__49);
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

	public static class OperatorAddSubContext extends ParserRuleContext {
		public OperatorAddSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAddSub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAddSubContext operatorAddSub() throws RecognitionException {
		OperatorAddSubContext _localctx = new OperatorAddSubContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_operatorAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(488);
			_la = _input.LA(1);
			if ( !(_la==T__50 || _la==T__51) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OperatorMulDivModContext extends ParserRuleContext {
		public OperatorMulDivModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorMulDivMod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorMulDivMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorMulDivMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorMulDivModContext operatorMulDivMod() throws RecognitionException {
		OperatorMulDivModContext _localctx = new OperatorMulDivModContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_operatorMulDivMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OperatorBitwiseContext extends ParserRuleContext {
		public OperatorBitwiseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorBitwise; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorBitwise(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorBitwise(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorBitwise(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorBitwiseContext operatorBitwise() throws RecognitionException {
		OperatorBitwiseContext _localctx = new OperatorBitwiseContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_operatorBitwise);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__56) | (1L << T__57) | (1L << T__58) | (1L << T__59) | (1L << T__60))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OperatorUnaryContext extends ParserRuleContext {
		public OperatorUnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorUnary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorUnaryContext operatorUnary() throws RecognitionException {
		OperatorUnaryContext _localctx = new OperatorUnaryContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_operatorUnary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(494);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__58) | (1L << T__61) | (1L << T__62))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OperatorPowerContext extends ParserRuleContext {
		public OperatorPowerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorPower; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorPower(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorPower(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorPowerContext operatorPower() throws RecognitionException {
		OperatorPowerContext _localctx = new OperatorPowerContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_operatorPower);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(496);
			match(T__63);
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

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(LuaParser.INT, 0); }
		public TerminalNode HEX() { return getToken(LuaParser.HEX, 0); }
		public TerminalNode FLOAT() { return getToken(LuaParser.FLOAT, 0); }
		public TerminalNode HEX_FLOAT() { return getToken(LuaParser.HEX_FLOAT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(498);
			_la = _input.LA(1);
			if ( !(((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (INT - 69)) | (1L << (HEX - 69)) | (1L << (FLOAT - 69)) | (1L << (HEX_FLOAT - 69)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class StringContext extends ParserRuleContext {
		public TerminalNode NORMALSTRING() { return getToken(LuaParser.NORMALSTRING, 0); }
		public TerminalNode CHARSTRING() { return getToken(LuaParser.CHARSTRING, 0); }
		public TerminalNode LONGSTRING() { return getToken(LuaParser.LONGSTRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(500);
			_la = _input.LA(1);
			if ( !(((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (NORMALSTRING - 66)) | (1L << (CHARSTRING - 66)) | (1L << (LONGSTRING - 66)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 17:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 8);
		case 2:
			return precpred(_ctx, 7);
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3N\u01f9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\3\2\3\3\7\3_\n\3\f\3\16\3b\13\3\3\3\5\3e\n\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u0084\n\4\3\4\3\4\7\4\u0088"+
		"\n\4\f\4\16\4\u008b\13\4\3\4\5\4\u008e\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\5\4\u009a\n\4\3\4\5\4\u009d\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\5\4\u00a7\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\5\4\u00b8\n\4\3\4\3\4\3\4\5\4\u00bd\n\4\3\4\7\4\u00c0\n\4\f"+
		"\4\16\4\u00c3\13\4\3\4\5\4\u00c6\n\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u00ce"+
		"\n\4\3\4\5\4\u00d1\n\4\3\4\3\4\3\4\3\4\5\4\u00d7\n\4\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\5\t\u00e9\n\t\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\7\13\u00f6\n\13\f\13\16\13\u00f9"+
		"\13\13\3\f\3\f\3\f\5\f\u00fe\n\f\3\r\3\r\5\r\u0102\n\r\3\r\5\r\u0105\n"+
		"\r\3\16\3\16\3\16\3\16\3\16\5\16\u010c\n\16\3\17\3\17\3\17\7\17\u0111"+
		"\n\17\f\17\16\17\u0114\13\17\3\17\3\17\5\17\u0118\n\17\3\20\3\20\3\20"+
		"\7\20\u011d\n\20\f\20\16\20\u0120\13\20\3\21\3\21\3\21\7\21\u0125\n\21"+
		"\f\21\16\21\u0128\13\21\3\22\3\22\3\22\7\22\u012d\n\22\f\22\16\22\u0130"+
		"\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\5\23\u0140\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u0162\n\23\f\23\16\23\u0165"+
		"\13\23\3\24\3\24\7\24\u0169\n\24\f\24\16\24\u016c\13\24\3\25\3\25\6\25"+
		"\u0170\n\25\r\25\16\25\u0171\3\26\3\26\3\26\3\26\3\26\5\26\u0179\n\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0181\n\27\3\27\7\27\u0184\n\27\f"+
		"\27\16\27\u0187\13\27\3\30\7\30\u018a\n\30\f\30\16\30\u018d\13\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\5\30\u0195\n\30\3\31\3\31\5\31\u0199\n\31\3"+
		"\31\3\31\3\32\3\32\5\32\u019f\n\32\3\32\3\32\3\32\5\32\u01a4\n\32\3\33"+
		"\3\33\3\33\3\34\3\34\3\34\3\35\3\35\5\35\u01ae\n\35\3\35\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\5\36\u01b7\n\36\3\36\5\36\u01ba\n\36\3\37\3\37\5\37\u01be"+
		"\n\37\3\37\3\37\3\37\3\37\5\37\u01c4\n\37\3\37\5\37\u01c7\n\37\3 \3 \3"+
		" \3 \7 \u01cd\n \f \16 \u01d0\13 \3 \5 \u01d3\n \3!\3!\3!\3!\3!\3!\3!"+
		"\3!\3!\3!\5!\u01df\n!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3"+
		")\3)\3*\3*\3+\3+\3,\3,\3-\3-\3-\2\3$.\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX\2\f\3\2\23\24\4\2\16\16\35"+
		"\35\4\2\3\3\20\20\4\2\36\37\60\63\3\2\65\66\3\2\67:\3\2;?\5\2\66\66=="+
		"@A\3\2GJ\3\2DF\2\u021f\2Z\3\2\2\2\4`\3\2\2\2\6\u00d6\3\2\2\2\b\u00d8\3"+
		"\2\2\2\n\u00da\3\2\2\2\f\u00df\3\2\2\2\16\u00e2\3\2\2\2\20\u00e5\3\2\2"+
		"\2\22\u00ec\3\2\2\2\24\u00f0\3\2\2\2\26\u00fd\3\2\2\2\30\u00ff\3\2\2\2"+
		"\32\u010b\3\2\2\2\34\u010d\3\2\2\2\36\u0119\3\2\2\2 \u0121\3\2\2\2\"\u0129"+
		"\3\2\2\2$\u013f\3\2\2\2&\u0166\3\2\2\2(\u016d\3\2\2\2*\u0178\3\2\2\2,"+
		"\u0180\3\2\2\2.\u018b\3\2\2\2\60\u0198\3\2\2\2\62\u01a3\3\2\2\2\64\u01a5"+
		"\3\2\2\2\66\u01a8\3\2\2\28\u01ab\3\2\2\2:\u01b9\3\2\2\2<\u01c6\3\2\2\2"+
		">\u01c8\3\2\2\2@\u01de\3\2\2\2B\u01e0\3\2\2\2D\u01e2\3\2\2\2F\u01e4\3"+
		"\2\2\2H\u01e6\3\2\2\2J\u01e8\3\2\2\2L\u01ea\3\2\2\2N\u01ec\3\2\2\2P\u01ee"+
		"\3\2\2\2R\u01f0\3\2\2\2T\u01f2\3\2\2\2V\u01f4\3\2\2\2X\u01f6\3\2\2\2Z"+
		"[\5\4\3\2[\\\7\2\2\3\\\3\3\2\2\2]_\5\6\4\2^]\3\2\2\2_b\3\2\2\2`^\3\2\2"+
		"\2`a\3\2\2\2ad\3\2\2\2b`\3\2\2\2ce\5\30\r\2dc\3\2\2\2de\3\2\2\2e\5\3\2"+
		"\2\2f\u00d7\7\3\2\2gh\5\36\20\2hi\7\4\2\2ij\5\"\22\2j\u00d7\3\2\2\2k\u00d7"+
		"\5(\25\2l\u00d7\5\32\16\2m\u00d7\7\5\2\2n\u00d7\7\6\2\2op\7\7\2\2p\u00d7"+
		"\7C\2\2qr\7\b\2\2rs\5\4\3\2st\7\t\2\2t\u00d7\3\2\2\2uv\7\n\2\2vw\5$\23"+
		"\2wx\7\b\2\2xy\5\4\3\2yz\7\t\2\2z\u00d7\3\2\2\2{|\7\13\2\2|}\5\4\3\2}"+
		"~\7\f\2\2~\177\5$\23\2\177\u00d7\3\2\2\2\u0080\u0081\7\r\2\2\u0081\u0083"+
		"\5$\23\2\u0082\u0084\7\16\2\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2"+
		"\u0084\u0085\3\2\2\2\u0085\u0089\5\b\5\2\u0086\u0088\5\n\6\2\u0087\u0086"+
		"\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a"+
		"\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008e\5\f\7\2\u008d\u008c\3\2"+
		"\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090\7\t\2\2\u0090"+
		"\u00d7\3\2\2\2\u0091\u0092\7\17\2\2\u0092\u0093\7C\2\2\u0093\u0094\7\4"+
		"\2\2\u0094\u0095\5$\23\2\u0095\u0096\7\20\2\2\u0096\u0099\5$\23\2\u0097"+
		"\u0098\7\20\2\2\u0098\u009a\5$\23\2\u0099\u0097\3\2\2\2\u0099\u009a\3"+
		"\2\2\2\u009a\u009c\3\2\2\2\u009b\u009d\7\b\2\2\u009c\u009b\3\2\2\2\u009c"+
		"\u009d\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\5\4\3\2\u009f\u00a0\7\t"+
		"\2\2\u00a0\u00d7\3\2\2\2\u00a1\u00a2\7\17\2\2\u00a2\u00a3\5 \21\2\u00a3"+
		"\u00a4\7\21\2\2\u00a4\u00a6\5\"\22\2\u00a5\u00a7\7\b\2\2\u00a6\u00a5\3"+
		"\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\5\4\3\2\u00a9"+
		"\u00aa\7\t\2\2\u00aa\u00d7\3\2\2\2\u00ab\u00ac\7\22\2\2\u00ac\u00ad\5"+
		"\34\17\2\u00ad\u00ae\58\35\2\u00ae\u00d7\3\2\2\2\u00af\u00b0\t\2\2\2\u00b0"+
		"\u00b1\7\22\2\2\u00b1\u00b2\7C\2\2\u00b2\u00d7\58\35\2\u00b3\u00b4\t\2"+
		"\2\2\u00b4\u00b7\5\24\13\2\u00b5\u00b6\7\4\2\2\u00b6\u00b8\5\"\22\2\u00b7"+
		"\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00d7\3\2\2\2\u00b9\u00ba\7\25"+
		"\2\2\u00ba\u00bc\5$\23\2\u00bb\u00bd\7\b\2\2\u00bc\u00bb\3\2\2\2\u00bc"+
		"\u00bd\3\2\2\2\u00bd\u00c1\3\2\2\2\u00be\u00c0\5\20\t\2\u00bf\u00be\3"+
		"\2\2\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"+
		"\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c6\5\16\b\2\u00c5\u00c4\3"+
		"\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\7\t\2\2\u00c8"+
		"\u00d7\3\2\2\2\u00c9\u00ca\7\26\2\2\u00ca\u00cb\5$\23\2\u00cb\u00d0\5"+
		"&\24\2\u00cc\u00ce\7\27\2\2\u00cd\u00cc\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce"+
		"\u00cf\3\2\2\2\u00cf\u00d1\5&\24\2\u00d0\u00cd\3\2\2\2\u00d0\u00d1\3\2"+
		"\2\2\u00d1\u00d7\3\2\2\2\u00d2\u00d3\7\30\2\2\u00d3\u00d7\5\22\n\2\u00d4"+
		"\u00d5\7\31\2\2\u00d5\u00d7\5(\25\2\u00d6f\3\2\2\2\u00d6g\3\2\2\2\u00d6"+
		"k\3\2\2\2\u00d6l\3\2\2\2\u00d6m\3\2\2\2\u00d6n\3\2\2\2\u00d6o\3\2\2\2"+
		"\u00d6q\3\2\2\2\u00d6u\3\2\2\2\u00d6{\3\2\2\2\u00d6\u0080\3\2\2\2\u00d6"+
		"\u0091\3\2\2\2\u00d6\u00a1\3\2\2\2\u00d6\u00ab\3\2\2\2\u00d6\u00af\3\2"+
		"\2\2\u00d6\u00b3\3\2\2\2\u00d6\u00b9\3\2\2\2\u00d6\u00c9\3\2\2\2\u00d6"+
		"\u00d2\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\7\3\2\2\2\u00d8\u00d9\5\4\3\2"+
		"\u00d9\t\3\2\2\2\u00da\u00db\7\32\2\2\u00db\u00dc\5$\23\2\u00dc\u00dd"+
		"\7\16\2\2\u00dd\u00de\5\4\3\2\u00de\13\3\2\2\2\u00df\u00e0\7\27\2\2\u00e0"+
		"\u00e1\5\4\3\2\u00e1\r\3\2\2\2\u00e2\u00e3\7\33\2\2\u00e3\u00e4\5\4\3"+
		"\2\u00e4\17\3\2\2\2\u00e5\u00e6\7\34\2\2\u00e6\u00e8\5:\36\2\u00e7\u00e9"+
		"\t\3\2\2\u00e8\u00e7\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea"+
		"\u00eb\5\4\3\2\u00eb\21\3\2\2\2\u00ec\u00ed\5:\36\2\u00ed\u00ee\7\35\2"+
		"\2\u00ee\u00ef\5\"\22\2\u00ef\23\3\2\2\2\u00f0\u00f1\7C\2\2\u00f1\u00f7"+
		"\5\26\f\2\u00f2\u00f3\7\20\2\2\u00f3\u00f4\7C\2\2\u00f4\u00f6\5\26\f\2"+
		"\u00f5\u00f2\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8"+
		"\3\2\2\2\u00f8\25\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00fb\7\36\2\2\u00fb"+
		"\u00fc\7C\2\2\u00fc\u00fe\7\37\2\2\u00fd\u00fa\3\2\2\2\u00fd\u00fe\3\2"+
		"\2\2\u00fe\27\3\2\2\2\u00ff\u0101\7 \2\2\u0100\u0102\5\"\22\2\u0101\u0100"+
		"\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0104\3\2\2\2\u0103\u0105\7\3\2\2\u0104"+
		"\u0103\3\2\2\2\u0104\u0105\3\2\2\2\u0105\31\3\2\2\2\u0106\u0107\7!\2\2"+
		"\u0107\u0108\7C\2\2\u0108\u010c\7!\2\2\u0109\u010a\7\"\2\2\u010a\u010c"+
		"\7C\2\2\u010b\u0106\3\2\2\2\u010b\u0109\3\2\2\2\u010c\33\3\2\2\2\u010d"+
		"\u0112\7C\2\2\u010e\u010f\7#\2\2\u010f\u0111\7C\2\2\u0110\u010e\3\2\2"+
		"\2\u0111\u0114\3\2\2\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0117"+
		"\3\2\2\2\u0114\u0112\3\2\2\2\u0115\u0116\7\35\2\2\u0116\u0118\7C\2\2\u0117"+
		"\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118\35\3\2\2\2\u0119\u011e\5,\27"+
		"\2\u011a\u011b\7\20\2\2\u011b\u011d\5,\27\2\u011c\u011a\3\2\2\2\u011d"+
		"\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f\37\3\2\2"+
		"\2\u0120\u011e\3\2\2\2\u0121\u0126\7C\2\2\u0122\u0123\7\20\2\2\u0123\u0125"+
		"\7C\2\2\u0124\u0122\3\2\2\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2\u0126"+
		"\u0127\3\2\2\2\u0127!\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012e\5$\23\2"+
		"\u012a\u012b\7\20\2\2\u012b\u012d\5$\23\2\u012c\u012a\3\2\2\2\u012d\u0130"+
		"\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f#\3\2\2\2\u0130"+
		"\u012e\3\2\2\2\u0131\u0132\b\23\1\2\u0132\u0140\7$\2\2\u0133\u0140\7%"+
		"\2\2\u0134\u0140\7&\2\2\u0135\u0140\5V,\2\u0136\u0140\5X-\2\u0137\u0140"+
		"\7\'\2\2\u0138\u0140\5\64\33\2\u0139\u0140\5&\24\2\u013a\u0140\5<\37\2"+
		"\u013b\u013c\5R*\2\u013c\u013d\5$\23\13\u013d\u0140\3\2\2\2\u013e\u0140"+
		"\5\66\34\2\u013f\u0131\3\2\2\2\u013f\u0133\3\2\2\2\u013f\u0134\3\2\2\2"+
		"\u013f\u0135\3\2\2\2\u013f\u0136\3\2\2\2\u013f\u0137\3\2\2\2\u013f\u0138"+
		"\3\2\2\2\u013f\u0139\3\2\2\2\u013f\u013a\3\2\2\2\u013f\u013b\3\2\2\2\u013f"+
		"\u013e\3\2\2\2\u0140\u0163\3\2\2\2\u0141\u0142\f\f\2\2\u0142\u0143\5T"+
		"+\2\u0143\u0144\5$\23\f\u0144\u0162\3\2\2\2\u0145\u0146\f\n\2\2\u0146"+
		"\u0147\5N(\2\u0147\u0148\5$\23\13\u0148\u0162\3\2\2\2\u0149\u014a\f\t"+
		"\2\2\u014a\u014b\5L\'\2\u014b\u014c\5$\23\n\u014c\u0162\3\2\2\2\u014d"+
		"\u014e\f\b\2\2\u014e\u014f\5J&\2\u014f\u0150\5$\23\b\u0150\u0162\3\2\2"+
		"\2\u0151\u0152\f\7\2\2\u0152\u0153\5H%\2\u0153\u0154\5$\23\b\u0154\u0162"+
		"\3\2\2\2\u0155\u0156\f\6\2\2\u0156\u0157\5F$\2\u0157\u0158\5$\23\7\u0158"+
		"\u0162\3\2\2\2\u0159\u015a\f\5\2\2\u015a\u015b\5D#\2\u015b\u015c\5$\23"+
		"\6\u015c\u0162\3\2\2\2\u015d\u015e\f\4\2\2\u015e\u015f\5P)\2\u015f\u0160"+
		"\5$\23\5\u0160\u0162\3\2\2\2\u0161\u0141\3\2\2\2\u0161\u0145\3\2\2\2\u0161"+
		"\u0149\3\2\2\2\u0161\u014d\3\2\2\2\u0161\u0151\3\2\2\2\u0161\u0155\3\2"+
		"\2\2\u0161\u0159\3\2\2\2\u0161\u015d\3\2\2\2\u0162\u0165\3\2\2\2\u0163"+
		"\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164%\3\2\2\2\u0165\u0163\3\2\2\2"+
		"\u0166\u016a\5*\26\2\u0167\u0169\5\60\31\2\u0168\u0167\3\2\2\2\u0169\u016c"+
		"\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2\2\2\u016b\'\3\2\2\2\u016c"+
		"\u016a\3\2\2\2\u016d\u016f\5*\26\2\u016e\u0170\5\60\31\2\u016f\u016e\3"+
		"\2\2\2\u0170\u0171\3\2\2\2\u0171\u016f\3\2\2\2\u0171\u0172\3\2\2\2\u0172"+
		")\3\2\2\2\u0173\u0179\5,\27\2\u0174\u0175\7(\2\2\u0175\u0176\5$\23\2\u0176"+
		"\u0177\7)\2\2\u0177\u0179\3\2\2\2\u0178\u0173\3\2\2\2\u0178\u0174\3\2"+
		"\2\2\u0179+\3\2\2\2\u017a\u0181\7C\2\2\u017b\u017c\7(\2\2\u017c\u017d"+
		"\5$\23\2\u017d\u017e\7)\2\2\u017e\u017f\5.\30\2\u017f\u0181\3\2\2\2\u0180"+
		"\u017a\3\2\2\2\u0180\u017b\3\2\2\2\u0181\u0185\3\2\2\2\u0182\u0184\5."+
		"\30\2\u0183\u0182\3\2\2\2\u0184\u0187\3\2\2\2\u0185\u0183\3\2\2\2\u0185"+
		"\u0186\3\2\2\2\u0186-\3\2\2\2\u0187\u0185\3\2\2\2\u0188\u018a\5\60\31"+
		"\2\u0189\u0188\3\2\2\2\u018a\u018d\3\2\2\2\u018b\u0189\3\2\2\2\u018b\u018c"+
		"\3\2\2\2\u018c\u0194\3\2\2\2\u018d\u018b\3\2\2\2\u018e\u018f\7*\2\2\u018f"+
		"\u0190\5$\23\2\u0190\u0191\7+\2\2\u0191\u0195\3\2\2\2\u0192\u0193\7#\2"+
		"\2\u0193\u0195\7C\2\2\u0194\u018e\3\2\2\2\u0194\u0192\3\2\2\2\u0195/\3"+
		"\2\2\2\u0196\u0197\7\35\2\2\u0197\u0199\7C\2\2\u0198\u0196\3\2\2\2\u0198"+
		"\u0199\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b\5\62\32\2\u019b\61\3\2"+
		"\2\2\u019c\u019e\7(\2\2\u019d\u019f\5\"\22\2\u019e\u019d\3\2\2\2\u019e"+
		"\u019f\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a4\7)\2\2\u01a1\u01a4\5<\37"+
		"\2\u01a2\u01a4\5X-\2\u01a3\u019c\3\2\2\2\u01a3\u01a1\3\2\2\2\u01a3\u01a2"+
		"\3\2\2\2\u01a4\63\3\2\2\2\u01a5\u01a6\7\22\2\2\u01a6\u01a7\58\35\2\u01a7"+
		"\65\3\2\2\2\u01a8\u01a9\7\30\2\2\u01a9\u01aa\5\22\n\2\u01aa\67\3\2\2\2"+
		"\u01ab\u01ad\7(\2\2\u01ac\u01ae\5:\36\2\u01ad\u01ac\3\2\2\2\u01ad\u01ae"+
		"\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u01b0\7)\2\2\u01b0\u01b1\5\4\3\2\u01b1"+
		"\u01b2\7\t\2\2\u01b29\3\2\2\2\u01b3\u01b6\5 \21\2\u01b4\u01b5\7\20\2\2"+
		"\u01b5\u01b7\7\'\2\2\u01b6\u01b4\3\2\2\2\u01b6\u01b7\3\2\2\2\u01b7\u01ba"+
		"\3\2\2\2\u01b8\u01ba\7\'\2\2\u01b9\u01b3\3\2\2\2\u01b9\u01b8\3\2\2\2\u01ba"+
		";\3\2\2\2\u01bb\u01bd\7,\2\2\u01bc\u01be\5> \2\u01bd\u01bc\3\2\2\2\u01bd"+
		"\u01be\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01c7\7-\2\2\u01c0\u01c1\7C\2"+
		"\2\u01c1\u01c3\7*\2\2\u01c2\u01c4\5V,\2\u01c3\u01c2\3\2\2\2\u01c3\u01c4"+
		"\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01c7\7+\2\2\u01c6\u01bb\3\2\2\2\u01c6"+
		"\u01c0\3\2\2\2\u01c7=\3\2\2\2\u01c8\u01ce\5@!\2\u01c9\u01ca\5B\"\2\u01ca"+
		"\u01cb\5@!\2\u01cb\u01cd\3\2\2\2\u01cc\u01c9\3\2\2\2\u01cd\u01d0\3\2\2"+
		"\2\u01ce\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d2\3\2\2\2\u01d0\u01ce"+
		"\3\2\2\2\u01d1\u01d3\5B\"\2\u01d2\u01d1\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3"+
		"?\3\2\2\2\u01d4\u01d5\7*\2\2\u01d5\u01d6\5$\23\2\u01d6\u01d7\7+\2\2\u01d7"+
		"\u01d8\7\4\2\2\u01d8\u01d9\5$\23\2\u01d9\u01df\3\2\2\2\u01da\u01db\7C"+
		"\2\2\u01db\u01dc\7\4\2\2\u01dc\u01df\5$\23\2\u01dd\u01df\5$\23\2\u01de"+
		"\u01d4\3\2\2\2\u01de\u01da\3\2\2\2\u01de\u01dd\3\2\2\2\u01dfA\3\2\2\2"+
		"\u01e0\u01e1\t\4\2\2\u01e1C\3\2\2\2\u01e2\u01e3\7.\2\2\u01e3E\3\2\2\2"+
		"\u01e4\u01e5\7/\2\2\u01e5G\3\2\2\2\u01e6\u01e7\t\5\2\2\u01e7I\3\2\2\2"+
		"\u01e8\u01e9\7\64\2\2\u01e9K\3\2\2\2\u01ea\u01eb\t\6\2\2\u01ebM\3\2\2"+
		"\2\u01ec\u01ed\t\7\2\2\u01edO\3\2\2\2\u01ee\u01ef\t\b\2\2\u01efQ\3\2\2"+
		"\2\u01f0\u01f1\t\t\2\2\u01f1S\3\2\2\2\u01f2\u01f3\7B\2\2\u01f3U\3\2\2"+
		"\2\u01f4\u01f5\t\n\2\2\u01f5W\3\2\2\2\u01f6\u01f7\t\13\2\2\u01f7Y\3\2"+
		"\2\2\62`d\u0083\u0089\u008d\u0099\u009c\u00a6\u00b7\u00bc\u00c1\u00c5"+
		"\u00cd\u00d0\u00d6\u00e8\u00f7\u00fd\u0101\u0104\u010b\u0112\u0117\u011e"+
		"\u0126\u012e\u013f\u0161\u0163\u016a\u0171\u0178\u0180\u0185\u018b\u0194"+
		"\u0198\u019e\u01a3\u01ad\u01b6\u01b9\u01bd\u01c3\u01c6\u01ce\u01d2\u01de";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}