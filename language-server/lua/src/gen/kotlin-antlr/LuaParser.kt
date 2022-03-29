// Generated from java-escape by ANTLR 4.7.1
package com.dingyi.myluaapp.editor.lsp.server.lua.common.parser;
import com.strumenta.kotlinmultiplatform.asCharArray
import com.strumenta.kotlinmultiplatform.getType
import com.strumenta.kotlinmultiplatform.TypeDeclarator
import org.antlr.v4.kotlinruntime.*
import org.antlr.v4.kotlinruntime.atn.*
import org.antlr.v4.kotlinruntime.atn.ATNDeserializer
import org.antlr.v4.kotlinruntime.atn.ParserATNSimulator
import org.antlr.v4.kotlinruntime.atn.PredictionContextCache
import org.antlr.v4.kotlinruntime.dfa.*
import org.antlr.v4.kotlinruntime.tree.ParseTreeListener
import org.antlr.v4.kotlinruntime.tree.TerminalNode
import org.antlr.v4.kotlinruntime.atn.ATN.Companion.INVALID_ALT_NUMBER
import org.antlr.v4.kotlinruntime.tree.ParseTreeVisitor
import kotlin.reflect.KClass

class LuaParser(input: TokenStream) : Parser(input) {

    object solver : TypeDeclarator {
        override val classesByName : List<KClass<*>> = listOf(LuaParser.ChunkContext::class,
                                                              LuaParser.BlockContext::class,
                                                              LuaParser.StatContext::class,
                                                              LuaParser.CommentContext::class,
                                                              LuaParser.IfbodyContext::class,
                                                              LuaParser.ElseifbodyContext::class,
                                                              LuaParser.ElsebodyContext::class,
                                                              LuaParser.DefaultbodyContext::class,
                                                              LuaParser.CasebodyContext::class,
                                                              LuaParser.CaseexpContext::class,
                                                              LuaParser.LambdabodyContext::class,
                                                              LuaParser.AttnamelistContext::class,
                                                              LuaParser.AttribContext::class,
                                                              LuaParser.RetstatContext::class,
                                                              LuaParser.LabelContext::class,
                                                              LuaParser.FuncnameContext::class,
                                                              LuaParser.Funcname_selfContext::class,
                                                              LuaParser.VarlistContext::class,
                                                              LuaParser.NamelistContext::class,
                                                              LuaParser.ExplistContext::class,
                                                              LuaParser.ExpContext::class,
                                                              LuaParser.PrefixexpContext::class,
                                                              LuaParser.FunctioncallContext::class,
                                                              LuaParser.VarOrExpContext::class,
                                                              LuaParser.LvarContext::class,
                                                              LuaParser.VarSuffixContext::class,
                                                              LuaParser.NameAndArgsContext::class,
                                                              LuaParser.ArgsContext::class,
                                                              LuaParser.FunctiondefContext::class,
                                                              LuaParser.LambdadefContext::class,
                                                              LuaParser.FuncbodyContext::class,
                                                              LuaParser.ParlistContext::class,
                                                              LuaParser.TableconstructorContext::class,
                                                              LuaParser.FieldlistContext::class,
                                                              LuaParser.FieldContext::class,
                                                              LuaParser.FieldsepContext::class,
                                                              LuaParser.OperatorOrContext::class,
                                                              LuaParser.OperatorAndContext::class,
                                                              LuaParser.OperatorComparisonContext::class,
                                                              LuaParser.OperatorStrcatContext::class,
                                                              LuaParser.OperatorAddSubContext::class,
                                                              LuaParser.OperatorMulDivModContext::class,
                                                              LuaParser.OperatorBitwiseContext::class,
                                                              LuaParser.OperatorUnaryContext::class,
                                                              LuaParser.OperatorPowerContext::class,
                                                              LuaParser.NumberContext::class,
                                                              LuaParser.StringContext::class)
    }

	// TODO verify version of runtime is compatible

    override val grammarFileName: String
        get() = "Lua.g4"

    override val tokenNames: Array<String?>?
        get() = LuaParser.Companion.tokenNames
    override val ruleNames: Array<String>?
        get() = LuaParser.Companion.ruleNames
    override val atn: ATN
        get() = LuaParser.Companion.ATN
    override val vocabulary: Vocabulary
        get() = LuaParser.Companion.VOCABULARY

    enum class Tokens(val id: Int) {
        EOF(-1),
        T__0(1),
        T__1(2),
        T__2(3),
        T__3(4),
        T__4(5),
        T__5(6),
        T__6(7),
        T__7(8),
        T__8(9),
        T__9(10),
        T__10(11),
        T__11(12),
        T__12(13),
        T__13(14),
        T__14(15),
        T__15(16),
        T__16(17),
        T__17(18),
        T__18(19),
        T__19(20),
        T__20(21),
        T__21(22),
        T__22(23),
        T__23(24),
        T__24(25),
        T__25(26),
        T__26(27),
        T__27(28),
        T__28(29),
        T__29(30),
        T__30(31),
        T__31(32),
        T__32(33),
        T__33(34),
        T__34(35),
        T__35(36),
        T__36(37),
        T__37(38),
        T__38(39),
        T__39(40),
        T__40(41),
        T__41(42),
        T__42(43),
        T__43(44),
        T__44(45),
        T__45(46),
        T__46(47),
        T__47(48),
        T__48(49),
        T__49(50),
        T__50(51),
        T__51(52),
        T__52(53),
        T__53(54),
        T__54(55),
        T__55(56),
        T__56(57),
        T__57(58),
        T__58(59),
        T__59(60),
        T__60(61),
        T__61(62),
        T__62(63),
        NAME(64),
        NORMALSTRING(65),
        CHARSTRING(66),
        LONGSTRING(67),
        INT(68),
        HEX(69),
        FLOAT(70),
        HEX_FLOAT(71),
        COMMENT(72),
        LINE_COMMENT(73),
        WS(74),
        SHEBANG(75)
    }

    enum class Rules(val id: Int) {
        RULE_chunk(0),
        RULE_block(1),
        RULE_stat(2),
        RULE_comment(3),
        RULE_ifbody(4),
        RULE_elseifbody(5),
        RULE_elsebody(6),
        RULE_defaultbody(7),
        RULE_casebody(8),
        RULE_caseexp(9),
        RULE_lambdabody(10),
        RULE_attnamelist(11),
        RULE_attrib(12),
        RULE_retstat(13),
        RULE_label(14),
        RULE_funcname(15),
        RULE_funcname_self(16),
        RULE_varlist(17),
        RULE_namelist(18),
        RULE_explist(19),
        RULE_exp(20),
        RULE_prefixexp(21),
        RULE_functioncall(22),
        RULE_varOrExp(23),
        RULE_lvar(24),
        RULE_varSuffix(25),
        RULE_nameAndArgs(26),
        RULE_args(27),
        RULE_functiondef(28),
        RULE_lambdadef(29),
        RULE_funcbody(30),
        RULE_parlist(31),
        RULE_tableconstructor(32),
        RULE_fieldlist(33),
        RULE_field(34),
        RULE_fieldsep(35),
        RULE_operatorOr(36),
        RULE_operatorAnd(37),
        RULE_operatorComparison(38),
        RULE_operatorStrcat(39),
        RULE_operatorAddSub(40),
        RULE_operatorMulDivMod(41),
        RULE_operatorBitwise(42),
        RULE_operatorUnary(43),
        RULE_operatorPower(44),
        RULE_number(45),
        RULE_string(46)
    }

	@ThreadLocal
	companion object {
	    protected val decisionToDFA : Array<DFA>
    	protected val sharedContextCache = PredictionContextCache()

        val ruleNames = arrayOf("chunk", "block", "stat", "comment", "ifbody", 
                                "elseifbody", "elsebody", "defaultbody", 
                                "casebody", "caseexp", "lambdabody", "attnamelist", 
                                "attrib", "retstat", "label", "funcname", 
                                "funcname_self", "varlist", "namelist", 
                                "explist", "exp", "prefixexp", "functioncall", 
                                "varOrExp", "lvar", "varSuffix", "nameAndArgs", 
                                "args", "functiondef", "lambdadef", "funcbody", 
                                "parlist", "tableconstructor", "fieldlist", 
                                "field", "fieldsep", "operatorOr", "operatorAnd", 
                                "operatorComparison", "operatorStrcat", 
                                "operatorAddSub", "operatorMulDivMod", "operatorBitwise", 
                                "operatorUnary", "operatorPower", "number", 
                                "string")

        private val LITERAL_NAMES: List<String?> = listOf(null, "';'", "'='", 
                                                          "'break'", "'continue'", 
                                                          "'goto'", "'do'", 
                                                          "'end'", "'while'", 
                                                          "'repeat'", "'until'", 
                                                          "'if'", "'then'", 
                                                          "'for'", "','", 
                                                          "'in'", "'function'", 
                                                          "'local'", "'switch'", 
                                                          "'when'", "'else'", 
                                                          "'lambda'", "'defer'", 
                                                          "'return'", "'elseif'", 
                                                          "'default'", "':'", 
                                                          "'case'", "'<'", 
                                                          "'>'", "'::'", 
                                                          "'@'", "'.'", 
                                                          "'nil'", "'false'", 
                                                          "'true'", "'...'", 
                                                          "'('", "')'", 
                                                          "'['", "']'", 
                                                          "'{'", "'}'", 
                                                          "'or'", "'and'", 
                                                          "'<='", "'>='", 
                                                          "'~='", "'=='", 
                                                          "'..'", "'+'", 
                                                          "'-'", "'*'", 
                                                          "'/'", "'%'", 
                                                          "'//'", "'&'", 
                                                          "'|'", "'~'", 
                                                          "'<<'", "'>>'", 
                                                          "'not'", "'#'", 
                                                          "'^'")
        private val SYMBOLIC_NAMES: List<String?> = listOf(null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, "NAME", 
                                                           "NORMALSTRING", 
                                                           "CHARSTRING", 
                                                           "LONGSTRING", 
                                                           "INT", "HEX", 
                                                           "FLOAT", "HEX_FLOAT", 
                                                           "COMMENT", "LINE_COMMENT", 
                                                           "WS", "SHEBANG")

        val VOCABULARY = VocabularyImpl(LITERAL_NAMES.toTypedArray(), SYMBOLIC_NAMES.toTypedArray())

        val tokenNames: Array<String?> = Array<String?>(SYMBOLIC_NAMES.size) {
            var el = VOCABULARY.getLiteralName(it)
            if (el == null) {
                el = VOCABULARY.getSymbolicName(it)
            }

            if (el == null) {
                el = "<INVALID>"
            }
            el
        }

        private const val serializedATN : String = "\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\u0003\u004d\u0214\u0004\u0002\u0009\u0002\u0004\u0003\u0009\u0003\u0004\u0004\u0009\u0004\u0004\u0005\u0009\u0005\u0004\u0006\u0009\u0006\u0004\u0007\u0009\u0007\u0004\u0008\u0009\u0008\u0004\u0009\u0009\u0009\u0004\u000a\u0009\u000a\u0004\u000b\u0009\u000b\u0004\u000c\u0009\u000c\u0004\u000d\u0009\u000d\u0004\u000e\u0009\u000e\u0004\u000f\u0009\u000f\u0004\u0010\u0009\u0010\u0004\u0011\u0009\u0011\u0004\u0012\u0009\u0012\u0004\u0013\u0009\u0013\u0004\u0014\u0009\u0014\u0004\u0015\u0009\u0015\u0004\u0016\u0009\u0016\u0004\u0017\u0009\u0017\u0004\u0018\u0009\u0018\u0004\u0019\u0009\u0019\u0004\u001a\u0009\u001a\u0004\u001b\u0009\u001b\u0004\u001c\u0009\u001c\u0004\u001d\u0009\u001d\u0004\u001e\u0009\u001e\u0004\u001f\u0009\u001f\u0004\u0020\u0009\u0020\u0004\u0021\u0009\u0021\u0004\u0022\u0009\u0022\u0004\u0023\u0009\u0023\u0004\u0024\u0009\u0024\u0004\u0025\u0009\u0025\u0004\u0026\u0009\u0026\u0004\u0027\u0009\u0027\u0004\u0028\u0009\u0028\u0004\u0029\u0009\u0029\u0004\u002a\u0009\u002a\u0004\u002b\u0009\u002b\u0004\u002c\u0009\u002c\u0004\u002d\u0009\u002d\u0004\u002e\u0009\u002e\u0004\u002f\u0009\u002f\u0004\u0030\u0009\u0030\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0007\u0003\u0065\u000a\u0003\u000c\u0003\u000e\u0003\u0068\u000b\u0003\u0003\u0003\u0005\u0003\u006b\u000a\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u008a\u000a\u0004\u0003\u0004\u0003\u0004\u0007\u0004\u008e\u000a\u0004\u000c\u0004\u000e\u0004\u0091\u000b\u0004\u0003\u0004\u0005\u0004\u0094\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00a0\u000a\u0004\u0003\u0004\u0005\u0004\u00a3\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00ad\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00be\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00c3\u000a\u0004\u0003\u0004\u0007\u0004\u00c6\u000a\u0004\u000c\u0004\u000e\u0004\u00c9\u000b\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00d2\u000a\u0004\u0003\u0004\u0005\u0004\u00d5\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00de\u000a\u0004\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0009\u0003\u0009\u0005\u0009\u00ee\u000a\u0009\u0003\u0009\u0003\u0009\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000b\u0003\u000b\u0005\u000b\u00f8\u000a\u000b\u0003\u000c\u0003\u000c\u0003\u000c\u0003\u000c\u0003\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u0103\u000a\u000d\u000c\u000d\u000e\u000d\u0106\u000b\u000d\u0003\u000e\u0003\u000e\u0003\u000e\u0005\u000e\u010b\u000a\u000e\u0003\u000f\u0003\u000f\u0005\u000f\u010f\u000a\u000f\u0003\u000f\u0005\u000f\u0112\u000a\u000f\u0003\u000f\u0007\u000f\u0115\u000a\u000f\u000c\u000f\u000e\u000f\u0118\u000b\u000f\u0005\u000f\u011a\u000a\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0005\u0010\u0121\u000a\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0007\u0011\u0126\u000a\u0011\u000c\u0011\u000e\u0011\u0129\u000b\u0011\u0003\u0011\u0005\u0011\u012c\u000a\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0007\u0013\u0134\u000a\u0013\u000c\u0013\u000e\u0013\u0137\u000b\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0007\u0014\u013c\u000a\u0014\u000c\u0014\u000e\u0014\u013f\u000b\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0007\u0015\u0144\u000a\u0015\u000c\u0015\u000e\u0015\u0147\u000b\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0005\u0016\u0158\u000a\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0007\u0016\u017a\u000a\u0016\u000c\u0016\u000e\u0016\u017d\u000b\u0016\u0003\u0017\u0003\u0017\u0007\u0017\u0181\u000a\u0017\u000c\u0017\u000e\u0017\u0184\u000b\u0017\u0003\u0018\u0003\u0018\u0006\u0018\u0188\u000a\u0018\u000d\u0018\u000e\u0018\u0189\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0005\u0019\u0191\u000a\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0005\u001a\u0199\u000a\u001a\u0003\u001a\u0007\u001a\u019c\u000a\u001a\u000c\u001a\u000e\u001a\u019f\u000b\u001a\u0003\u001b\u0007\u001b\u01a2\u000a\u001b\u000c\u001b\u000e\u001b\u01a5\u000b\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u01ad\u000a\u001b\u0003\u001b\u0005\u001b\u01b0\u000a\u001b\u0003\u001c\u0003\u001c\u0005\u001c\u01b4\u000a\u001c\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0005\u001d\u01ba\u000a\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u01bf\u000a\u001d\u0003\u001e\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u0020\u0003\u0020\u0005\u0020\u01c9\u000a\u0020\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0021\u0003\u0021\u0003\u0021\u0005\u0021\u01d2\u000a\u0021\u0003\u0021\u0005\u0021\u01d5\u000a\u0021\u0003\u0022\u0003\u0022\u0005\u0022\u01d9\u000a\u0022\u0003\u0022\u0003\u0022\u0003\u0022\u0003\u0022\u0005\u0022\u01df\u000a\u0022\u0003\u0022\u0005\u0022\u01e2\u000a\u0022\u0003\u0023\u0003\u0023\u0003\u0023\u0003\u0023\u0007\u0023\u01e8\u000a\u0023\u000c\u0023\u000e\u0023\u01eb\u000b\u0023\u0003\u0023\u0005\u0023\u01ee\u000a\u0023\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0003\u0024\u0005\u0024\u01fa\u000a\u0024\u0003\u0025\u0003\u0025\u0003\u0026\u0003\u0026\u0003\u0027\u0003\u0027\u0003\u0028\u0003\u0028\u0003\u0029\u0003\u0029\u0003\u002a\u0003\u002a\u0003\u002b\u0003\u002b\u0003\u002c\u0003\u002c\u0003\u002d\u0003\u002d\u0003\u002e\u0003\u002e\u0003\u002f\u0003\u002f\u0003\u0030\u0003\u0030\u0003\u0030\u0002\u0003\u002a\u0031\u0002\u0004\u0006\u0008\u000a\u000c\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0020\u0022\u0024\u0026\u0028\u002a\u002c\u002e\u0030\u0032\u0034\u0036\u0038\u003a\u003c\u003e\u0040\u0042\u0044\u0046\u0048\u004a\u004c\u004e\u0050\u0052\u0054\u0056\u0058\u005a\u005c\u005e\u0002\u000c\u0003\u0002\u004a\u004b\u0004\u0002\u000e\u000e\u001c\u001c\u0004\u0002\u0003\u0003\u0010\u0010\u0004\u0002\u001e\u001f\u002f\u0032\u0003\u0002\u0034\u0035\u0003\u0002\u0036\u0039\u0003\u0002\u003a\u003e\u0005\u0002\u0035\u0035\u003c\u003c\u003f\u0040\u0003\u0002\u0046\u0049\u0003\u0002\u0043\u0045\u0002\u023d\u0002\u0060\u0003\u0002\u0002\u0002\u0004\u0066\u0003\u0002\u0002\u0002\u0006\u00dd\u0003\u0002\u0002\u0002\u0008\u00df\u0003\u0002\u0002\u0002\u000a\u00e1\u0003\u0002\u0002\u0002\u000c\u00e3\u0003\u0002\u0002\u0002\u000e\u00e8\u0003\u0002\u0002\u0002\u0010\u00eb\u0003\u0002\u0002\u0002\u0012\u00f1\u0003\u0002\u0002\u0002\u0014\u00f5\u0003\u0002\u0002\u0002\u0016\u00f9\u0003\u0002\u0002\u0002\u0018\u00fd\u0003\u0002\u0002\u0002\u001a\u010a\u0003\u0002\u0002\u0002\u001c\u010c\u0003\u0002\u0002\u0002\u001e\u0120\u0003\u0002\u0002\u0002\u0020\u0122\u0003\u0002\u0002\u0002\u0022\u012d\u0003\u0002\u0002\u0002\u0024\u0130\u0003\u0002\u0002\u0002\u0026\u0138\u0003\u0002\u0002\u0002\u0028\u0140\u0003\u0002\u0002\u0002\u002a\u0157\u0003\u0002\u0002\u0002\u002c\u017e\u0003\u0002\u0002\u0002\u002e\u0185\u0003\u0002\u0002\u0002\u0030\u0190\u0003\u0002\u0002\u0002\u0032\u0198\u0003\u0002\u0002\u0002\u0034\u01a3\u0003\u0002\u0002\u0002\u0036\u01b3\u0003\u0002\u0002\u0002\u0038\u01be\u0003\u0002\u0002\u0002\u003a\u01c0\u0003\u0002\u0002\u0002\u003c\u01c3\u0003\u0002\u0002\u0002\u003e\u01c6\u0003\u0002\u0002\u0002\u0040\u01d4\u0003\u0002\u0002\u0002\u0042\u01e1\u0003\u0002\u0002\u0002\u0044\u01e3\u0003\u0002\u0002\u0002\u0046\u01f9\u0003\u0002\u0002\u0002\u0048\u01fb\u0003\u0002\u0002\u0002\u004a\u01fd\u0003\u0002\u0002\u0002\u004c\u01ff\u0003\u0002\u0002\u0002\u004e\u0201\u0003\u0002\u0002\u0002\u0050\u0203\u0003\u0002\u0002\u0002\u0052\u0205\u0003\u0002\u0002\u0002\u0054\u0207\u0003\u0002\u0002\u0002\u0056\u0209\u0003\u0002\u0002\u0002\u0058\u020b\u0003\u0002\u0002\u0002\u005a\u020d\u0003\u0002\u0002\u0002\u005c\u020f\u0003\u0002\u0002\u0002\u005e\u0211\u0003\u0002\u0002\u0002\u0060\u0061\u0005\u0004\u0003\u0002\u0061\u0062\u0007\u0002\u0002\u0003\u0062\u0003\u0003\u0002\u0002\u0002\u0063\u0065\u0005\u0006\u0004\u0002\u0064\u0063\u0003\u0002\u0002\u0002\u0065\u0068\u0003\u0002\u0002\u0002\u0066\u0064\u0003\u0002\u0002\u0002\u0066\u0067\u0003\u0002\u0002\u0002\u0067\u006a\u0003\u0002\u0002\u0002\u0068\u0066\u0003\u0002\u0002\u0002\u0069\u006b\u0005\u001c\u000f\u0002\u006a\u0069\u0003\u0002\u0002\u0002\u006a\u006b\u0003\u0002\u0002\u0002\u006b\u0005\u0003\u0002\u0002\u0002\u006c\u00de\u0007\u0003\u0002\u0002\u006d\u006e\u0005\u0024\u0013\u0002\u006e\u006f\u0007\u0004\u0002\u0002\u006f\u0070\u0005\u0028\u0015\u0002\u0070\u00de\u0003\u0002\u0002\u0002\u0071\u00de\u0005\u002e\u0018\u0002\u0072\u00de\u0005\u001e\u0010\u0002\u0073\u00de\u0007\u0005\u0002\u0002\u0074\u00de\u0007\u0006\u0002\u0002\u0075\u0076\u0007\u0007\u0002\u0002\u0076\u00de\u0007\u0042\u0002\u0002\u0077\u0078\u0007\u0008\u0002\u0002\u0078\u0079\u0005\u0004\u0003\u0002\u0079\u007a\u0007\u0009\u0002\u0002\u007a\u00de\u0003\u0002\u0002\u0002\u007b\u007c\u0007\u000a\u0002\u0002\u007c\u007d\u0005\u002a\u0016\u0002\u007d\u007e\u0007\u0008\u0002\u0002\u007e\u007f\u0005\u0004\u0003\u0002\u007f\u0080\u0007\u0009\u0002\u0002\u0080\u00de\u0003\u0002\u0002\u0002\u0081\u0082\u0007\u000b\u0002\u0002\u0082\u0083\u0005\u0004\u0003\u0002\u0083\u0084\u0007\u000c\u0002\u0002\u0084\u0085\u0005\u002a\u0016\u0002\u0085\u00de\u0003\u0002\u0002\u0002\u0086\u0087\u0007\u000d\u0002\u0002\u0087\u0089\u0005\u002a\u0016\u0002\u0088\u008a\u0007\u000e\u0002\u0002\u0089\u0088\u0003\u0002\u0002\u0002\u0089\u008a\u0003\u0002\u0002\u0002\u008a\u008b\u0003\u0002\u0002\u0002\u008b\u008f\u0005\u000a\u0006\u0002\u008c\u008e\u0005\u000c\u0007\u0002\u008d\u008c\u0003\u0002\u0002\u0002\u008e\u0091\u0003\u0002\u0002\u0002\u008f\u008d\u0003\u0002\u0002\u0002\u008f\u0090\u0003\u0002\u0002\u0002\u0090\u0093\u0003\u0002\u0002\u0002\u0091\u008f\u0003\u0002\u0002\u0002\u0092\u0094\u0005\u000e\u0008\u0002\u0093\u0092\u0003\u0002\u0002\u0002\u0093\u0094\u0003\u0002\u0002\u0002\u0094\u0095\u0003\u0002\u0002\u0002\u0095\u0096\u0007\u0009\u0002\u0002\u0096\u00de\u0003\u0002\u0002\u0002\u0097\u0098\u0007\u000f\u0002\u0002\u0098\u0099\u0007\u0042\u0002\u0002\u0099\u009a\u0007\u0004\u0002\u0002\u009a\u009b\u0005\u002a\u0016\u0002\u009b\u009c\u0007\u0010\u0002\u0002\u009c\u009f\u0005\u002a\u0016\u0002\u009d\u009e\u0007\u0010\u0002\u0002\u009e\u00a0\u0005\u002a\u0016\u0002\u009f\u009d\u0003\u0002\u0002\u0002\u009f\u00a0\u0003\u0002\u0002\u0002\u00a0\u00a2\u0003\u0002\u0002\u0002\u00a1\u00a3\u0007\u0008\u0002\u0002\u00a2\u00a1\u0003\u0002\u0002\u0002\u00a2\u00a3\u0003\u0002\u0002\u0002\u00a3\u00a4\u0003\u0002\u0002\u0002\u00a4\u00a5\u0005\u0004\u0003\u0002\u00a5\u00a6\u0007\u0009\u0002\u0002\u00a6\u00de\u0003\u0002\u0002\u0002\u00a7\u00a8\u0007\u000f\u0002\u0002\u00a8\u00a9\u0005\u0026\u0014\u0002\u00a9\u00aa\u0007\u0011\u0002\u0002\u00aa\u00ac\u0005\u0028\u0015\u0002\u00ab\u00ad\u0007\u0008\u0002\u0002\u00ac\u00ab\u0003\u0002\u0002\u0002\u00ac\u00ad\u0003\u0002\u0002\u0002\u00ad\u00ae\u0003\u0002\u0002\u0002\u00ae\u00af\u0005\u0004\u0003\u0002\u00af\u00b0\u0007\u0009\u0002\u0002\u00b0\u00de\u0003\u0002\u0002\u0002\u00b1\u00b2\u0007\u0012\u0002\u0002\u00b2\u00b3\u0005\u0020\u0011\u0002\u00b3\u00b4\u0005\u003e\u0020\u0002\u00b4\u00de\u0003\u0002\u0002\u0002\u00b5\u00b6\u0007\u0013\u0002\u0002\u00b6\u00b7\u0007\u0012\u0002\u0002\u00b7\u00b8\u0007\u0042\u0002\u0002\u00b8\u00de\u0005\u003e\u0020\u0002\u00b9\u00ba\u0007\u0013\u0002\u0002\u00ba\u00bd\u0005\u0018\u000d\u0002\u00bb\u00bc\u0007\u0004\u0002\u0002\u00bc\u00be\u0005\u0028\u0015\u0002\u00bd\u00bb\u0003\u0002\u0002\u0002\u00bd\u00be\u0003\u0002\u0002\u0002\u00be\u00de\u0003\u0002\u0002\u0002\u00bf\u00c0\u0007\u0014\u0002\u0002\u00c0\u00c2\u0005\u002a\u0016\u0002\u00c1\u00c3\u0007\u0008\u0002\u0002\u00c2\u00c1\u0003\u0002\u0002\u0002\u00c2\u00c3\u0003\u0002\u0002\u0002\u00c3\u00c7\u0003\u0002\u0002\u0002\u00c4\u00c6\u0005\u0012\u000a\u0002\u00c5\u00c4\u0003\u0002\u0002\u0002\u00c6\u00c9\u0003\u0002\u0002\u0002\u00c7\u00c5\u0003\u0002\u0002\u0002\u00c7\u00c8\u0003\u0002\u0002\u0002\u00c8\u00ca\u0003\u0002\u0002\u0002\u00c9\u00c7\u0003\u0002\u0002\u0002\u00ca\u00cb\u0005\u0010\u0009\u0002\u00cb\u00cc\u0007\u0009\u0002\u0002\u00cc\u00de\u0003\u0002\u0002\u0002\u00cd\u00ce\u0007\u0015\u0002\u0002\u00ce\u00cf\u0005\u002a\u0016\u0002\u00cf\u00d4\u0005\u002c\u0017\u0002\u00d0\u00d2\u0007\u0016\u0002\u0002\u00d1\u00d0\u0003\u0002\u0002\u0002\u00d1\u00d2\u0003\u0002\u0002\u0002\u00d2\u00d3\u0003\u0002\u0002\u0002\u00d3\u00d5\u0005\u002c\u0017\u0002\u00d4\u00d1\u0003\u0002\u0002\u0002\u00d4\u00d5\u0003\u0002\u0002\u0002\u00d5\u00de\u0003\u0002\u0002\u0002\u00d6\u00d7\u0007\u0017\u0002\u0002\u00d7\u00de\u0005\u0016\u000c\u0002\u00d8\u00d9\u0007\u0018\u0002\u0002\u00d9\u00de\u0005\u002e\u0018\u0002\u00da\u00db\u0007\u0019\u0002\u0002\u00db\u00de\u0005\u0004\u0003\u0002\u00dc\u00de\u0005\u0008\u0005\u0002\u00dd\u006c\u0003\u0002\u0002\u0002\u00dd\u006d\u0003\u0002\u0002\u0002\u00dd\u0071\u0003\u0002\u0002\u0002\u00dd\u0072\u0003\u0002\u0002\u0002\u00dd\u0073\u0003\u0002\u0002\u0002\u00dd\u0074\u0003\u0002\u0002\u0002\u00dd\u0075\u0003\u0002\u0002\u0002\u00dd\u0077\u0003\u0002\u0002\u0002\u00dd\u007b\u0003\u0002\u0002\u0002\u00dd\u0081\u0003\u0002\u0002\u0002\u00dd\u0086\u0003\u0002\u0002\u0002\u00dd\u0097\u0003\u0002\u0002\u0002\u00dd\u00a7\u0003\u0002\u0002\u0002\u00dd\u00b1\u0003\u0002\u0002\u0002\u00dd\u00b5\u0003\u0002\u0002\u0002\u00dd\u00b9\u0003\u0002\u0002\u0002\u00dd\u00bf\u0003\u0002\u0002\u0002\u00dd\u00cd\u0003\u0002\u0002\u0002\u00dd\u00d6\u0003\u0002\u0002\u0002\u00dd\u00d8\u0003\u0002\u0002\u0002\u00dd\u00da\u0003\u0002\u0002\u0002\u00dd\u00dc\u0003\u0002\u0002\u0002\u00de\u0007\u0003\u0002\u0002\u0002\u00df\u00e0\u0009\u0002\u0002\u0002\u00e0\u0009\u0003\u0002\u0002\u0002\u00e1\u00e2\u0005\u0004\u0003\u0002\u00e2\u000b\u0003\u0002\u0002\u0002\u00e3\u00e4\u0007\u001a\u0002\u0002\u00e4\u00e5\u0005\u002a\u0016\u0002\u00e5\u00e6\u0007\u000e\u0002\u0002\u00e6\u00e7\u0005\u0004\u0003\u0002\u00e7\u000d\u0003\u0002\u0002\u0002\u00e8\u00e9\u0007\u0016\u0002\u0002\u00e9\u00ea\u0005\u0004\u0003\u0002\u00ea\u000f\u0003\u0002\u0002\u0002\u00eb\u00ed\u0007\u001b\u0002\u0002\u00ec\u00ee\u0009\u0003\u0002\u0002\u00ed\u00ec\u0003\u0002\u0002\u0002\u00ed\u00ee\u0003\u0002\u0002\u0002\u00ee\u00ef\u0003\u0002\u0002\u0002\u00ef\u00f0\u0005\u0004\u0003\u0002\u00f0\u0011\u0003\u0002\u0002\u0002\u00f1\u00f2\u0007\u001d\u0002\u0002\u00f2\u00f3\u0005\u0014\u000b\u0002\u00f3\u00f4\u0005\u0004\u0003\u0002\u00f4\u0013\u0003\u0002\u0002\u0002\u00f5\u00f7\u0005\u0028\u0015\u0002\u00f6\u00f8\u0009\u0003\u0002\u0002\u00f7\u00f6\u0003\u0002\u0002\u0002\u00f7\u00f8\u0003\u0002\u0002\u0002\u00f8\u0015\u0003\u0002\u0002\u0002\u00f9\u00fa\u0005\u0040\u0021\u0002\u00fa\u00fb\u0007\u001c\u0002\u0002\u00fb\u00fc\u0005\u0028\u0015\u0002\u00fc\u0017\u0003\u0002\u0002\u0002\u00fd\u00fe\u0007\u0042\u0002\u0002\u00fe\u0104\u0005\u001a\u000e\u0002\u00ff\u0100\u0007\u0010\u0002\u0002\u0100\u0101\u0007\u0042\u0002\u0002\u0101\u0103\u0005\u001a\u000e\u0002\u0102\u00ff\u0003\u0002\u0002\u0002\u0103\u0106\u0003\u0002\u0002\u0002\u0104\u0102\u0003\u0002\u0002\u0002\u0104\u0105\u0003\u0002\u0002\u0002\u0105\u0019\u0003\u0002\u0002\u0002\u0106\u0104\u0003\u0002\u0002\u0002\u0107\u0108\u0007\u001e\u0002\u0002\u0108\u0109\u0007\u0042\u0002\u0002\u0109\u010b\u0007\u001f\u0002\u0002\u010a\u0107\u0003\u0002\u0002\u0002\u010a\u010b\u0003\u0002\u0002\u0002\u010b\u001b\u0003\u0002\u0002\u0002\u010c\u010e\u0007\u0019\u0002\u0002\u010d\u010f\u0005\u0028\u0015\u0002\u010e\u010d\u0003\u0002\u0002\u0002\u010e\u010f\u0003\u0002\u0002\u0002\u010f\u0111\u0003\u0002\u0002\u0002\u0110\u0112\u0007\u0003\u0002\u0002\u0111\u0110\u0003\u0002\u0002\u0002\u0111\u0112\u0003\u0002\u0002\u0002\u0112\u0119\u0003\u0002\u0002\u0002\u0113\u0115\u0005\u0008\u0005\u0002\u0114\u0113\u0003\u0002\u0002\u0002\u0115\u0118\u0003\u0002\u0002\u0002\u0116\u0114\u0003\u0002\u0002\u0002\u0116\u0117\u0003\u0002\u0002\u0002\u0117\u011a\u0003\u0002\u0002\u0002\u0118\u0116\u0003\u0002\u0002\u0002\u0119\u0116\u0003\u0002\u0002\u0002\u0119\u011a\u0003\u0002\u0002\u0002\u011a\u001d\u0003\u0002\u0002\u0002\u011b\u011c\u0007\u0020\u0002\u0002\u011c\u011d\u0007\u0042\u0002\u0002\u011d\u0121\u0007\u0020\u0002\u0002\u011e\u011f\u0007\u0021\u0002\u0002\u011f\u0121\u0007\u0042\u0002\u0002\u0120\u011b\u0003\u0002\u0002\u0002\u0120\u011e\u0003\u0002\u0002\u0002\u0121\u001f\u0003\u0002\u0002\u0002\u0122\u0127\u0007\u0042\u0002\u0002\u0123\u0124\u0007\u0022\u0002\u0002\u0124\u0126\u0007\u0042\u0002\u0002\u0125\u0123\u0003\u0002\u0002\u0002\u0126\u0129\u0003\u0002\u0002\u0002\u0127\u0125\u0003\u0002\u0002\u0002\u0127\u0128\u0003\u0002\u0002\u0002\u0128\u012b\u0003\u0002\u0002\u0002\u0129\u0127\u0003\u0002\u0002\u0002\u012a\u012c\u0005\u0022\u0012\u0002\u012b\u012a\u0003\u0002\u0002\u0002\u012b\u012c\u0003\u0002\u0002\u0002\u012c\u0021\u0003\u0002\u0002\u0002\u012d\u012e\u0007\u001c\u0002\u0002\u012e\u012f\u0007\u0042\u0002\u0002\u012f\u0023\u0003\u0002\u0002\u0002\u0130\u0135\u0005\u0032\u001a\u0002\u0131\u0132\u0007\u0010\u0002\u0002\u0132\u0134\u0005\u0032\u001a\u0002\u0133\u0131\u0003\u0002\u0002\u0002\u0134\u0137\u0003\u0002\u0002\u0002\u0135\u0133\u0003\u0002\u0002\u0002\u0135\u0136\u0003\u0002\u0002\u0002\u0136\u0025\u0003\u0002\u0002\u0002\u0137\u0135\u0003\u0002\u0002\u0002\u0138\u013d\u0007\u0042\u0002\u0002\u0139\u013a\u0007\u0010\u0002\u0002\u013a\u013c\u0007\u0042\u0002\u0002\u013b\u0139\u0003\u0002\u0002\u0002\u013c\u013f\u0003\u0002\u0002\u0002\u013d\u013b\u0003\u0002\u0002\u0002\u013d\u013e\u0003\u0002\u0002\u0002\u013e\u0027\u0003\u0002\u0002\u0002\u013f\u013d\u0003\u0002\u0002\u0002\u0140\u0145\u0005\u002a\u0016\u0002\u0141\u0142\u0007\u0010\u0002\u0002\u0142\u0144\u0005\u002a\u0016\u0002\u0143\u0141\u0003\u0002\u0002\u0002\u0144\u0147\u0003\u0002\u0002\u0002\u0145\u0143\u0003\u0002\u0002\u0002\u0145\u0146\u0003\u0002\u0002\u0002\u0146\u0029\u0003\u0002\u0002\u0002\u0147\u0145\u0003\u0002\u0002\u0002\u0148\u0149\u0008\u0016\u0001\u0002\u0149\u0158\u0007\u0023\u0002\u0002\u014a\u0158\u0007\u0024\u0002\u0002\u014b\u0158\u0007\u0025\u0002\u0002\u014c\u0158\u0005\u005c\u002f\u0002\u014d\u0158\u0005\u005e\u0030\u0002\u014e\u0158\u0005\u002e\u0018\u0002\u014f\u0158\u0007\u0026\u0002\u0002\u0150\u0158\u0005\u003a\u001e\u0002\u0151\u0158\u0005\u002c\u0017\u0002\u0152\u0158\u0005\u0042\u0022\u0002\u0153\u0154\u0005\u0058\u002d\u0002\u0154\u0155\u0005\u002a\u0016\u000b\u0155\u0158\u0003\u0002\u0002\u0002\u0156\u0158\u0005\u003c\u001f\u0002\u0157\u0148\u0003\u0002\u0002\u0002\u0157\u014a\u0003\u0002\u0002\u0002\u0157\u014b\u0003\u0002\u0002\u0002\u0157\u014c\u0003\u0002\u0002\u0002\u0157\u014d\u0003\u0002\u0002\u0002\u0157\u014e\u0003\u0002\u0002\u0002\u0157\u014f\u0003\u0002\u0002\u0002\u0157\u0150\u0003\u0002\u0002\u0002\u0157\u0151\u0003\u0002\u0002\u0002\u0157\u0152\u0003\u0002\u0002\u0002\u0157\u0153\u0003\u0002\u0002\u0002\u0157\u0156\u0003\u0002\u0002\u0002\u0158\u017b\u0003\u0002\u0002\u0002\u0159\u015a\u000c\u000c\u0002\u0002\u015a\u015b\u0005\u005a\u002e\u0002\u015b\u015c\u0005\u002a\u0016\u000c\u015c\u017a\u0003\u0002\u0002\u0002\u015d\u015e\u000c\u000a\u0002\u0002\u015e\u015f\u0005\u0054\u002b\u0002\u015f\u0160\u0005\u002a\u0016\u000b\u0160\u017a\u0003\u0002\u0002\u0002\u0161\u0162\u000c\u0009\u0002\u0002\u0162\u0163\u0005\u0052\u002a\u0002\u0163\u0164\u0005\u002a\u0016\u000a\u0164\u017a\u0003\u0002\u0002\u0002\u0165\u0166\u000c\u0008\u0002\u0002\u0166\u0167\u0005\u0050\u0029\u0002\u0167\u0168\u0005\u002a\u0016\u0008\u0168\u017a\u0003\u0002\u0002\u0002\u0169\u016a\u000c\u0007\u0002\u0002\u016a\u016b\u0005\u004e\u0028\u0002\u016b\u016c\u0005\u002a\u0016\u0008\u016c\u017a\u0003\u0002\u0002\u0002\u016d\u016e\u000c\u0006\u0002\u0002\u016e\u016f\u0005\u004c\u0027\u0002\u016f\u0170\u0005\u002a\u0016\u0007\u0170\u017a\u0003\u0002\u0002\u0002\u0171\u0172\u000c\u0005\u0002\u0002\u0172\u0173\u0005\u004a\u0026\u0002\u0173\u0174\u0005\u002a\u0016\u0006\u0174\u017a\u0003\u0002\u0002\u0002\u0175\u0176\u000c\u0004\u0002\u0002\u0176\u0177\u0005\u0056\u002c\u0002\u0177\u0178\u0005\u002a\u0016\u0005\u0178\u017a\u0003\u0002\u0002\u0002\u0179\u0159\u0003\u0002\u0002\u0002\u0179\u015d\u0003\u0002\u0002\u0002\u0179\u0161\u0003\u0002\u0002\u0002\u0179\u0165\u0003\u0002\u0002\u0002\u0179\u0169\u0003\u0002\u0002\u0002\u0179\u016d\u0003\u0002\u0002\u0002\u0179\u0171\u0003\u0002\u0002\u0002\u0179\u0175\u0003\u0002\u0002\u0002\u017a\u017d\u0003\u0002\u0002\u0002\u017b\u0179\u0003\u0002\u0002\u0002\u017b\u017c\u0003\u0002\u0002\u0002\u017c\u002b\u0003\u0002\u0002\u0002\u017d\u017b\u0003\u0002\u0002\u0002\u017e\u0182\u0005\u0030\u0019\u0002\u017f\u0181\u0005\u0036\u001c\u0002\u0180\u017f\u0003\u0002\u0002\u0002\u0181\u0184\u0003\u0002\u0002\u0002\u0182\u0180\u0003\u0002\u0002\u0002\u0182\u0183\u0003\u0002\u0002\u0002\u0183\u002d\u0003\u0002\u0002\u0002\u0184\u0182\u0003\u0002\u0002\u0002\u0185\u0187\u0005\u0030\u0019\u0002\u0186\u0188\u0005\u0036\u001c\u0002\u0187\u0186\u0003\u0002\u0002\u0002\u0188\u0189\u0003\u0002\u0002\u0002\u0189\u0187\u0003\u0002\u0002\u0002\u0189\u018a\u0003\u0002\u0002\u0002\u018a\u002f\u0003\u0002\u0002\u0002\u018b\u0191\u0005\u0032\u001a\u0002\u018c\u018d\u0007\u0027\u0002\u0002\u018d\u018e\u0005\u002a\u0016\u0002\u018e\u018f\u0007\u0028\u0002\u0002\u018f\u0191\u0003\u0002\u0002\u0002\u0190\u018b\u0003\u0002\u0002\u0002\u0190\u018c\u0003\u0002\u0002\u0002\u0191\u0031\u0003\u0002\u0002\u0002\u0192\u0199\u0007\u0042\u0002\u0002\u0193\u0194\u0007\u0027\u0002\u0002\u0194\u0195\u0005\u002a\u0016\u0002\u0195\u0196\u0007\u0028\u0002\u0002\u0196\u0197\u0005\u0034\u001b\u0002\u0197\u0199\u0003\u0002\u0002\u0002\u0198\u0192\u0003\u0002\u0002\u0002\u0198\u0193\u0003\u0002\u0002\u0002\u0199\u019d\u0003\u0002\u0002\u0002\u019a\u019c\u0005\u0034\u001b\u0002\u019b\u019a\u0003\u0002\u0002\u0002\u019c\u019f\u0003\u0002\u0002\u0002\u019d\u019b\u0003\u0002\u0002\u0002\u019d\u019e\u0003\u0002\u0002\u0002\u019e\u0033\u0003\u0002\u0002\u0002\u019f\u019d\u0003\u0002\u0002\u0002\u01a0\u01a2\u0005\u0036\u001c\u0002\u01a1\u01a0\u0003\u0002\u0002\u0002\u01a2\u01a5\u0003\u0002\u0002\u0002\u01a3\u01a1\u0003\u0002\u0002\u0002\u01a3\u01a4\u0003\u0002\u0002\u0002\u01a4\u01ac\u0003\u0002\u0002\u0002\u01a5\u01a3\u0003\u0002\u0002\u0002\u01a6\u01a7\u0007\u0029\u0002\u0002\u01a7\u01a8\u0005\u002a\u0016\u0002\u01a8\u01a9\u0007\u002a\u0002\u0002\u01a9\u01ad\u0003\u0002\u0002\u0002\u01aa\u01ab\u0007\u0022\u0002\u0002\u01ab\u01ad\u0007\u0042\u0002\u0002\u01ac\u01a6\u0003\u0002\u0002\u0002\u01ac\u01aa\u0003\u0002\u0002\u0002\u01ad\u01af\u0003\u0002\u0002\u0002\u01ae\u01b0\u0005\u0038\u001d\u0002\u01af\u01ae\u0003\u0002\u0002\u0002\u01af\u01b0\u0003\u0002\u0002\u0002\u01b0\u0035\u0003\u0002\u0002\u0002\u01b1\u01b2\u0007\u001c\u0002\u0002\u01b2\u01b4\u0007\u0042\u0002\u0002\u01b3\u01b1\u0003\u0002\u0002\u0002\u01b3\u01b4\u0003\u0002\u0002\u0002\u01b4\u01b5\u0003\u0002\u0002\u0002\u01b5\u01b6\u0005\u0038\u001d\u0002\u01b6\u0037\u0003\u0002\u0002\u0002\u01b7\u01b9\u0007\u0027\u0002\u0002\u01b8\u01ba\u0005\u0028\u0015\u0002\u01b9\u01b8\u0003\u0002\u0002\u0002\u01b9\u01ba\u0003\u0002\u0002\u0002\u01ba\u01bb\u0003\u0002\u0002\u0002\u01bb\u01bf\u0007\u0028\u0002\u0002\u01bc\u01bf\u0005\u0042\u0022\u0002\u01bd\u01bf\u0005\u005e\u0030\u0002\u01be\u01b7\u0003\u0002\u0002\u0002\u01be\u01bc\u0003\u0002\u0002\u0002\u01be\u01bd\u0003\u0002\u0002\u0002\u01bf\u0039\u0003\u0002\u0002\u0002\u01c0\u01c1\u0007\u0012\u0002\u0002\u01c1\u01c2\u0005\u003e\u0020\u0002\u01c2\u003b\u0003\u0002\u0002\u0002\u01c3\u01c4\u0007\u0017\u0002\u0002\u01c4\u01c5\u0005\u0016\u000c\u0002\u01c5\u003d\u0003\u0002\u0002\u0002\u01c6\u01c8\u0007\u0027\u0002\u0002\u01c7\u01c9\u0005\u0040\u0021\u0002\u01c8\u01c7\u0003\u0002\u0002\u0002\u01c8\u01c9\u0003\u0002\u0002\u0002\u01c9\u01ca\u0003\u0002\u0002\u0002\u01ca\u01cb\u0007\u0028\u0002\u0002\u01cb\u01cc\u0005\u0004\u0003\u0002\u01cc\u01cd\u0007\u0009\u0002\u0002\u01cd\u003f\u0003\u0002\u0002\u0002\u01ce\u01d1\u0005\u0026\u0014\u0002\u01cf\u01d0\u0007\u0010\u0002\u0002\u01d0\u01d2\u0007\u0026\u0002\u0002\u01d1\u01cf\u0003\u0002\u0002\u0002\u01d1\u01d2\u0003\u0002\u0002\u0002\u01d2\u01d5\u0003\u0002\u0002\u0002\u01d3\u01d5\u0007\u0026\u0002\u0002\u01d4\u01ce\u0003\u0002\u0002\u0002\u01d4\u01d3\u0003\u0002\u0002\u0002\u01d5\u0041\u0003\u0002\u0002\u0002\u01d6\u01d8\u0007\u002b\u0002\u0002\u01d7\u01d9\u0005\u0044\u0023\u0002\u01d8\u01d7\u0003\u0002\u0002\u0002\u01d8\u01d9\u0003\u0002\u0002\u0002\u01d9\u01da\u0003\u0002\u0002\u0002\u01da\u01e2\u0007\u002c\u0002\u0002\u01db\u01dc\u0007\u0042\u0002\u0002\u01dc\u01de\u0007\u0029\u0002\u0002\u01dd\u01df\u0005\u005c\u002f\u0002\u01de\u01dd\u0003\u0002\u0002\u0002\u01de\u01df\u0003\u0002\u0002\u0002\u01df\u01e0\u0003\u0002\u0002\u0002\u01e0\u01e2\u0007\u002a\u0002\u0002\u01e1\u01d6\u0003\u0002\u0002\u0002\u01e1\u01db\u0003\u0002\u0002\u0002\u01e2\u0043\u0003\u0002\u0002\u0002\u01e3\u01e9\u0005\u0046\u0024\u0002\u01e4\u01e5\u0005\u0048\u0025\u0002\u01e5\u01e6\u0005\u0046\u0024\u0002\u01e6\u01e8\u0003\u0002\u0002\u0002\u01e7\u01e4\u0003\u0002\u0002\u0002\u01e8\u01eb\u0003\u0002\u0002\u0002\u01e9\u01e7\u0003\u0002\u0002\u0002\u01e9\u01ea\u0003\u0002\u0002\u0002\u01ea\u01ed\u0003\u0002\u0002\u0002\u01eb\u01e9\u0003\u0002\u0002\u0002\u01ec\u01ee\u0005\u0048\u0025\u0002\u01ed\u01ec\u0003\u0002\u0002\u0002\u01ed\u01ee\u0003\u0002\u0002\u0002\u01ee\u0045\u0003\u0002\u0002\u0002\u01ef\u01f0\u0007\u0029\u0002\u0002\u01f0\u01f1\u0005\u002a\u0016\u0002\u01f1\u01f2\u0007\u002a\u0002\u0002\u01f2\u01f3\u0007\u0004\u0002\u0002\u01f3\u01f4\u0005\u002a\u0016\u0002\u01f4\u01fa\u0003\u0002\u0002\u0002\u01f5\u01f6\u0007\u0042\u0002\u0002\u01f6\u01f7\u0007\u0004\u0002\u0002\u01f7\u01fa\u0005\u002a\u0016\u0002\u01f8\u01fa\u0005\u002a\u0016\u0002\u01f9\u01ef\u0003\u0002\u0002\u0002\u01f9\u01f5\u0003\u0002\u0002\u0002\u01f9\u01f8\u0003\u0002\u0002\u0002\u01fa\u0047\u0003\u0002\u0002\u0002\u01fb\u01fc\u0009\u0004\u0002\u0002\u01fc\u0049\u0003\u0002\u0002\u0002\u01fd\u01fe\u0007\u002d\u0002\u0002\u01fe\u004b\u0003\u0002\u0002\u0002\u01ff\u0200\u0007\u002e\u0002\u0002\u0200\u004d\u0003\u0002\u0002\u0002\u0201\u0202\u0009\u0005\u0002\u0002\u0202\u004f\u0003\u0002\u0002\u0002\u0203\u0204\u0007\u0033\u0002\u0002\u0204\u0051\u0003\u0002\u0002\u0002\u0205\u0206\u0009\u0006\u0002\u0002\u0206\u0053\u0003\u0002\u0002\u0002\u0207\u0208\u0009\u0007\u0002\u0002\u0208\u0055\u0003\u0002\u0002\u0002\u0209\u020a\u0009\u0008\u0002\u0002\u020a\u0057\u0003\u0002\u0002\u0002\u020b\u020c\u0009\u0009\u0002\u0002\u020c\u0059\u0003\u0002\u0002\u0002\u020d\u020e\u0007\u0041\u0002\u0002\u020e\u005b\u0003\u0002\u0002\u0002\u020f\u0210\u0009\u000a\u0002\u0002\u0210\u005d\u0003\u0002\u0002\u0002\u0211\u0212\u0009\u000b\u0002\u0002\u0212\u005f\u0003\u0002\u0002\u0002\u0035\u0066\u006a\u0089\u008f\u0093\u009f\u00a2\u00ac\u00bd\u00c2\u00c7\u00d1\u00d4\u00dd\u00ed\u00f7\u0104\u010a\u010e\u0111\u0116\u0119\u0120\u0127\u012b\u0135\u013d\u0145\u0157\u0179\u017b\u0182\u0189\u0190\u0198\u019d\u01a3\u01ac\u01af\u01b3\u01b9\u01be\u01c8\u01d1\u01d4\u01d8\u01de\u01e1\u01e9\u01ed\u01f9"

        val ATN = ATNDeserializer().deserialize(serializedATN.asCharArray())
        init {
        	decisionToDFA = Array<DFA>(ATN.numberOfDecisions, {
        		DFA(ATN.getDecisionState(it)!!, it)
        	})


        }
	}

    private val T__0 = Tokens.T__0.id
    private val T__1 = Tokens.T__1.id
    private val T__2 = Tokens.T__2.id
    private val T__3 = Tokens.T__3.id
    private val T__4 = Tokens.T__4.id
    private val T__5 = Tokens.T__5.id
    private val T__6 = Tokens.T__6.id
    private val T__7 = Tokens.T__7.id
    private val T__8 = Tokens.T__8.id
    private val T__9 = Tokens.T__9.id
    private val T__10 = Tokens.T__10.id
    private val T__11 = Tokens.T__11.id
    private val T__12 = Tokens.T__12.id
    private val T__13 = Tokens.T__13.id
    private val T__14 = Tokens.T__14.id
    private val T__15 = Tokens.T__15.id
    private val T__16 = Tokens.T__16.id
    private val T__17 = Tokens.T__17.id
    private val T__18 = Tokens.T__18.id
    private val T__19 = Tokens.T__19.id
    private val T__20 = Tokens.T__20.id
    private val T__21 = Tokens.T__21.id
    private val T__22 = Tokens.T__22.id
    private val T__23 = Tokens.T__23.id
    private val T__24 = Tokens.T__24.id
    private val T__25 = Tokens.T__25.id
    private val T__26 = Tokens.T__26.id
    private val T__27 = Tokens.T__27.id
    private val T__28 = Tokens.T__28.id
    private val T__29 = Tokens.T__29.id
    private val T__30 = Tokens.T__30.id
    private val T__31 = Tokens.T__31.id
    private val T__32 = Tokens.T__32.id
    private val T__33 = Tokens.T__33.id
    private val T__34 = Tokens.T__34.id
    private val T__35 = Tokens.T__35.id
    private val T__36 = Tokens.T__36.id
    private val T__37 = Tokens.T__37.id
    private val T__38 = Tokens.T__38.id
    private val T__39 = Tokens.T__39.id
    private val T__40 = Tokens.T__40.id
    private val T__41 = Tokens.T__41.id
    private val T__42 = Tokens.T__42.id
    private val T__43 = Tokens.T__43.id
    private val T__44 = Tokens.T__44.id
    private val T__45 = Tokens.T__45.id
    private val T__46 = Tokens.T__46.id
    private val T__47 = Tokens.T__47.id
    private val T__48 = Tokens.T__48.id
    private val T__49 = Tokens.T__49.id
    private val T__50 = Tokens.T__50.id
    private val T__51 = Tokens.T__51.id
    private val T__52 = Tokens.T__52.id
    private val T__53 = Tokens.T__53.id
    private val T__54 = Tokens.T__54.id
    private val T__55 = Tokens.T__55.id
    private val T__56 = Tokens.T__56.id
    private val T__57 = Tokens.T__57.id
    private val T__58 = Tokens.T__58.id
    private val T__59 = Tokens.T__59.id
    private val T__60 = Tokens.T__60.id
    private val T__61 = Tokens.T__61.id
    private val T__62 = Tokens.T__62.id
    private val NAME = Tokens.NAME.id
    private val NORMALSTRING = Tokens.NORMALSTRING.id
    private val CHARSTRING = Tokens.CHARSTRING.id
    private val LONGSTRING = Tokens.LONGSTRING.id
    private val INT = Tokens.INT.id
    private val HEX = Tokens.HEX.id
    private val FLOAT = Tokens.FLOAT.id
    private val HEX_FLOAT = Tokens.HEX_FLOAT.id
    private val COMMENT = Tokens.COMMENT.id
    private val LINE_COMMENT = Tokens.LINE_COMMENT.id
    private val WS = Tokens.WS.id
    private val SHEBANG = Tokens.SHEBANG.id

    /* Named actions */
	init {
		interpreter = ParserATNSimulator(this, ATN, decisionToDFA, sharedContextCache)
	}
	/* Funcs */
	open class ChunkContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_chunk.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		fun EOF() : TerminalNode? = getToken(LuaParser.Tokens.EOF.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterChunk(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitChunk(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitChunk(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  chunk() : ChunkContext {
		var _localctx : ChunkContext = ChunkContext(context, state)
		enterRule(_localctx, 0, Rules.RULE_chunk.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 94
			block()
			this.state = 95
			match(EOF) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class BlockContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_block.id
	        set(value) { throw RuntimeException() }
		fun findStat() : List<StatContext> = getRuleContexts(solver.getType("StatContext"))
		fun findStat(i: Int) : StatContext? = getRuleContext(solver.getType("StatContext"),i)
		fun findRetstat() : RetstatContext? = getRuleContext(solver.getType("RetstatContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterBlock(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitBlock(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitBlock(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  block() : BlockContext {
		var _localctx : BlockContext = BlockContext(context, state)
		enterRule(_localctx, 2, Rules.RULE_block.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 100
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,0,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 97
					stat()
					}
					} 
				}
				this.state = 102
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,0,context)
			}
			this.state = 104
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,1,context) ) {
			1   -> if (true){
			this.state = 103
			retstat()
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class StatContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_stat.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
	 
		constructor() : super() { }
		fun copyFrom(ctx: StatContext) {
			super.copyFrom(ctx)
		}
	}
	open class IfStatContext : StatContext {
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findIfbody() : IfbodyContext? = getRuleContext(solver.getType("IfbodyContext"),0)
		fun findElseifbody() : List<ElseifbodyContext> = getRuleContexts(solver.getType("ElseifbodyContext"))
		fun findElseifbody(i: Int) : ElseifbodyContext? = getRuleContext(solver.getType("ElseifbodyContext"),i)
		fun findElsebody() : ElsebodyContext? = getRuleContext(solver.getType("ElsebodyContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterIfStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitIfStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitIfStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class SwitchStatContext : StatContext {
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findDefaultbody() : DefaultbodyContext? = getRuleContext(solver.getType("DefaultbodyContext"),0)
		fun findCasebody() : List<CasebodyContext> = getRuleContexts(solver.getType("CasebodyContext"))
		fun findCasebody(i: Int) : CasebodyContext? = getRuleContext(solver.getType("CasebodyContext"),i)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterSwitchStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitSwitchStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitSwitchStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ContinueStatContext : StatContext {
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterContinueStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitContinueStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitContinueStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class BreakStatContext : StatContext {
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterBreakStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitBreakStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitBreakStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class WhenStatContext : StatContext {
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findPrefixexp() : List<PrefixexpContext> = getRuleContexts(solver.getType("PrefixexpContext"))
		fun findPrefixexp(i: Int) : PrefixexpContext? = getRuleContext(solver.getType("PrefixexpContext"),i)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterWhenStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitWhenStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitWhenStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class LabelStatContext : StatContext {
		fun findLabel() : LabelContext? = getRuleContext(solver.getType("LabelContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLabelStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLabelStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLabelStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ForStatContext : StatContext {
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		fun findExp() : List<ExpContext> = getRuleContexts(solver.getType("ExpContext"))
		fun findExp(i: Int) : ExpContext? = getRuleContext(solver.getType("ExpContext"),i)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterForStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitForStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitForStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class NilContext : StatContext {
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterNil(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitNil(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitNil(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class FunctionCallStatContext : StatContext {
		fun findFunctioncall() : FunctioncallContext? = getRuleContext(solver.getType("FunctioncallContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFunctionCallStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFunctionCallStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFunctionCallStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class LocalFunctionDefStatContext : StatContext {
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		fun findFuncbody() : FuncbodyContext? = getRuleContext(solver.getType("FuncbodyContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLocalFunctionDefStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLocalFunctionDefStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLocalFunctionDefStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class RepeatStatContext : StatContext {
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterRepeatStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitRepeatStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitRepeatStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class CommentStatContext : StatContext {
		fun findComment() : CommentContext? = getRuleContext(solver.getType("CommentContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterCommentStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitCommentStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitCommentStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class DeferStatContext : StatContext {
		fun findFunctioncall() : FunctioncallContext? = getRuleContext(solver.getType("FunctioncallContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterDeferStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitDeferStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitDeferStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class LambdaStatContext : StatContext {
		fun findLambdabody() : LambdabodyContext? = getRuleContext(solver.getType("LambdabodyContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLambdaStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLambdaStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLambdaStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class GotoStatContext : StatContext {
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterGotoStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitGotoStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitGotoStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ForInStatContext : StatContext {
		fun findNamelist() : NamelistContext? = getRuleContext(solver.getType("NamelistContext"),0)
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterForInStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitForInStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitForInStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class DoStatContext : StatContext {
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterDoStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitDoStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitDoStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class LocalVarListStatContext : StatContext {
		fun findAttnamelist() : AttnamelistContext? = getRuleContext(solver.getType("AttnamelistContext"),0)
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLocalVarListStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLocalVarListStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLocalVarListStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ReturnStatContext : StatContext {
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterReturnStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitReturnStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitReturnStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class FunctionDefStatContext : StatContext {
		fun findFuncname() : FuncnameContext? = getRuleContext(solver.getType("FuncnameContext"),0)
		fun findFuncbody() : FuncbodyContext? = getRuleContext(solver.getType("FuncbodyContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFunctionDefStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFunctionDefStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFunctionDefStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class VarListStatContext : StatContext {
		fun findVarlist() : VarlistContext? = getRuleContext(solver.getType("VarlistContext"),0)
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterVarListStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitVarListStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitVarListStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class WhileStatContext : StatContext {
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterWhileStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitWhileStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitWhileStat(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  stat() : StatContext {
		var _localctx : StatContext = StatContext(context, state)
		enterRule(_localctx, 4, Rules.RULE_stat.id)
		var _la: Int
		try {
			this.state = 219
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,13,context) ) {
			1 -> {_localctx = NilContext(_localctx)
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 106
			match(T__0) as Token
			}}
			2 -> {_localctx = VarListStatContext(_localctx)
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 107
			varlist()
			this.state = 108
			match(T__1) as Token
			this.state = 109
			explist()
			}}
			3 -> {_localctx = FunctionCallStatContext(_localctx)
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 111
			functioncall()
			}}
			4 -> {_localctx = LabelStatContext(_localctx)
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 112
			label()
			}}
			5 -> {_localctx = BreakStatContext(_localctx)
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 113
			match(T__2) as Token
			}}
			6 -> {_localctx = ContinueStatContext(_localctx)
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 114
			match(T__3) as Token
			}}
			7 -> {_localctx = GotoStatContext(_localctx)
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 115
			match(T__4) as Token
			this.state = 116
			match(NAME) as Token
			}}
			8 -> {_localctx = DoStatContext(_localctx)
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 117
			match(T__5) as Token
			this.state = 118
			block()
			this.state = 119
			match(T__6) as Token
			}}
			9 -> {_localctx = WhileStatContext(_localctx)
			enterOuterAlt(_localctx, 9)
			if (true){
			this.state = 121
			match(T__7) as Token
			this.state = 122
			exp(0)
			this.state = 123
			match(T__5) as Token
			this.state = 124
			block()
			this.state = 125
			match(T__6) as Token
			}}
			10 -> {_localctx = RepeatStatContext(_localctx)
			enterOuterAlt(_localctx, 10)
			if (true){
			this.state = 127
			match(T__8) as Token
			this.state = 128
			block()
			this.state = 129
			match(T__9) as Token
			this.state = 130
			exp(0)
			}}
			11 -> {_localctx = IfStatContext(_localctx)
			enterOuterAlt(_localctx, 11)
			if (true){
			this.state = 132
			match(T__10) as Token
			this.state = 133
			exp(0)
			this.state = 135
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__11) {
				if (true){
				this.state = 134
				match(T__11) as Token
				}
			}

			this.state = 137
			ifbody()
			this.state = 141
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__23) {
				if (true){
				if (true){
				this.state = 138
				elseifbody()
				}
				}
				this.state = 143
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 145
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__19) {
				if (true){
				this.state = 144
				elsebody()
				}
			}

			this.state = 147
			match(T__6) as Token
			}}
			12 -> {_localctx = ForStatContext(_localctx)
			enterOuterAlt(_localctx, 12)
			if (true){
			this.state = 149
			match(T__12) as Token
			this.state = 150
			match(NAME) as Token
			this.state = 151
			match(T__1) as Token
			this.state = 152
			exp(0)
			this.state = 153
			match(T__13) as Token
			this.state = 154
			exp(0)
			this.state = 157
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__13) {
				if (true){
				this.state = 155
				match(T__13) as Token
				this.state = 156
				exp(0)
				}
			}

			this.state = 160
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,6,context) ) {
			1   -> if (true){
			this.state = 159
			match(T__5) as Token
			}
			}
			this.state = 162
			block()
			this.state = 163
			match(T__6) as Token
			}}
			13 -> {_localctx = ForInStatContext(_localctx)
			enterOuterAlt(_localctx, 13)
			if (true){
			this.state = 165
			match(T__12) as Token
			this.state = 166
			namelist()
			this.state = 167
			match(T__14) as Token
			this.state = 168
			explist()
			this.state = 170
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,7,context) ) {
			1   -> if (true){
			this.state = 169
			match(T__5) as Token
			}
			}
			this.state = 172
			block()
			this.state = 173
			match(T__6) as Token
			}}
			14 -> {_localctx = FunctionDefStatContext(_localctx)
			enterOuterAlt(_localctx, 14)
			if (true){
			this.state = 175
			match(T__15) as Token
			this.state = 176
			funcname()
			this.state = 177
			funcbody()
			}}
			15 -> {_localctx = LocalFunctionDefStatContext(_localctx)
			enterOuterAlt(_localctx, 15)
			if (true){
			this.state = 179
			match(T__16) as Token
			this.state = 180
			match(T__15) as Token
			this.state = 181
			match(NAME) as Token
			this.state = 182
			funcbody()
			}}
			16 -> {_localctx = LocalVarListStatContext(_localctx)
			enterOuterAlt(_localctx, 16)
			if (true){
			this.state = 183
			match(T__16) as Token
			this.state = 184
			attnamelist()
			this.state = 187
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__1) {
				if (true){
				this.state = 185
				match(T__1) as Token
				this.state = 186
				explist()
				}
			}

			}}
			17 -> {_localctx = SwitchStatContext(_localctx)
			enterOuterAlt(_localctx, 17)
			if (true){
			this.state = 189
			match(T__17) as Token
			this.state = 190
			exp(0)
			this.state = 192
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 191
				match(T__5) as Token
				}
			}

			this.state = 197
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__26) {
				if (true){
				if (true){
				this.state = 194
				casebody()
				}
				}
				this.state = 199
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 200
			defaultbody()
			this.state = 201
			match(T__6) as Token
			}}
			18 -> {_localctx = WhenStatContext(_localctx)
			enterOuterAlt(_localctx, 18)
			if (true){
			this.state = 203
			match(T__18) as Token
			this.state = 204
			exp(0)
			this.state = 205
			prefixexp()
			this.state = 210
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,12,context) ) {
			1   -> if (true){
			this.state = 207
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__19) {
				if (true){
				this.state = 206
				match(T__19) as Token
				}
			}

			this.state = 209
			prefixexp()
			}
			}
			}}
			19 -> {_localctx = LambdaStatContext(_localctx)
			enterOuterAlt(_localctx, 19)
			if (true){
			this.state = 212
			match(T__20) as Token
			this.state = 213
			lambdabody()
			}}
			20 -> {_localctx = DeferStatContext(_localctx)
			enterOuterAlt(_localctx, 20)
			if (true){
			this.state = 214
			match(T__21) as Token
			this.state = 215
			functioncall()
			}}
			21 -> {_localctx = ReturnStatContext(_localctx)
			enterOuterAlt(_localctx, 21)
			if (true){
			this.state = 216
			match(T__22) as Token
			this.state = 217
			block()
			}}
			22 -> {_localctx = CommentStatContext(_localctx)
			enterOuterAlt(_localctx, 22)
			if (true){
			this.state = 218
			comment()
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class CommentContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_comment.id
	        set(value) { throw RuntimeException() }
		fun COMMENT() : TerminalNode? = getToken(LuaParser.Tokens.COMMENT.id, 0)
		fun LINE_COMMENT() : TerminalNode? = getToken(LuaParser.Tokens.LINE_COMMENT.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterComment(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitComment(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitComment(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  comment() : CommentContext {
		var _localctx : CommentContext = CommentContext(context, state)
		enterRule(_localctx, 6, Rules.RULE_comment.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 221
			_la = _input!!.LA(1)
			if ( !(_la==COMMENT || _la==LINE_COMMENT) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class IfbodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_ifbody.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterIfbody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitIfbody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitIfbody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  ifbody() : IfbodyContext {
		var _localctx : IfbodyContext = IfbodyContext(context, state)
		enterRule(_localctx, 8, Rules.RULE_ifbody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 223
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ElseifbodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_elseifbody.id
	        set(value) { throw RuntimeException() }
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterElseifbody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitElseifbody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitElseifbody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  elseifbody() : ElseifbodyContext {
		var _localctx : ElseifbodyContext = ElseifbodyContext(context, state)
		enterRule(_localctx, 10, Rules.RULE_elseifbody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 225
			match(T__23) as Token
			this.state = 226
			exp(0)
			this.state = 227
			match(T__11) as Token
			this.state = 228
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ElsebodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_elsebody.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterElsebody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitElsebody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitElsebody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  elsebody() : ElsebodyContext {
		var _localctx : ElsebodyContext = ElsebodyContext(context, state)
		enterRule(_localctx, 12, Rules.RULE_elsebody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 230
			match(T__19) as Token
			this.state = 231
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class DefaultbodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_defaultbody.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterDefaultbody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitDefaultbody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitDefaultbody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  defaultbody() : DefaultbodyContext {
		var _localctx : DefaultbodyContext = DefaultbodyContext(context, state)
		enterRule(_localctx, 14, Rules.RULE_defaultbody.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 233
			match(T__24) as Token
			this.state = 235
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__11 || _la==T__25) {
				if (true){
				this.state = 234
				_la = _input!!.LA(1)
				if ( !(_la==T__11 || _la==T__25) ) {
					errorHandler.recoverInline(this)
				}
				else {
					if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
					errorHandler.reportMatch(this)
					consume()
				}
				}
			}

			this.state = 237
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class CasebodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_casebody.id
	        set(value) { throw RuntimeException() }
		fun findCaseexp() : CaseexpContext? = getRuleContext(solver.getType("CaseexpContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterCasebody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitCasebody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitCasebody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  casebody() : CasebodyContext {
		var _localctx : CasebodyContext = CasebodyContext(context, state)
		enterRule(_localctx, 16, Rules.RULE_casebody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 239
			match(T__26) as Token
			this.state = 240
			caseexp()
			this.state = 241
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class CaseexpContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_caseexp.id
	        set(value) { throw RuntimeException() }
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterCaseexp(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitCaseexp(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitCaseexp(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  caseexp() : CaseexpContext {
		var _localctx : CaseexpContext = CaseexpContext(context, state)
		enterRule(_localctx, 18, Rules.RULE_caseexp.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 243
			explist()
			this.state = 245
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__11 || _la==T__25) {
				if (true){
				this.state = 244
				_la = _input!!.LA(1)
				if ( !(_la==T__11 || _la==T__25) ) {
					errorHandler.recoverInline(this)
				}
				else {
					if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
					errorHandler.reportMatch(this)
					consume()
				}
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class LambdabodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_lambdabody.id
	        set(value) { throw RuntimeException() }
		fun findParlist() : ParlistContext? = getRuleContext(solver.getType("ParlistContext"),0)
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLambdabody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLambdabody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLambdabody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  lambdabody() : LambdabodyContext {
		var _localctx : LambdabodyContext = LambdabodyContext(context, state)
		enterRule(_localctx, 20, Rules.RULE_lambdabody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			if (true){
			this.state = 247
			parlist()
			}
			this.state = 248
			match(T__25) as Token
			if (true){
			this.state = 249
			explist()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class AttnamelistContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_attnamelist.id
	        set(value) { throw RuntimeException() }
		fun NAME() : List<TerminalNode> = getTokens(LuaParser.Tokens.NAME.id)
		fun NAME(i: Int) : TerminalNode = getToken(LuaParser.Tokens.NAME.id, i) as TerminalNode
		fun findAttrib() : List<AttribContext> = getRuleContexts(solver.getType("AttribContext"))
		fun findAttrib(i: Int) : AttribContext? = getRuleContext(solver.getType("AttribContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterAttnamelist(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitAttnamelist(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitAttnamelist(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  attnamelist() : AttnamelistContext {
		var _localctx : AttnamelistContext = AttnamelistContext(context, state)
		enterRule(_localctx, 22, Rules.RULE_attnamelist.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 251
			match(NAME) as Token
			this.state = 252
			attrib()
			this.state = 258
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__13) {
				if (true){
				if (true){
				this.state = 253
				match(T__13) as Token
				this.state = 254
				match(NAME) as Token
				this.state = 255
				attrib()
				}
				}
				this.state = 260
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class AttribContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_attrib.id
	        set(value) { throw RuntimeException() }
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterAttrib(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitAttrib(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitAttrib(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  attrib() : AttribContext {
		var _localctx : AttribContext = AttribContext(context, state)
		enterRule(_localctx, 24, Rules.RULE_attrib.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 264
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__27) {
				if (true){
				this.state = 261
				match(T__27) as Token
				this.state = 262
				match(NAME) as Token
				this.state = 263
				match(T__28) as Token
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class RetstatContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_retstat.id
	        set(value) { throw RuntimeException() }
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		fun findComment() : List<CommentContext> = getRuleContexts(solver.getType("CommentContext"))
		fun findComment(i: Int) : CommentContext? = getRuleContext(solver.getType("CommentContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterRetstat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitRetstat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitRetstat(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  retstat() : RetstatContext {
		var _localctx : RetstatContext = RetstatContext(context, state)
		enterRule(_localctx, 26, Rules.RULE_retstat.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 266
			match(T__22) as Token
			this.state = 268
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,18,context) ) {
			1   -> if (true){
			this.state = 267
			explist()
			}
			}
			this.state = 271
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,19,context) ) {
			1   -> if (true){
			this.state = 270
			match(T__0) as Token
			}
			}
			this.state = 279
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,21,context) ) {
			1   -> if (true){
			this.state = 276
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,20,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 273
					comment()
					}
					} 
				}
				this.state = 278
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,20,context)
			}
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class LabelContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_label.id
	        set(value) { throw RuntimeException() }
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLabel(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLabel(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLabel(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  label() : LabelContext {
		var _localctx : LabelContext = LabelContext(context, state)
		enterRule(_localctx, 28, Rules.RULE_label.id)
		try {
			this.state = 286
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__29  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 281
			match(T__29) as Token
			this.state = 282
			match(NAME) as Token
			this.state = 283
			match(T__29) as Token
			}}
			T__30  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 284
			match(T__30) as Token
			this.state = 285
			match(NAME) as Token
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FuncnameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_funcname.id
	        set(value) { throw RuntimeException() }
		fun NAME() : List<TerminalNode> = getTokens(LuaParser.Tokens.NAME.id)
		fun NAME(i: Int) : TerminalNode = getToken(LuaParser.Tokens.NAME.id, i) as TerminalNode
		fun findFuncname_self() : Funcname_selfContext? = getRuleContext(solver.getType("Funcname_selfContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFuncname(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFuncname(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFuncname(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  funcname() : FuncnameContext {
		var _localctx : FuncnameContext = FuncnameContext(context, state)
		enterRule(_localctx, 30, Rules.RULE_funcname.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 288
			match(NAME) as Token
			this.state = 293
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__31) {
				if (true){
				if (true){
				this.state = 289
				match(T__31) as Token
				this.state = 290
				match(NAME) as Token
				}
				}
				this.state = 295
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 297
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__25) {
				if (true){
				this.state = 296
				funcname_self()
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class Funcname_selfContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_funcname_self.id
	        set(value) { throw RuntimeException() }
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFuncname_self(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFuncname_self(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFuncname_self(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  funcname_self() : Funcname_selfContext {
		var _localctx : Funcname_selfContext = Funcname_selfContext(context, state)
		enterRule(_localctx, 32, Rules.RULE_funcname_self.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 299
			match(T__25) as Token
			this.state = 300
			match(NAME) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class VarlistContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_varlist.id
	        set(value) { throw RuntimeException() }
		fun findLvar() : List<LvarContext> = getRuleContexts(solver.getType("LvarContext"))
		fun findLvar(i: Int) : LvarContext? = getRuleContext(solver.getType("LvarContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterVarlist(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitVarlist(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitVarlist(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  varlist() : VarlistContext {
		var _localctx : VarlistContext = VarlistContext(context, state)
		enterRule(_localctx, 34, Rules.RULE_varlist.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 302
			lvar()
			this.state = 307
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__13) {
				if (true){
				if (true){
				this.state = 303
				match(T__13) as Token
				this.state = 304
				lvar()
				}
				}
				this.state = 309
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class NamelistContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_namelist.id
	        set(value) { throw RuntimeException() }
		fun NAME() : List<TerminalNode> = getTokens(LuaParser.Tokens.NAME.id)
		fun NAME(i: Int) : TerminalNode = getToken(LuaParser.Tokens.NAME.id, i) as TerminalNode
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterNamelist(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitNamelist(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitNamelist(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  namelist() : NamelistContext {
		var _localctx : NamelistContext = NamelistContext(context, state)
		enterRule(_localctx, 36, Rules.RULE_namelist.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 310
			match(NAME) as Token
			this.state = 315
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,26,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 311
					match(T__13) as Token
					this.state = 312
					match(NAME) as Token
					}
					} 
				}
				this.state = 317
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,26,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ExplistContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_explist.id
	        set(value) { throw RuntimeException() }
		fun findExp() : List<ExpContext> = getRuleContexts(solver.getType("ExpContext"))
		fun findExp(i: Int) : ExpContext? = getRuleContext(solver.getType("ExpContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterExplist(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitExplist(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitExplist(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  explist() : ExplistContext {
		var _localctx : ExplistContext = ExplistContext(context, state)
		enterRule(_localctx, 38, Rules.RULE_explist.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 318
			exp(0)
			this.state = 323
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,27,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 319
					match(T__13) as Token
					this.state = 320
					exp(0)
					}
					} 
				}
				this.state = 325
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,27,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ExpContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_exp.id
	        set(value) { throw RuntimeException() }
		fun findNumber() : NumberContext? = getRuleContext(solver.getType("NumberContext"),0)
		fun findString() : StringContext? = getRuleContext(solver.getType("StringContext"),0)
		fun findFunctioncall() : FunctioncallContext? = getRuleContext(solver.getType("FunctioncallContext"),0)
		fun findFunctiondef() : FunctiondefContext? = getRuleContext(solver.getType("FunctiondefContext"),0)
		fun findPrefixexp() : PrefixexpContext? = getRuleContext(solver.getType("PrefixexpContext"),0)
		fun findTableconstructor() : TableconstructorContext? = getRuleContext(solver.getType("TableconstructorContext"),0)
		fun findOperatorUnary() : OperatorUnaryContext? = getRuleContext(solver.getType("OperatorUnaryContext"),0)
		fun findExp() : List<ExpContext> = getRuleContexts(solver.getType("ExpContext"))
		fun findExp(i: Int) : ExpContext? = getRuleContext(solver.getType("ExpContext"),i)
		fun findLambdadef() : LambdadefContext? = getRuleContext(solver.getType("LambdadefContext"),0)
		fun findOperatorPower() : OperatorPowerContext? = getRuleContext(solver.getType("OperatorPowerContext"),0)
		fun findOperatorMulDivMod() : OperatorMulDivModContext? = getRuleContext(solver.getType("OperatorMulDivModContext"),0)
		fun findOperatorAddSub() : OperatorAddSubContext? = getRuleContext(solver.getType("OperatorAddSubContext"),0)
		fun findOperatorStrcat() : OperatorStrcatContext? = getRuleContext(solver.getType("OperatorStrcatContext"),0)
		fun findOperatorComparison() : OperatorComparisonContext? = getRuleContext(solver.getType("OperatorComparisonContext"),0)
		fun findOperatorAnd() : OperatorAndContext? = getRuleContext(solver.getType("OperatorAndContext"),0)
		fun findOperatorOr() : OperatorOrContext? = getRuleContext(solver.getType("OperatorOrContext"),0)
		fun findOperatorBitwise() : OperatorBitwiseContext? = getRuleContext(solver.getType("OperatorBitwiseContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterExp(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitExp(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitExp(this)
			else return visitor.visitChildren(this)!!
		}
	}

	 fun exp() : ExpContext {
		return exp(0);
	}

	private fun exp(_p: Int) : ExpContext {
		var _parentctx : ParserRuleContext? = context
		var _parentState : Int = state
		var _localctx : ExpContext= ExpContext(context, _parentState)
		var _prevctx : ExpContext= _localctx
		var _startState : Int = 40
		enterRecursionRule(_localctx, 40, Rules.RULE_exp.id, _p)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 341
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,28,context) ) {
			1 -> {if (true){
			this.state = 327
			match(T__32) as Token
			}}
			2 -> {if (true){
			this.state = 328
			match(T__33) as Token
			}}
			3 -> {if (true){
			this.state = 329
			match(T__34) as Token
			}}
			4 -> {if (true){
			this.state = 330
			number()
			}}
			5 -> {if (true){
			this.state = 331
			string()
			}}
			6 -> {if (true){
			this.state = 332
			functioncall()
			}}
			7 -> {if (true){
			this.state = 333
			match(T__35) as Token
			}}
			8 -> {if (true){
			this.state = 334
			functiondef()
			}}
			9 -> {if (true){
			this.state = 335
			prefixexp()
			}}
			10 -> {if (true){
			this.state = 336
			tableconstructor()
			}}
			11 -> {if (true){
			this.state = 337
			operatorUnary()
			this.state = 338
			exp(9)
			}}
			12 -> {if (true){
			this.state = 340
			lambdadef()
			}}
			}
			this.context!!.stop = _input!!.LT(-1)
			this.state = 377
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,30,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent()
					    _prevctx = _localctx
					if (true){
					this.state = 375
					errorHandler.sync(this)
					when ( interpreter!!.adaptivePredict(_input!!,29,context) ) {
					1 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 343
					if (!(precpred(context!!, 10))) throw FailedPredicateException(this, "precpred(context!!, 10)")
					this.state = 344
					operatorPower()
					this.state = 345
					exp(10)
					}}
					2 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 347
					if (!(precpred(context!!, 8))) throw FailedPredicateException(this, "precpred(context!!, 8)")
					this.state = 348
					operatorMulDivMod()
					this.state = 349
					exp(9)
					}}
					3 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 351
					if (!(precpred(context!!, 7))) throw FailedPredicateException(this, "precpred(context!!, 7)")
					this.state = 352
					operatorAddSub()
					this.state = 353
					exp(8)
					}}
					4 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 355
					if (!(precpred(context!!, 6))) throw FailedPredicateException(this, "precpred(context!!, 6)")
					this.state = 356
					operatorStrcat()
					this.state = 357
					exp(6)
					}}
					5 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 359
					if (!(precpred(context!!, 5))) throw FailedPredicateException(this, "precpred(context!!, 5)")
					this.state = 360
					operatorComparison()
					this.state = 361
					exp(6)
					}}
					6 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 363
					if (!(precpred(context!!, 4))) throw FailedPredicateException(this, "precpred(context!!, 4)")
					this.state = 364
					operatorAnd()
					this.state = 365
					exp(5)
					}}
					7 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 367
					if (!(precpred(context!!, 3))) throw FailedPredicateException(this, "precpred(context!!, 3)")
					this.state = 368
					operatorOr()
					this.state = 369
					exp(4)
					}}
					8 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 371
					if (!(precpred(context!!, 2))) throw FailedPredicateException(this, "precpred(context!!, 2)")
					this.state = 372
					operatorBitwise()
					this.state = 373
					exp(3)
					}}
					}
					} 
				}
				this.state = 379
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,30,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			unrollRecursionContexts(_parentctx)
		}
		return _localctx
	}

	open class PrefixexpContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_prefixexp.id
	        set(value) { throw RuntimeException() }
		fun findVarOrExp() : VarOrExpContext? = getRuleContext(solver.getType("VarOrExpContext"),0)
		fun findNameAndArgs() : List<NameAndArgsContext> = getRuleContexts(solver.getType("NameAndArgsContext"))
		fun findNameAndArgs(i: Int) : NameAndArgsContext? = getRuleContext(solver.getType("NameAndArgsContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterPrefixexp(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitPrefixexp(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitPrefixexp(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  prefixexp() : PrefixexpContext {
		var _localctx : PrefixexpContext = PrefixexpContext(context, state)
		enterRule(_localctx, 42, Rules.RULE_prefixexp.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 380
			varOrExp()
			this.state = 384
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,31,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 381
					nameAndArgs()
					}
					} 
				}
				this.state = 386
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,31,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctioncallContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_functioncall.id
	        set(value) { throw RuntimeException() }
		fun findVarOrExp() : VarOrExpContext? = getRuleContext(solver.getType("VarOrExpContext"),0)
		fun findNameAndArgs() : List<NameAndArgsContext> = getRuleContexts(solver.getType("NameAndArgsContext"))
		fun findNameAndArgs(i: Int) : NameAndArgsContext? = getRuleContext(solver.getType("NameAndArgsContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFunctioncall(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFunctioncall(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFunctioncall(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  functioncall() : FunctioncallContext {
		var _localctx : FunctioncallContext = FunctioncallContext(context, state)
		enterRule(_localctx, 44, Rules.RULE_functioncall.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 387
			varOrExp()
			this.state = 389 
			errorHandler.sync(this)
			_alt = 1
			do {
				when (_alt) {
				    1 -> if (true){
				if (true){
				this.state = 388
				nameAndArgs()
				}
				}
				else -> throw NoViableAltException(this)
				}
				this.state = 391 
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,32,context)
			} while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER )
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class VarOrExpContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_varOrExp.id
	        set(value) { throw RuntimeException() }
		fun findLvar() : LvarContext? = getRuleContext(solver.getType("LvarContext"),0)
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterVarOrExp(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitVarOrExp(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitVarOrExp(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  varOrExp() : VarOrExpContext {
		var _localctx : VarOrExpContext = VarOrExpContext(context, state)
		enterRule(_localctx, 46, Rules.RULE_varOrExp.id)
		try {
			this.state = 398
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,33,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 393
			lvar()
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 394
			match(T__36) as Token
			this.state = 395
			exp(0)
			this.state = 396
			match(T__37) as Token
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class LvarContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_lvar.id
	        set(value) { throw RuntimeException() }
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findVarSuffix() : List<VarSuffixContext> = getRuleContexts(solver.getType("VarSuffixContext"))
		fun findVarSuffix(i: Int) : VarSuffixContext? = getRuleContext(solver.getType("VarSuffixContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLvar(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLvar(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLvar(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  lvar() : LvarContext {
		var _localctx : LvarContext = LvarContext(context, state)
		enterRule(_localctx, 48, Rules.RULE_lvar.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 406
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			NAME  ->  /*LL1AltBlock*/{if (true){
			this.state = 400
			match(NAME) as Token
			}}
			T__36  ->  /*LL1AltBlock*/{if (true){
			this.state = 401
			match(T__36) as Token
			this.state = 402
			exp(0)
			this.state = 403
			match(T__37) as Token
			this.state = 404
			varSuffix()
			}}
			else -> throw NoViableAltException(this)
			}
			this.state = 411
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,35,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 408
					varSuffix()
					}
					} 
				}
				this.state = 413
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,35,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class VarSuffixContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_varSuffix.id
	        set(value) { throw RuntimeException() }
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		fun findNameAndArgs() : List<NameAndArgsContext> = getRuleContexts(solver.getType("NameAndArgsContext"))
		fun findNameAndArgs(i: Int) : NameAndArgsContext? = getRuleContext(solver.getType("NameAndArgsContext"),i)
		fun findArgs() : ArgsContext? = getRuleContext(solver.getType("ArgsContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterVarSuffix(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitVarSuffix(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitVarSuffix(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  varSuffix() : VarSuffixContext {
		var _localctx : VarSuffixContext = VarSuffixContext(context, state)
		enterRule(_localctx, 50, Rules.RULE_varSuffix.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 417
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (((((_la - 26)) and 0x3f.inv()) == 0 && ((1L shl (_la - 26)) and ((1L shl (T__25 - 26)) or (1L shl (T__36 - 26)) or (1L shl (T__40 - 26)) or (1L shl (NAME - 26)) or (1L shl (NORMALSTRING - 26)) or (1L shl (CHARSTRING - 26)) or (1L shl (LONGSTRING - 26)))) != 0L)) {
				if (true){
				if (true){
				this.state = 414
				nameAndArgs()
				}
				}
				this.state = 419
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 426
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__38  ->  /*LL1AltBlock*/{if (true){
			this.state = 420
			match(T__38) as Token
			this.state = 421
			exp(0)
			this.state = 422
			match(T__39) as Token
			}}
			T__31  ->  /*LL1AltBlock*/{if (true){
			this.state = 424
			match(T__31) as Token
			this.state = 425
			match(NAME) as Token
			}}
			else -> throw NoViableAltException(this)
			}
			this.state = 429
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,38,context) ) {
			1   -> if (true){
			this.state = 428
			args()
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class NameAndArgsContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_nameAndArgs.id
	        set(value) { throw RuntimeException() }
		fun findArgs() : ArgsContext? = getRuleContext(solver.getType("ArgsContext"),0)
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterNameAndArgs(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitNameAndArgs(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitNameAndArgs(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  nameAndArgs() : NameAndArgsContext {
		var _localctx : NameAndArgsContext = NameAndArgsContext(context, state)
		enterRule(_localctx, 52, Rules.RULE_nameAndArgs.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 433
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__25) {
				if (true){
				this.state = 431
				match(T__25) as Token
				this.state = 432
				match(NAME) as Token
				}
			}

			this.state = 435
			args()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ArgsContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_args.id
	        set(value) { throw RuntimeException() }
		fun findExplist() : ExplistContext? = getRuleContext(solver.getType("ExplistContext"),0)
		fun findTableconstructor() : TableconstructorContext? = getRuleContext(solver.getType("TableconstructorContext"),0)
		fun findString() : StringContext? = getRuleContext(solver.getType("StringContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterArgs(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitArgs(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitArgs(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  args() : ArgsContext {
		var _localctx : ArgsContext = ArgsContext(context, state)
		enterRule(_localctx, 54, Rules.RULE_args.id)
		var _la: Int
		try {
			this.state = 444
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__36  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 437
			match(T__36) as Token
			this.state = 439
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 16)) and 0x3f.inv()) == 0 && ((1L shl (_la - 16)) and ((1L shl (T__15 - 16)) or (1L shl (T__20 - 16)) or (1L shl (T__32 - 16)) or (1L shl (T__33 - 16)) or (1L shl (T__34 - 16)) or (1L shl (T__35 - 16)) or (1L shl (T__36 - 16)) or (1L shl (T__40 - 16)) or (1L shl (T__50 - 16)) or (1L shl (T__57 - 16)) or (1L shl (T__60 - 16)) or (1L shl (T__61 - 16)) or (1L shl (NAME - 16)) or (1L shl (NORMALSTRING - 16)) or (1L shl (CHARSTRING - 16)) or (1L shl (LONGSTRING - 16)) or (1L shl (INT - 16)) or (1L shl (HEX - 16)) or (1L shl (FLOAT - 16)) or (1L shl (HEX_FLOAT - 16)))) != 0L)) {
				if (true){
				this.state = 438
				explist()
				}
			}

			this.state = 441
			match(T__37) as Token
			}}
			T__40 , NAME  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 442
			tableconstructor()
			}}
			NORMALSTRING , CHARSTRING , LONGSTRING  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 443
			string()
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctiondefContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_functiondef.id
	        set(value) { throw RuntimeException() }
		fun findFuncbody() : FuncbodyContext? = getRuleContext(solver.getType("FuncbodyContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFunctiondef(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFunctiondef(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFunctiondef(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  functiondef() : FunctiondefContext {
		var _localctx : FunctiondefContext = FunctiondefContext(context, state)
		enterRule(_localctx, 56, Rules.RULE_functiondef.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 446
			match(T__15) as Token
			this.state = 447
			funcbody()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class LambdadefContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_lambdadef.id
	        set(value) { throw RuntimeException() }
		fun findLambdabody() : LambdabodyContext? = getRuleContext(solver.getType("LambdabodyContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLambdadef(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLambdadef(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLambdadef(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  lambdadef() : LambdadefContext {
		var _localctx : LambdadefContext = LambdadefContext(context, state)
		enterRule(_localctx, 58, Rules.RULE_lambdadef.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 449
			match(T__20) as Token
			this.state = 450
			lambdabody()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FuncbodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_funcbody.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		fun findParlist() : ParlistContext? = getRuleContext(solver.getType("ParlistContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFuncbody(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFuncbody(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFuncbody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  funcbody() : FuncbodyContext {
		var _localctx : FuncbodyContext = FuncbodyContext(context, state)
		enterRule(_localctx, 60, Rules.RULE_funcbody.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 452
			match(T__36) as Token
			this.state = 454
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__35 || _la==NAME) {
				if (true){
				this.state = 453
				parlist()
				}
			}

			this.state = 456
			match(T__37) as Token
			this.state = 457
			block()
			this.state = 458
			match(T__6) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ParlistContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_parlist.id
	        set(value) { throw RuntimeException() }
		fun findNamelist() : NamelistContext? = getRuleContext(solver.getType("NamelistContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterParlist(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitParlist(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitParlist(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  parlist() : ParlistContext {
		var _localctx : ParlistContext = ParlistContext(context, state)
		enterRule(_localctx, 62, Rules.RULE_parlist.id)
		var _la: Int
		try {
			this.state = 466
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			NAME  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 460
			namelist()
			this.state = 463
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__13) {
				if (true){
				this.state = 461
				match(T__13) as Token
				this.state = 462
				match(T__35) as Token
				}
			}

			}}
			T__35  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 465
			match(T__35) as Token
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class TableconstructorContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_tableconstructor.id
	        set(value) { throw RuntimeException() }
		fun findFieldlist() : FieldlistContext? = getRuleContext(solver.getType("FieldlistContext"),0)
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		fun findNumber() : NumberContext? = getRuleContext(solver.getType("NumberContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterTableconstructor(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitTableconstructor(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitTableconstructor(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  tableconstructor() : TableconstructorContext {
		var _localctx : TableconstructorContext = TableconstructorContext(context, state)
		enterRule(_localctx, 64, Rules.RULE_tableconstructor.id)
		var _la: Int
		try {
			this.state = 479
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__40  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 468
			match(T__40) as Token
			this.state = 470
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 16)) and 0x3f.inv()) == 0 && ((1L shl (_la - 16)) and ((1L shl (T__15 - 16)) or (1L shl (T__20 - 16)) or (1L shl (T__32 - 16)) or (1L shl (T__33 - 16)) or (1L shl (T__34 - 16)) or (1L shl (T__35 - 16)) or (1L shl (T__36 - 16)) or (1L shl (T__38 - 16)) or (1L shl (T__40 - 16)) or (1L shl (T__50 - 16)) or (1L shl (T__57 - 16)) or (1L shl (T__60 - 16)) or (1L shl (T__61 - 16)) or (1L shl (NAME - 16)) or (1L shl (NORMALSTRING - 16)) or (1L shl (CHARSTRING - 16)) or (1L shl (LONGSTRING - 16)) or (1L shl (INT - 16)) or (1L shl (HEX - 16)) or (1L shl (FLOAT - 16)) or (1L shl (HEX_FLOAT - 16)))) != 0L)) {
				if (true){
				this.state = 469
				fieldlist()
				}
			}

			this.state = 472
			match(T__41) as Token
			}}
			NAME  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 473
			match(NAME) as Token
			this.state = 474
			match(T__38) as Token
			this.state = 476
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 68)) and 0x3f.inv()) == 0 && ((1L shl (_la - 68)) and ((1L shl (INT - 68)) or (1L shl (HEX - 68)) or (1L shl (FLOAT - 68)) or (1L shl (HEX_FLOAT - 68)))) != 0L)) {
				if (true){
				this.state = 475
				number()
				}
			}

			this.state = 478
			match(T__39) as Token
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FieldlistContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_fieldlist.id
	        set(value) { throw RuntimeException() }
		fun findField() : List<FieldContext> = getRuleContexts(solver.getType("FieldContext"))
		fun findField(i: Int) : FieldContext? = getRuleContext(solver.getType("FieldContext"),i)
		fun findFieldsep() : List<FieldsepContext> = getRuleContexts(solver.getType("FieldsepContext"))
		fun findFieldsep(i: Int) : FieldsepContext? = getRuleContext(solver.getType("FieldsepContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFieldlist(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFieldlist(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFieldlist(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  fieldlist() : FieldlistContext {
		var _localctx : FieldlistContext = FieldlistContext(context, state)
		enterRule(_localctx, 66, Rules.RULE_fieldlist.id)
		var _la: Int
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 481
			field()
			this.state = 487
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,48,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 482
					fieldsep()
					this.state = 483
					field()
					}
					} 
				}
				this.state = 489
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,48,context)
			}
			this.state = 491
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__0 || _la==T__13) {
				if (true){
				this.state = 490
				fieldsep()
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FieldContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_field.id
	        set(value) { throw RuntimeException() }
		fun findExp() : List<ExpContext> = getRuleContexts(solver.getType("ExpContext"))
		fun findExp(i: Int) : ExpContext? = getRuleContext(solver.getType("ExpContext"),i)
		fun NAME() : TerminalNode? = getToken(LuaParser.Tokens.NAME.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterField(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitField(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitField(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  field() : FieldContext {
		var _localctx : FieldContext = FieldContext(context, state)
		enterRule(_localctx, 68, Rules.RULE_field.id)
		try {
			this.state = 503
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,50,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 493
			match(T__38) as Token
			this.state = 494
			exp(0)
			this.state = 495
			match(T__39) as Token
			this.state = 496
			match(T__1) as Token
			this.state = 497
			exp(0)
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 499
			match(NAME) as Token
			this.state = 500
			match(T__1) as Token
			this.state = 501
			exp(0)
			}}
			3 -> {
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 502
			exp(0)
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FieldsepContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_fieldsep.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterFieldsep(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitFieldsep(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitFieldsep(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  fieldsep() : FieldsepContext {
		var _localctx : FieldsepContext = FieldsepContext(context, state)
		enterRule(_localctx, 70, Rules.RULE_fieldsep.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 505
			_la = _input!!.LA(1)
			if ( !(_la==T__0 || _la==T__13) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorOrContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorOr.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorOr(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorOr(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorOr(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorOr() : OperatorOrContext {
		var _localctx : OperatorOrContext = OperatorOrContext(context, state)
		enterRule(_localctx, 72, Rules.RULE_operatorOr.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 507
			match(T__42) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorAndContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorAnd.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorAnd(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorAnd(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorAnd(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorAnd() : OperatorAndContext {
		var _localctx : OperatorAndContext = OperatorAndContext(context, state)
		enterRule(_localctx, 74, Rules.RULE_operatorAnd.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 509
			match(T__43) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorComparisonContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorComparison.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorComparison(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorComparison(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorComparison(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorComparison() : OperatorComparisonContext {
		var _localctx : OperatorComparisonContext = OperatorComparisonContext(context, state)
		enterRule(_localctx, 76, Rules.RULE_operatorComparison.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 511
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__27) or (1L shl T__28) or (1L shl T__44) or (1L shl T__45) or (1L shl T__46) or (1L shl T__47))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorStrcatContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorStrcat.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorStrcat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorStrcat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorStrcat(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorStrcat() : OperatorStrcatContext {
		var _localctx : OperatorStrcatContext = OperatorStrcatContext(context, state)
		enterRule(_localctx, 78, Rules.RULE_operatorStrcat.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 513
			match(T__48) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorAddSubContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorAddSub.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorAddSub(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorAddSub(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorAddSub(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorAddSub() : OperatorAddSubContext {
		var _localctx : OperatorAddSubContext = OperatorAddSubContext(context, state)
		enterRule(_localctx, 80, Rules.RULE_operatorAddSub.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 515
			_la = _input!!.LA(1)
			if ( !(_la==T__49 || _la==T__50) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorMulDivModContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorMulDivMod.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorMulDivMod(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorMulDivMod(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorMulDivMod(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorMulDivMod() : OperatorMulDivModContext {
		var _localctx : OperatorMulDivModContext = OperatorMulDivModContext(context, state)
		enterRule(_localctx, 82, Rules.RULE_operatorMulDivMod.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 517
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__51) or (1L shl T__52) or (1L shl T__53) or (1L shl T__54))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorBitwiseContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorBitwise.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorBitwise(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorBitwise(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorBitwise(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorBitwise() : OperatorBitwiseContext {
		var _localctx : OperatorBitwiseContext = OperatorBitwiseContext(context, state)
		enterRule(_localctx, 84, Rules.RULE_operatorBitwise.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 519
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__55) or (1L shl T__56) or (1L shl T__57) or (1L shl T__58) or (1L shl T__59))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorUnaryContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorUnary.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorUnary(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorUnary(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorUnary(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorUnary() : OperatorUnaryContext {
		var _localctx : OperatorUnaryContext = OperatorUnaryContext(context, state)
		enterRule(_localctx, 86, Rules.RULE_operatorUnary.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 521
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__50) or (1L shl T__57) or (1L shl T__60) or (1L shl T__61))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OperatorPowerContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_operatorPower.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterOperatorPower(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitOperatorPower(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitOperatorPower(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  operatorPower() : OperatorPowerContext {
		var _localctx : OperatorPowerContext = OperatorPowerContext(context, state)
		enterRule(_localctx, 88, Rules.RULE_operatorPower.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 523
			match(T__62) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class NumberContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_number.id
	        set(value) { throw RuntimeException() }
		fun INT() : TerminalNode? = getToken(LuaParser.Tokens.INT.id, 0)
		fun HEX() : TerminalNode? = getToken(LuaParser.Tokens.HEX.id, 0)
		fun FLOAT() : TerminalNode? = getToken(LuaParser.Tokens.FLOAT.id, 0)
		fun HEX_FLOAT() : TerminalNode? = getToken(LuaParser.Tokens.HEX_FLOAT.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterNumber(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitNumber(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitNumber(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  number() : NumberContext {
		var _localctx : NumberContext = NumberContext(context, state)
		enterRule(_localctx, 90, Rules.RULE_number.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 525
			_la = _input!!.LA(1)
			if ( !(((((_la - 68)) and 0x3f.inv()) == 0 && ((1L shl (_la - 68)) and ((1L shl (INT - 68)) or (1L shl (HEX - 68)) or (1L shl (FLOAT - 68)) or (1L shl (HEX_FLOAT - 68)))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class StringContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_string.id
	        set(value) { throw RuntimeException() }
		fun NORMALSTRING() : TerminalNode? = getToken(LuaParser.Tokens.NORMALSTRING.id, 0)
		fun CHARSTRING() : TerminalNode? = getToken(LuaParser.Tokens.CHARSTRING.id, 0)
		fun LONGSTRING() : TerminalNode? = getToken(LuaParser.Tokens.LONGSTRING.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterString(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitString(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitString(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  string() : StringContext {
		var _localctx : StringContext = StringContext(context, state)
		enterRule(_localctx, 92, Rules.RULE_string.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 527
			_la = _input!!.LA(1)
			if ( !(((((_la - 65)) and 0x3f.inv()) == 0 && ((1L shl (_la - 65)) and ((1L shl (NORMALSTRING - 65)) or (1L shl (CHARSTRING - 65)) or (1L shl (LONGSTRING - 65)))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	override fun sempred(_localctx: RuleContext?, ruleIndex: Int, predIndex: Int) : Boolean {
		when (ruleIndex) {
		20 -> return exp_sempred(_localctx as ExpContext, predIndex)
		}
		return true
	}
	private fun exp_sempred( _localctx : ExpContext, predIndex: Int) : Boolean {
		when (predIndex) {
		    0 -> return precpred(context!!, 10)
		    1 -> return precpred(context!!, 8)
		    2 -> return precpred(context!!, 7)
		    3 -> return precpred(context!!, 6)
		    4 -> return precpred(context!!, 5)
		    5 -> return precpred(context!!, 4)
		    6 -> return precpred(context!!, 3)
		    7 -> return precpred(context!!, 2)
		}
		return true
	}

}