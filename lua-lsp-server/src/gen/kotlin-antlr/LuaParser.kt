// Generated from java-escape by ANTLR 4.7.1
package com.dingyi.lsp.lua.common.parser;
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
                                                              LuaParser.IfbodyContext::class,
                                                              LuaParser.ElseifbodyContext::class,
                                                              LuaParser.ElsebodyContext::class,
                                                              LuaParser.DefaultbodyContext::class,
                                                              LuaParser.CasebodyContext::class,
                                                              LuaParser.LambdabodyContext::class,
                                                              LuaParser.AttnamelistContext::class,
                                                              LuaParser.AttribContext::class,
                                                              LuaParser.RetstatContext::class,
                                                              LuaParser.LabelContext::class,
                                                              LuaParser.FuncnameContext::class,
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
        T__63(64),
        NAME(65),
        NORMALSTRING(66),
        CHARSTRING(67),
        LONGSTRING(68),
        INT(69),
        HEX(70),
        FLOAT(71),
        HEX_FLOAT(72),
        COMMENT(73),
        LINE_COMMENT(74),
        WS(75),
        SHEBANG(76)
    }

    enum class Rules(val id: Int) {
        RULE_chunk(0),
        RULE_block(1),
        RULE_stat(2),
        RULE_ifbody(3),
        RULE_elseifbody(4),
        RULE_elsebody(5),
        RULE_defaultbody(6),
        RULE_casebody(7),
        RULE_lambdabody(8),
        RULE_attnamelist(9),
        RULE_attrib(10),
        RULE_retstat(11),
        RULE_label(12),
        RULE_funcname(13),
        RULE_varlist(14),
        RULE_namelist(15),
        RULE_explist(16),
        RULE_exp(17),
        RULE_prefixexp(18),
        RULE_functioncall(19),
        RULE_varOrExp(20),
        RULE_lvar(21),
        RULE_varSuffix(22),
        RULE_nameAndArgs(23),
        RULE_args(24),
        RULE_functiondef(25),
        RULE_lambdadef(26),
        RULE_funcbody(27),
        RULE_parlist(28),
        RULE_tableconstructor(29),
        RULE_fieldlist(30),
        RULE_field(31),
        RULE_fieldsep(32),
        RULE_operatorOr(33),
        RULE_operatorAnd(34),
        RULE_operatorComparison(35),
        RULE_operatorStrcat(36),
        RULE_operatorAddSub(37),
        RULE_operatorMulDivMod(38),
        RULE_operatorBitwise(39),
        RULE_operatorUnary(40),
        RULE_operatorPower(41),
        RULE_number(42),
        RULE_string(43)
    }

	@ThreadLocal
	companion object {
	    protected val decisionToDFA : Array<DFA>
    	protected val sharedContextCache = PredictionContextCache()

        val ruleNames = arrayOf("chunk", "block", "stat", "ifbody", "elseifbody", 
                                "elsebody", "defaultbody", "casebody", "lambdabody", 
                                "attnamelist", "attrib", "retstat", "label", 
                                "funcname", "varlist", "namelist", "explist", 
                                "exp", "prefixexp", "functioncall", "varOrExp", 
                                "lvar", "varSuffix", "nameAndArgs", "args", 
                                "functiondef", "lambdadef", "funcbody", 
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
                                                          "'local'", "'$'", 
                                                          "'switch'", "'when'", 
                                                          "'else'", "'lambda'", 
                                                          "'defer'", "'elseif'", 
                                                          "'default'", "'case'", 
                                                          "':'", "'<'", 
                                                          "'>'", "'return'", 
                                                          "'::'", "'@'", 
                                                          "'.'", "'nil'", 
                                                          "'false'", "'true'", 
                                                          "'...'", "'('", 
                                                          "')'", "'['", 
                                                          "']'", "'{'", 
                                                          "'}'", "'or'", 
                                                          "'and'", "'<='", 
                                                          "'>='", "'~='", 
                                                          "'=='", "'..'", 
                                                          "'+'", "'-'", 
                                                          "'*'", "'/'", 
                                                          "'%'", "'//'", 
                                                          "'&'", "'|'", 
                                                          "'~'", "'<<'", 
                                                          "'>>'", "'not'", 
                                                          "'#'", "'^'")
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
                                                           null, null, "NAME", 
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

        private const val serializedATN : String = "\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\u0003\u004e\u01ff\u0004\u0002\u0009\u0002\u0004\u0003\u0009\u0003\u0004\u0004\u0009\u0004\u0004\u0005\u0009\u0005\u0004\u0006\u0009\u0006\u0004\u0007\u0009\u0007\u0004\u0008\u0009\u0008\u0004\u0009\u0009\u0009\u0004\u000a\u0009\u000a\u0004\u000b\u0009\u000b\u0004\u000c\u0009\u000c\u0004\u000d\u0009\u000d\u0004\u000e\u0009\u000e\u0004\u000f\u0009\u000f\u0004\u0010\u0009\u0010\u0004\u0011\u0009\u0011\u0004\u0012\u0009\u0012\u0004\u0013\u0009\u0013\u0004\u0014\u0009\u0014\u0004\u0015\u0009\u0015\u0004\u0016\u0009\u0016\u0004\u0017\u0009\u0017\u0004\u0018\u0009\u0018\u0004\u0019\u0009\u0019\u0004\u001a\u0009\u001a\u0004\u001b\u0009\u001b\u0004\u001c\u0009\u001c\u0004\u001d\u0009\u001d\u0004\u001e\u0009\u001e\u0004\u001f\u0009\u001f\u0004\u0020\u0009\u0020\u0004\u0021\u0009\u0021\u0004\u0022\u0009\u0022\u0004\u0023\u0009\u0023\u0004\u0024\u0009\u0024\u0004\u0025\u0009\u0025\u0004\u0026\u0009\u0026\u0004\u0027\u0009\u0027\u0004\u0028\u0009\u0028\u0004\u0029\u0009\u0029\u0004\u002a\u0009\u002a\u0004\u002b\u0009\u002b\u0004\u002c\u0009\u002c\u0004\u002d\u0009\u002d\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0007\u0003\u005f\u000a\u0003\u000c\u0003\u000e\u0003\u0062\u000b\u0003\u0003\u0003\u0005\u0003\u0065\u000a\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u0084\u000a\u0004\u0003\u0004\u0003\u0004\u0007\u0004\u0088\u000a\u0004\u000c\u0004\u000e\u0004\u008b\u000b\u0004\u0003\u0004\u0005\u0004\u008e\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u009a\u000a\u0004\u0003\u0004\u0005\u0004\u009d\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00a7\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00b8\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00bd\u000a\u0004\u0003\u0004\u0007\u0004\u00c0\u000a\u0004\u000c\u0004\u000e\u0004\u00c3\u000b\u0004\u0003\u0004\u0005\u0004\u00c6\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00ce\u000a\u0004\u0003\u0004\u0005\u0004\u00d1\u000a\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004\u00d9\u000a\u0004\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0009\u0003\u0009\u0003\u0009\u0005\u0009\u00eb\u000a\u0009\u0003\u0009\u0003\u0009\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0007\u000b\u00f8\u000a\u000b\u000c\u000b\u000e\u000b\u00fb\u000b\u000b\u0003\u000c\u0003\u000c\u0003\u000c\u0005\u000c\u0100\u000a\u000c\u0003\u000d\u0003\u000d\u0005\u000d\u0104\u000a\u000d\u0003\u000d\u0005\u000d\u0107\u000a\u000d\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0005\u000e\u010e\u000a\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0007\u000f\u0113\u000a\u000f\u000c\u000f\u000e\u000f\u0116\u000b\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u011a\u000a\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0007\u0010\u011f\u000a\u0010\u000c\u0010\u000e\u0010\u0122\u000b\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0007\u0011\u0127\u000a\u0011\u000c\u0011\u000e\u0011\u012a\u000b\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0007\u0012\u012f\u000a\u0012\u000c\u0012\u000e\u0012\u0132\u000b\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0005\u0013\u0143\u000a\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0007\u0013\u0165\u000a\u0013\u000c\u0013\u000e\u0013\u0168\u000b\u0013\u0003\u0014\u0003\u0014\u0007\u0014\u016c\u000a\u0014\u000c\u0014\u000e\u0014\u016f\u000b\u0014\u0003\u0015\u0003\u0015\u0006\u0015\u0173\u000a\u0015\u000d\u0015\u000e\u0015\u0174\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0005\u0016\u017c\u000a\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0005\u0017\u0184\u000a\u0017\u0003\u0017\u0007\u0017\u0187\u000a\u0017\u000c\u0017\u000e\u0017\u018a\u000b\u0017\u0003\u0018\u0007\u0018\u018d\u000a\u0018\u000c\u0018\u000e\u0018\u0190\u000b\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0005\u0018\u0198\u000a\u0018\u0003\u0018\u0005\u0018\u019b\u000a\u0018\u0003\u0019\u0003\u0019\u0005\u0019\u019f\u000a\u0019\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0005\u001a\u01a5\u000a\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0005\u001a\u01aa\u000a\u001a\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0005\u001d\u01b4\u000a\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001e\u0005\u001e\u01bd\u000a\u001e\u0003\u001e\u0005\u001e\u01c0\u000a\u001e\u0003\u001f\u0003\u001f\u0005\u001f\u01c4\u000a\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0005\u001f\u01ca\u000a\u001f\u0003\u001f\u0005\u001f\u01cd\u000a\u001f\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0020\u0007\u0020\u01d3\u000a\u0020\u000c\u0020\u000e\u0020\u01d6\u000b\u0020\u0003\u0020\u0005\u0020\u01d9\u000a\u0020\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0003\u0021\u0005\u0021\u01e5\u000a\u0021\u0003\u0022\u0003\u0022\u0003\u0023\u0003\u0023\u0003\u0024\u0003\u0024\u0003\u0025\u0003\u0025\u0003\u0026\u0003\u0026\u0003\u0027\u0003\u0027\u0003\u0028\u0003\u0028\u0003\u0029\u0003\u0029\u0003\u002a\u0003\u002a\u0003\u002b\u0003\u002b\u0003\u002c\u0003\u002c\u0003\u002d\u0003\u002d\u0003\u002d\u0002\u0003\u0024\u002e\u0002\u0004\u0006\u0008\u000a\u000c\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0020\u0022\u0024\u0026\u0028\u002a\u002c\u002e\u0030\u0032\u0034\u0036\u0038\u003a\u003c\u003e\u0040\u0042\u0044\u0046\u0048\u004a\u004c\u004e\u0050\u0052\u0054\u0056\u0058\u0002\u000c\u0003\u0002\u0013\u0014\u0004\u0002\u000e\u000e\u001d\u001d\u0004\u0002\u0003\u0003\u0010\u0010\u0004\u0002\u001e\u001f\u0030\u0033\u0003\u0002\u0035\u0036\u0003\u0002\u0037\u003a\u0003\u0002\u003b\u003f\u0005\u0002\u0036\u0036\u003d\u003d\u0040\u0041\u0003\u0002\u0047\u004a\u0003\u0002\u0044\u0046\u0002\u0229\u0002\u005a\u0003\u0002\u0002\u0002\u0004\u0060\u0003\u0002\u0002\u0002\u0006\u00d8\u0003\u0002\u0002\u0002\u0008\u00da\u0003\u0002\u0002\u0002\u000a\u00dc\u0003\u0002\u0002\u0002\u000c\u00e1\u0003\u0002\u0002\u0002\u000e\u00e4\u0003\u0002\u0002\u0002\u0010\u00e7\u0003\u0002\u0002\u0002\u0012\u00ee\u0003\u0002\u0002\u0002\u0014\u00f2\u0003\u0002\u0002\u0002\u0016\u00ff\u0003\u0002\u0002\u0002\u0018\u0101\u0003\u0002\u0002\u0002\u001a\u010d\u0003\u0002\u0002\u0002\u001c\u010f\u0003\u0002\u0002\u0002\u001e\u011b\u0003\u0002\u0002\u0002\u0020\u0123\u0003\u0002\u0002\u0002\u0022\u012b\u0003\u0002\u0002\u0002\u0024\u0142\u0003\u0002\u0002\u0002\u0026\u0169\u0003\u0002\u0002\u0002\u0028\u0170\u0003\u0002\u0002\u0002\u002a\u017b\u0003\u0002\u0002\u0002\u002c\u0183\u0003\u0002\u0002\u0002\u002e\u018e\u0003\u0002\u0002\u0002\u0030\u019e\u0003\u0002\u0002\u0002\u0032\u01a9\u0003\u0002\u0002\u0002\u0034\u01ab\u0003\u0002\u0002\u0002\u0036\u01ae\u0003\u0002\u0002\u0002\u0038\u01b1\u0003\u0002\u0002\u0002\u003a\u01bf\u0003\u0002\u0002\u0002\u003c\u01cc\u0003\u0002\u0002\u0002\u003e\u01ce\u0003\u0002\u0002\u0002\u0040\u01e4\u0003\u0002\u0002\u0002\u0042\u01e6\u0003\u0002\u0002\u0002\u0044\u01e8\u0003\u0002\u0002\u0002\u0046\u01ea\u0003\u0002\u0002\u0002\u0048\u01ec\u0003\u0002\u0002\u0002\u004a\u01ee\u0003\u0002\u0002\u0002\u004c\u01f0\u0003\u0002\u0002\u0002\u004e\u01f2\u0003\u0002\u0002\u0002\u0050\u01f4\u0003\u0002\u0002\u0002\u0052\u01f6\u0003\u0002\u0002\u0002\u0054\u01f8\u0003\u0002\u0002\u0002\u0056\u01fa\u0003\u0002\u0002\u0002\u0058\u01fc\u0003\u0002\u0002\u0002\u005a\u005b\u0005\u0004\u0003\u0002\u005b\u005c\u0007\u0002\u0002\u0003\u005c\u0003\u0003\u0002\u0002\u0002\u005d\u005f\u0005\u0006\u0004\u0002\u005e\u005d\u0003\u0002\u0002\u0002\u005f\u0062\u0003\u0002\u0002\u0002\u0060\u005e\u0003\u0002\u0002\u0002\u0060\u0061\u0003\u0002\u0002\u0002\u0061\u0064\u0003\u0002\u0002\u0002\u0062\u0060\u0003\u0002\u0002\u0002\u0063\u0065\u0005\u0018\u000d\u0002\u0064\u0063\u0003\u0002\u0002\u0002\u0064\u0065\u0003\u0002\u0002\u0002\u0065\u0005\u0003\u0002\u0002\u0002\u0066\u00d9\u0007\u0003\u0002\u0002\u0067\u0068\u0005\u001e\u0010\u0002\u0068\u0069\u0007\u0004\u0002\u0002\u0069\u006a\u0005\u0022\u0012\u0002\u006a\u00d9\u0003\u0002\u0002\u0002\u006b\u00d9\u0005\u0028\u0015\u0002\u006c\u00d9\u0005\u001a\u000e\u0002\u006d\u00d9\u0007\u0005\u0002\u0002\u006e\u00d9\u0007\u0006\u0002\u0002\u006f\u0070\u0007\u0007\u0002\u0002\u0070\u00d9\u0007\u0043\u0002\u0002\u0071\u0072\u0007\u0008\u0002\u0002\u0072\u0073\u0005\u0004\u0003\u0002\u0073\u0074\u0007\u0009\u0002\u0002\u0074\u00d9\u0003\u0002\u0002\u0002\u0075\u0076\u0007\u000a\u0002\u0002\u0076\u0077\u0005\u0024\u0013\u0002\u0077\u0078\u0007\u0008\u0002\u0002\u0078\u0079\u0005\u0004\u0003\u0002\u0079\u007a\u0007\u0009\u0002\u0002\u007a\u00d9\u0003\u0002\u0002\u0002\u007b\u007c\u0007\u000b\u0002\u0002\u007c\u007d\u0005\u0004\u0003\u0002\u007d\u007e\u0007\u000c\u0002\u0002\u007e\u007f\u0005\u0024\u0013\u0002\u007f\u00d9\u0003\u0002\u0002\u0002\u0080\u0081\u0007\u000d\u0002\u0002\u0081\u0083\u0005\u0024\u0013\u0002\u0082\u0084\u0007\u000e\u0002\u0002\u0083\u0082\u0003\u0002\u0002\u0002\u0083\u0084\u0003\u0002\u0002\u0002\u0084\u0085\u0003\u0002\u0002\u0002\u0085\u0089\u0005\u0008\u0005\u0002\u0086\u0088\u0005\u000a\u0006\u0002\u0087\u0086\u0003\u0002\u0002\u0002\u0088\u008b\u0003\u0002\u0002\u0002\u0089\u0087\u0003\u0002\u0002\u0002\u0089\u008a\u0003\u0002\u0002\u0002\u008a\u008d\u0003\u0002\u0002\u0002\u008b\u0089\u0003\u0002\u0002\u0002\u008c\u008e\u0005\u000c\u0007\u0002\u008d\u008c\u0003\u0002\u0002\u0002\u008d\u008e\u0003\u0002\u0002\u0002\u008e\u008f\u0003\u0002\u0002\u0002\u008f\u0090\u0007\u0009\u0002\u0002\u0090\u00d9\u0003\u0002\u0002\u0002\u0091\u0092\u0007\u000f\u0002\u0002\u0092\u0093\u0007\u0043\u0002\u0002\u0093\u0094\u0007\u0004\u0002\u0002\u0094\u0095\u0005\u0024\u0013\u0002\u0095\u0096\u0007\u0010\u0002\u0002\u0096\u0099\u0005\u0024\u0013\u0002\u0097\u0098\u0007\u0010\u0002\u0002\u0098\u009a\u0005\u0024\u0013\u0002\u0099\u0097\u0003\u0002\u0002\u0002\u0099\u009a\u0003\u0002\u0002\u0002\u009a\u009c\u0003\u0002\u0002\u0002\u009b\u009d\u0007\u0008\u0002\u0002\u009c\u009b\u0003\u0002\u0002\u0002\u009c\u009d\u0003\u0002\u0002\u0002\u009d\u009e\u0003\u0002\u0002\u0002\u009e\u009f\u0005\u0004\u0003\u0002\u009f\u00a0\u0007\u0009\u0002\u0002\u00a0\u00d9\u0003\u0002\u0002\u0002\u00a1\u00a2\u0007\u000f\u0002\u0002\u00a2\u00a3\u0005\u0020\u0011\u0002\u00a3\u00a4\u0007\u0011\u0002\u0002\u00a4\u00a6\u0005\u0022\u0012\u0002\u00a5\u00a7\u0007\u0008\u0002\u0002\u00a6\u00a5\u0003\u0002\u0002\u0002\u00a6\u00a7\u0003\u0002\u0002\u0002\u00a7\u00a8\u0003\u0002\u0002\u0002\u00a8\u00a9\u0005\u0004\u0003\u0002\u00a9\u00aa\u0007\u0009\u0002\u0002\u00aa\u00d9\u0003\u0002\u0002\u0002\u00ab\u00ac\u0007\u0012\u0002\u0002\u00ac\u00ad\u0005\u001c\u000f\u0002\u00ad\u00ae\u0005\u0038\u001d\u0002\u00ae\u00d9\u0003\u0002\u0002\u0002\u00af\u00b0\u0009\u0002\u0002\u0002\u00b0\u00b1\u0007\u0012\u0002\u0002\u00b1\u00b2\u0007\u0043\u0002\u0002\u00b2\u00d9\u0005\u0038\u001d\u0002\u00b3\u00b4\u0009\u0002\u0002\u0002\u00b4\u00b7\u0005\u0014\u000b\u0002\u00b5\u00b6\u0007\u0004\u0002\u0002\u00b6\u00b8\u0005\u0022\u0012\u0002\u00b7\u00b5\u0003\u0002\u0002\u0002\u00b7\u00b8\u0003\u0002\u0002\u0002\u00b8\u00d9\u0003\u0002\u0002\u0002\u00b9\u00ba\u0007\u0015\u0002\u0002\u00ba\u00bc\u0005\u0024\u0013\u0002\u00bb\u00bd\u0007\u0008\u0002\u0002\u00bc\u00bb\u0003\u0002\u0002\u0002\u00bc\u00bd\u0003\u0002\u0002\u0002\u00bd\u00c1\u0003\u0002\u0002\u0002\u00be\u00c0\u0005\u0010\u0009\u0002\u00bf\u00be\u0003\u0002\u0002\u0002\u00c0\u00c3\u0003\u0002\u0002\u0002\u00c1\u00bf\u0003\u0002\u0002\u0002\u00c1\u00c2\u0003\u0002\u0002\u0002\u00c2\u00c5\u0003\u0002\u0002\u0002\u00c3\u00c1\u0003\u0002\u0002\u0002\u00c4\u00c6\u0005\u000e\u0008\u0002\u00c5\u00c4\u0003\u0002\u0002\u0002\u00c5\u00c6\u0003\u0002\u0002\u0002\u00c6\u00c7\u0003\u0002\u0002\u0002\u00c7\u00c8\u0007\u0009\u0002\u0002\u00c8\u00d9\u0003\u0002\u0002\u0002\u00c9\u00ca\u0007\u0016\u0002\u0002\u00ca\u00cb\u0005\u0024\u0013\u0002\u00cb\u00d0\u0005\u0026\u0014\u0002\u00cc\u00ce\u0007\u0017\u0002\u0002\u00cd\u00cc\u0003\u0002\u0002\u0002\u00cd\u00ce\u0003\u0002\u0002\u0002\u00ce\u00cf\u0003\u0002\u0002\u0002\u00cf\u00d1\u0005\u0026\u0014\u0002\u00d0\u00cd\u0003\u0002\u0002\u0002\u00d0\u00d1\u0003\u0002\u0002\u0002\u00d1\u00d9\u0003\u0002\u0002\u0002\u00d2\u00d3\u0007\u0018\u0002\u0002\u00d3\u00d9\u0005\u0012\u000a\u0002\u00d4\u00d5\u0007\u0019\u0002\u0002\u00d5\u00d9\u0005\u0028\u0015\u0002\u00d6\u00d9\u0007\u004b\u0002\u0002\u00d7\u00d9\u0007\u004c\u0002\u0002\u00d8\u0066\u0003\u0002\u0002\u0002\u00d8\u0067\u0003\u0002\u0002\u0002\u00d8\u006b\u0003\u0002\u0002\u0002\u00d8\u006c\u0003\u0002\u0002\u0002\u00d8\u006d\u0003\u0002\u0002\u0002\u00d8\u006e\u0003\u0002\u0002\u0002\u00d8\u006f\u0003\u0002\u0002\u0002\u00d8\u0071\u0003\u0002\u0002\u0002\u00d8\u0075\u0003\u0002\u0002\u0002\u00d8\u007b\u0003\u0002\u0002\u0002\u00d8\u0080\u0003\u0002\u0002\u0002\u00d8\u0091\u0003\u0002\u0002\u0002\u00d8\u00a1\u0003\u0002\u0002\u0002\u00d8\u00ab\u0003\u0002\u0002\u0002\u00d8\u00af\u0003\u0002\u0002\u0002\u00d8\u00b3\u0003\u0002\u0002\u0002\u00d8\u00b9\u0003\u0002\u0002\u0002\u00d8\u00c9\u0003\u0002\u0002\u0002\u00d8\u00d2\u0003\u0002\u0002\u0002\u00d8\u00d4\u0003\u0002\u0002\u0002\u00d8\u00d6\u0003\u0002\u0002\u0002\u00d8\u00d7\u0003\u0002\u0002\u0002\u00d9\u0007\u0003\u0002\u0002\u0002\u00da\u00db\u0005\u0004\u0003\u0002\u00db\u0009\u0003\u0002\u0002\u0002\u00dc\u00dd\u0007\u001a\u0002\u0002\u00dd\u00de\u0005\u0024\u0013\u0002\u00de\u00df\u0007\u000e\u0002\u0002\u00df\u00e0\u0005\u0004\u0003\u0002\u00e0\u000b\u0003\u0002\u0002\u0002\u00e1\u00e2\u0007\u0017\u0002\u0002\u00e2\u00e3\u0005\u0004\u0003\u0002\u00e3\u000d\u0003\u0002\u0002\u0002\u00e4\u00e5\u0007\u001b\u0002\u0002\u00e5\u00e6\u0005\u0004\u0003\u0002\u00e6\u000f\u0003\u0002\u0002\u0002\u00e7\u00e8\u0007\u001c\u0002\u0002\u00e8\u00ea\u0005\u003a\u001e\u0002\u00e9\u00eb\u0009\u0003\u0002\u0002\u00ea\u00e9\u0003\u0002\u0002\u0002\u00ea\u00eb\u0003\u0002\u0002\u0002\u00eb\u00ec\u0003\u0002\u0002\u0002\u00ec\u00ed\u0005\u0004\u0003\u0002\u00ed\u0011\u0003\u0002\u0002\u0002\u00ee\u00ef\u0005\u003a\u001e\u0002\u00ef\u00f0\u0007\u001d\u0002\u0002\u00f0\u00f1\u0005\u0022\u0012\u0002\u00f1\u0013\u0003\u0002\u0002\u0002\u00f2\u00f3\u0007\u0043\u0002\u0002\u00f3\u00f9\u0005\u0016\u000c\u0002\u00f4\u00f5\u0007\u0010\u0002\u0002\u00f5\u00f6\u0007\u0043\u0002\u0002\u00f6\u00f8\u0005\u0016\u000c\u0002\u00f7\u00f4\u0003\u0002\u0002\u0002\u00f8\u00fb\u0003\u0002\u0002\u0002\u00f9\u00f7\u0003\u0002\u0002\u0002\u00f9\u00fa\u0003\u0002\u0002\u0002\u00fa\u0015\u0003\u0002\u0002\u0002\u00fb\u00f9\u0003\u0002\u0002\u0002\u00fc\u00fd\u0007\u001e\u0002\u0002\u00fd\u00fe\u0007\u0043\u0002\u0002\u00fe\u0100\u0007\u001f\u0002\u0002\u00ff\u00fc\u0003\u0002\u0002\u0002\u00ff\u0100\u0003\u0002\u0002\u0002\u0100\u0017\u0003\u0002\u0002\u0002\u0101\u0103\u0007\u0020\u0002\u0002\u0102\u0104\u0005\u0022\u0012\u0002\u0103\u0102\u0003\u0002\u0002\u0002\u0103\u0104\u0003\u0002\u0002\u0002\u0104\u0106\u0003\u0002\u0002\u0002\u0105\u0107\u0007\u0003\u0002\u0002\u0106\u0105\u0003\u0002\u0002\u0002\u0106\u0107\u0003\u0002\u0002\u0002\u0107\u0019\u0003\u0002\u0002\u0002\u0108\u0109\u0007\u0021\u0002\u0002\u0109\u010a\u0007\u0043\u0002\u0002\u010a\u010e\u0007\u0021\u0002\u0002\u010b\u010c\u0007\u0022\u0002\u0002\u010c\u010e\u0007\u0043\u0002\u0002\u010d\u0108\u0003\u0002\u0002\u0002\u010d\u010b\u0003\u0002\u0002\u0002\u010e\u001b\u0003\u0002\u0002\u0002\u010f\u0114\u0007\u0043\u0002\u0002\u0110\u0111\u0007\u0023\u0002\u0002\u0111\u0113\u0007\u0043\u0002\u0002\u0112\u0110\u0003\u0002\u0002\u0002\u0113\u0116\u0003\u0002\u0002\u0002\u0114\u0112\u0003\u0002\u0002\u0002\u0114\u0115\u0003\u0002\u0002\u0002\u0115\u0119\u0003\u0002\u0002\u0002\u0116\u0114\u0003\u0002\u0002\u0002\u0117\u0118\u0007\u001d\u0002\u0002\u0118\u011a\u0007\u0043\u0002\u0002\u0119\u0117\u0003\u0002\u0002\u0002\u0119\u011a\u0003\u0002\u0002\u0002\u011a\u001d\u0003\u0002\u0002\u0002\u011b\u0120\u0005\u002c\u0017\u0002\u011c\u011d\u0007\u0010\u0002\u0002\u011d\u011f\u0005\u002c\u0017\u0002\u011e\u011c\u0003\u0002\u0002\u0002\u011f\u0122\u0003\u0002\u0002\u0002\u0120\u011e\u0003\u0002\u0002\u0002\u0120\u0121\u0003\u0002\u0002\u0002\u0121\u001f\u0003\u0002\u0002\u0002\u0122\u0120\u0003\u0002\u0002\u0002\u0123\u0128\u0007\u0043\u0002\u0002\u0124\u0125\u0007\u0010\u0002\u0002\u0125\u0127\u0007\u0043\u0002\u0002\u0126\u0124\u0003\u0002\u0002\u0002\u0127\u012a\u0003\u0002\u0002\u0002\u0128\u0126\u0003\u0002\u0002\u0002\u0128\u0129\u0003\u0002\u0002\u0002\u0129\u0021\u0003\u0002\u0002\u0002\u012a\u0128\u0003\u0002\u0002\u0002\u012b\u0130\u0005\u0024\u0013\u0002\u012c\u012d\u0007\u0010\u0002\u0002\u012d\u012f\u0005\u0024\u0013\u0002\u012e\u012c\u0003\u0002\u0002\u0002\u012f\u0132\u0003\u0002\u0002\u0002\u0130\u012e\u0003\u0002\u0002\u0002\u0130\u0131\u0003\u0002\u0002\u0002\u0131\u0023\u0003\u0002\u0002\u0002\u0132\u0130\u0003\u0002\u0002\u0002\u0133\u0134\u0008\u0013\u0001\u0002\u0134\u0143\u0007\u0024\u0002\u0002\u0135\u0143\u0007\u0025\u0002\u0002\u0136\u0143\u0007\u0026\u0002\u0002\u0137\u0143\u0005\u0056\u002c\u0002\u0138\u0143\u0005\u0058\u002d\u0002\u0139\u0143\u0005\u0028\u0015\u0002\u013a\u0143\u0007\u0027\u0002\u0002\u013b\u0143\u0005\u0034\u001b\u0002\u013c\u0143\u0005\u0026\u0014\u0002\u013d\u0143\u0005\u003c\u001f\u0002\u013e\u013f\u0005\u0052\u002a\u0002\u013f\u0140\u0005\u0024\u0013\u000b\u0140\u0143\u0003\u0002\u0002\u0002\u0141\u0143\u0005\u0036\u001c\u0002\u0142\u0133\u0003\u0002\u0002\u0002\u0142\u0135\u0003\u0002\u0002\u0002\u0142\u0136\u0003\u0002\u0002\u0002\u0142\u0137\u0003\u0002\u0002\u0002\u0142\u0138\u0003\u0002\u0002\u0002\u0142\u0139\u0003\u0002\u0002\u0002\u0142\u013a\u0003\u0002\u0002\u0002\u0142\u013b\u0003\u0002\u0002\u0002\u0142\u013c\u0003\u0002\u0002\u0002\u0142\u013d\u0003\u0002\u0002\u0002\u0142\u013e\u0003\u0002\u0002\u0002\u0142\u0141\u0003\u0002\u0002\u0002\u0143\u0166\u0003\u0002\u0002\u0002\u0144\u0145\u000c\u000c\u0002\u0002\u0145\u0146\u0005\u0054\u002b\u0002\u0146\u0147\u0005\u0024\u0013\u000c\u0147\u0165\u0003\u0002\u0002\u0002\u0148\u0149\u000c\u000a\u0002\u0002\u0149\u014a\u0005\u004e\u0028\u0002\u014a\u014b\u0005\u0024\u0013\u000b\u014b\u0165\u0003\u0002\u0002\u0002\u014c\u014d\u000c\u0009\u0002\u0002\u014d\u014e\u0005\u004c\u0027\u0002\u014e\u014f\u0005\u0024\u0013\u000a\u014f\u0165\u0003\u0002\u0002\u0002\u0150\u0151\u000c\u0008\u0002\u0002\u0151\u0152\u0005\u004a\u0026\u0002\u0152\u0153\u0005\u0024\u0013\u0008\u0153\u0165\u0003\u0002\u0002\u0002\u0154\u0155\u000c\u0007\u0002\u0002\u0155\u0156\u0005\u0048\u0025\u0002\u0156\u0157\u0005\u0024\u0013\u0008\u0157\u0165\u0003\u0002\u0002\u0002\u0158\u0159\u000c\u0006\u0002\u0002\u0159\u015a\u0005\u0046\u0024\u0002\u015a\u015b\u0005\u0024\u0013\u0007\u015b\u0165\u0003\u0002\u0002\u0002\u015c\u015d\u000c\u0005\u0002\u0002\u015d\u015e\u0005\u0044\u0023\u0002\u015e\u015f\u0005\u0024\u0013\u0006\u015f\u0165\u0003\u0002\u0002\u0002\u0160\u0161\u000c\u0004\u0002\u0002\u0161\u0162\u0005\u0050\u0029\u0002\u0162\u0163\u0005\u0024\u0013\u0005\u0163\u0165\u0003\u0002\u0002\u0002\u0164\u0144\u0003\u0002\u0002\u0002\u0164\u0148\u0003\u0002\u0002\u0002\u0164\u014c\u0003\u0002\u0002\u0002\u0164\u0150\u0003\u0002\u0002\u0002\u0164\u0154\u0003\u0002\u0002\u0002\u0164\u0158\u0003\u0002\u0002\u0002\u0164\u015c\u0003\u0002\u0002\u0002\u0164\u0160\u0003\u0002\u0002\u0002\u0165\u0168\u0003\u0002\u0002\u0002\u0166\u0164\u0003\u0002\u0002\u0002\u0166\u0167\u0003\u0002\u0002\u0002\u0167\u0025\u0003\u0002\u0002\u0002\u0168\u0166\u0003\u0002\u0002\u0002\u0169\u016d\u0005\u002a\u0016\u0002\u016a\u016c\u0005\u0030\u0019\u0002\u016b\u016a\u0003\u0002\u0002\u0002\u016c\u016f\u0003\u0002\u0002\u0002\u016d\u016b\u0003\u0002\u0002\u0002\u016d\u016e\u0003\u0002\u0002\u0002\u016e\u0027\u0003\u0002\u0002\u0002\u016f\u016d\u0003\u0002\u0002\u0002\u0170\u0172\u0005\u002a\u0016\u0002\u0171\u0173\u0005\u0030\u0019\u0002\u0172\u0171\u0003\u0002\u0002\u0002\u0173\u0174\u0003\u0002\u0002\u0002\u0174\u0172\u0003\u0002\u0002\u0002\u0174\u0175\u0003\u0002\u0002\u0002\u0175\u0029\u0003\u0002\u0002\u0002\u0176\u017c\u0005\u002c\u0017\u0002\u0177\u0178\u0007\u0028\u0002\u0002\u0178\u0179\u0005\u0024\u0013\u0002\u0179\u017a\u0007\u0029\u0002\u0002\u017a\u017c\u0003\u0002\u0002\u0002\u017b\u0176\u0003\u0002\u0002\u0002\u017b\u0177\u0003\u0002\u0002\u0002\u017c\u002b\u0003\u0002\u0002\u0002\u017d\u0184\u0007\u0043\u0002\u0002\u017e\u017f\u0007\u0028\u0002\u0002\u017f\u0180\u0005\u0024\u0013\u0002\u0180\u0181\u0007\u0029\u0002\u0002\u0181\u0182\u0005\u002e\u0018\u0002\u0182\u0184\u0003\u0002\u0002\u0002\u0183\u017d\u0003\u0002\u0002\u0002\u0183\u017e\u0003\u0002\u0002\u0002\u0184\u0188\u0003\u0002\u0002\u0002\u0185\u0187\u0005\u002e\u0018\u0002\u0186\u0185\u0003\u0002\u0002\u0002\u0187\u018a\u0003\u0002\u0002\u0002\u0188\u0186\u0003\u0002\u0002\u0002\u0188\u0189\u0003\u0002\u0002\u0002\u0189\u002d\u0003\u0002\u0002\u0002\u018a\u0188\u0003\u0002\u0002\u0002\u018b\u018d\u0005\u0030\u0019\u0002\u018c\u018b\u0003\u0002\u0002\u0002\u018d\u0190\u0003\u0002\u0002\u0002\u018e\u018c\u0003\u0002\u0002\u0002\u018e\u018f\u0003\u0002\u0002\u0002\u018f\u0197\u0003\u0002\u0002\u0002\u0190\u018e\u0003\u0002\u0002\u0002\u0191\u0192\u0007\u002a\u0002\u0002\u0192\u0193\u0005\u0024\u0013\u0002\u0193\u0194\u0007\u002b\u0002\u0002\u0194\u0198\u0003\u0002\u0002\u0002\u0195\u0196\u0007\u0023\u0002\u0002\u0196\u0198\u0007\u0043\u0002\u0002\u0197\u0191\u0003\u0002\u0002\u0002\u0197\u0195\u0003\u0002\u0002\u0002\u0198\u019a\u0003\u0002\u0002\u0002\u0199\u019b\u0005\u0032\u001a\u0002\u019a\u0199\u0003\u0002\u0002\u0002\u019a\u019b\u0003\u0002\u0002\u0002\u019b\u002f\u0003\u0002\u0002\u0002\u019c\u019d\u0007\u001d\u0002\u0002\u019d\u019f\u0007\u0043\u0002\u0002\u019e\u019c\u0003\u0002\u0002\u0002\u019e\u019f\u0003\u0002\u0002\u0002\u019f\u01a0\u0003\u0002\u0002\u0002\u01a0\u01a1\u0005\u0032\u001a\u0002\u01a1\u0031\u0003\u0002\u0002\u0002\u01a2\u01a4\u0007\u0028\u0002\u0002\u01a3\u01a5\u0005\u0022\u0012\u0002\u01a4\u01a3\u0003\u0002\u0002\u0002\u01a4\u01a5\u0003\u0002\u0002\u0002\u01a5\u01a6\u0003\u0002\u0002\u0002\u01a6\u01aa\u0007\u0029\u0002\u0002\u01a7\u01aa\u0005\u003c\u001f\u0002\u01a8\u01aa\u0005\u0058\u002d\u0002\u01a9\u01a2\u0003\u0002\u0002\u0002\u01a9\u01a7\u0003\u0002\u0002\u0002\u01a9\u01a8\u0003\u0002\u0002\u0002\u01aa\u0033\u0003\u0002\u0002\u0002\u01ab\u01ac\u0007\u0012\u0002\u0002\u01ac\u01ad\u0005\u0038\u001d\u0002\u01ad\u0035\u0003\u0002\u0002\u0002\u01ae\u01af\u0007\u0018\u0002\u0002\u01af\u01b0\u0005\u0012\u000a\u0002\u01b0\u0037\u0003\u0002\u0002\u0002\u01b1\u01b3\u0007\u0028\u0002\u0002\u01b2\u01b4\u0005\u003a\u001e\u0002\u01b3\u01b2\u0003\u0002\u0002\u0002\u01b3\u01b4\u0003\u0002\u0002\u0002\u01b4\u01b5\u0003\u0002\u0002\u0002\u01b5\u01b6\u0007\u0029\u0002\u0002\u01b6\u01b7\u0005\u0004\u0003\u0002\u01b7\u01b8\u0007\u0009\u0002\u0002\u01b8\u0039\u0003\u0002\u0002\u0002\u01b9\u01bc\u0005\u0020\u0011\u0002\u01ba\u01bb\u0007\u0010\u0002\u0002\u01bb\u01bd\u0007\u0027\u0002\u0002\u01bc\u01ba\u0003\u0002\u0002\u0002\u01bc\u01bd\u0003\u0002\u0002\u0002\u01bd\u01c0\u0003\u0002\u0002\u0002\u01be\u01c0\u0007\u0027\u0002\u0002\u01bf\u01b9\u0003\u0002\u0002\u0002\u01bf\u01be\u0003\u0002\u0002\u0002\u01c0\u003b\u0003\u0002\u0002\u0002\u01c1\u01c3\u0007\u002c\u0002\u0002\u01c2\u01c4\u0005\u003e\u0020\u0002\u01c3\u01c2\u0003\u0002\u0002\u0002\u01c3\u01c4\u0003\u0002\u0002\u0002\u01c4\u01c5\u0003\u0002\u0002\u0002\u01c5\u01cd\u0007\u002d\u0002\u0002\u01c6\u01c7\u0007\u0043\u0002\u0002\u01c7\u01c9\u0007\u002a\u0002\u0002\u01c8\u01ca\u0005\u0056\u002c\u0002\u01c9\u01c8\u0003\u0002\u0002\u0002\u01c9\u01ca\u0003\u0002\u0002\u0002\u01ca\u01cb\u0003\u0002\u0002\u0002\u01cb\u01cd\u0007\u002b\u0002\u0002\u01cc\u01c1\u0003\u0002\u0002\u0002\u01cc\u01c6\u0003\u0002\u0002\u0002\u01cd\u003d\u0003\u0002\u0002\u0002\u01ce\u01d4\u0005\u0040\u0021\u0002\u01cf\u01d0\u0005\u0042\u0022\u0002\u01d0\u01d1\u0005\u0040\u0021\u0002\u01d1\u01d3\u0003\u0002\u0002\u0002\u01d2\u01cf\u0003\u0002\u0002\u0002\u01d3\u01d6\u0003\u0002\u0002\u0002\u01d4\u01d2\u0003\u0002\u0002\u0002\u01d4\u01d5\u0003\u0002\u0002\u0002\u01d5\u01d8\u0003\u0002\u0002\u0002\u01d6\u01d4\u0003\u0002\u0002\u0002\u01d7\u01d9\u0005\u0042\u0022\u0002\u01d8\u01d7\u0003\u0002\u0002\u0002\u01d8\u01d9\u0003\u0002\u0002\u0002\u01d9\u003f\u0003\u0002\u0002\u0002\u01da\u01db\u0007\u002a\u0002\u0002\u01db\u01dc\u0005\u0024\u0013\u0002\u01dc\u01dd\u0007\u002b\u0002\u0002\u01dd\u01de\u0007\u0004\u0002\u0002\u01de\u01df\u0005\u0024\u0013\u0002\u01df\u01e5\u0003\u0002\u0002\u0002\u01e0\u01e1\u0007\u0043\u0002\u0002\u01e1\u01e2\u0007\u0004\u0002\u0002\u01e2\u01e5\u0005\u0024\u0013\u0002\u01e3\u01e5\u0005\u0024\u0013\u0002\u01e4\u01da\u0003\u0002\u0002\u0002\u01e4\u01e0\u0003\u0002\u0002\u0002\u01e4\u01e3\u0003\u0002\u0002\u0002\u01e5\u0041\u0003\u0002\u0002\u0002\u01e6\u01e7\u0009\u0004\u0002\u0002\u01e7\u0043\u0003\u0002\u0002\u0002\u01e8\u01e9\u0007\u002e\u0002\u0002\u01e9\u0045\u0003\u0002\u0002\u0002\u01ea\u01eb\u0007\u002f\u0002\u0002\u01eb\u0047\u0003\u0002\u0002\u0002\u01ec\u01ed\u0009\u0005\u0002\u0002\u01ed\u0049\u0003\u0002\u0002\u0002\u01ee\u01ef\u0007\u0034\u0002\u0002\u01ef\u004b\u0003\u0002\u0002\u0002\u01f0\u01f1\u0009\u0006\u0002\u0002\u01f1\u004d\u0003\u0002\u0002\u0002\u01f2\u01f3\u0009\u0007\u0002\u0002\u01f3\u004f\u0003\u0002\u0002\u0002\u01f4\u01f5\u0009\u0008\u0002\u0002\u01f5\u0051\u0003\u0002\u0002\u0002\u01f6\u01f7\u0009\u0009\u0002\u0002\u01f7\u0053\u0003\u0002\u0002\u0002\u01f8\u01f9\u0007\u0042\u0002\u0002\u01f9\u0055\u0003\u0002\u0002\u0002\u01fa\u01fb\u0009\u000a\u0002\u0002\u01fb\u0057\u0003\u0002\u0002\u0002\u01fc\u01fd\u0009\u000b\u0002\u0002\u01fd\u0059\u0003\u0002\u0002\u0002\u0033\u0060\u0064\u0083\u0089\u008d\u0099\u009c\u00a6\u00b7\u00bc\u00c1\u00c5\u00cd\u00d0\u00d8\u00ea\u00f9\u00ff\u0103\u0106\u010d\u0114\u0119\u0120\u0128\u0130\u0142\u0164\u0166\u016d\u0174\u017b\u0183\u0188\u018e\u0197\u019a\u019e\u01a4\u01a9\u01b3\u01bc\u01bf\u01c3\u01c9\u01cc\u01d4\u01d8\u01e4"

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
    private val T__63 = Tokens.T__63.id
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
			this.state = 88
			block()
			this.state = 89
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
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 94
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__0) or (1L shl T__2) or (1L shl T__3) or (1L shl T__4) or (1L shl T__5) or (1L shl T__7) or (1L shl T__8) or (1L shl T__10) or (1L shl T__12) or (1L shl T__15) or (1L shl T__16) or (1L shl T__17) or (1L shl T__18) or (1L shl T__19) or (1L shl T__21) or (1L shl T__22) or (1L shl T__30) or (1L shl T__31) or (1L shl T__37))) != 0L) || ((((_la - 65)) and 0x3f.inv()) == 0 && ((1L shl (_la - 65)) and ((1L shl (NAME - 65)) or (1L shl (COMMENT - 65)) or (1L shl (LINE_COMMENT - 65)))) != 0L)) {
				if (true){
				if (true){
				this.state = 91
				stat()
				}
				}
				this.state = 96
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 98
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__29) {
				if (true){
				this.state = 97
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
	open class DefaultStatContext : StatContext {
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterDefaultStat(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitDefaultStat(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitDefaultStat(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class SwitchStatContext : StatContext {
		fun findExp() : ExpContext? = getRuleContext(solver.getType("ExpContext"),0)
		fun findCasebody() : List<CasebodyContext> = getRuleContexts(solver.getType("CasebodyContext"))
		fun findCasebody(i: Int) : CasebodyContext? = getRuleContext(solver.getType("CasebodyContext"),i)
		fun findDefaultbody() : DefaultbodyContext? = getRuleContext(solver.getType("DefaultbodyContext"),0)
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
	open class CommentContext : StatContext {
		fun COMMENT() : TerminalNode? = getToken(LuaParser.Tokens.COMMENT.id, 0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
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
	open class LineCommentContext : StatContext {
		fun LINE_COMMENT() : TerminalNode? = getToken(LuaParser.Tokens.LINE_COMMENT.id, 0)
		constructor(ctx: StatContext) { copyFrom(ctx) }
		override fun enterRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).enterLineComment(this)
		}
		override fun exitRule(listener: ParseTreeListener) {
			if ( listener is LuaListener ) (listener as LuaListener).exitLineComment(this)
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is LuaVisitor ) return (visitor as LuaVisitor<out T>).visitLineComment(this)
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
			this.state = 214
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,14,context) ) {
			1 -> {_localctx = DefaultStatContext(_localctx)
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 100
			match(T__0) as Token
			}}
			2 -> {_localctx = VarListStatContext(_localctx)
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 101
			varlist()
			this.state = 102
			match(T__1) as Token
			this.state = 103
			explist()
			}}
			3 -> {_localctx = FunctionCallStatContext(_localctx)
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 105
			functioncall()
			}}
			4 -> {_localctx = LabelStatContext(_localctx)
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 106
			label()
			}}
			5 -> {_localctx = BreakStatContext(_localctx)
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 107
			match(T__2) as Token
			}}
			6 -> {_localctx = ContinueStatContext(_localctx)
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 108
			match(T__3) as Token
			}}
			7 -> {_localctx = GotoStatContext(_localctx)
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 109
			match(T__4) as Token
			this.state = 110
			match(NAME) as Token
			}}
			8 -> {_localctx = DoStatContext(_localctx)
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 111
			match(T__5) as Token
			this.state = 112
			block()
			this.state = 113
			match(T__6) as Token
			}}
			9 -> {_localctx = WhileStatContext(_localctx)
			enterOuterAlt(_localctx, 9)
			if (true){
			this.state = 115
			match(T__7) as Token
			this.state = 116
			exp(0)
			this.state = 117
			match(T__5) as Token
			this.state = 118
			block()
			this.state = 119
			match(T__6) as Token
			}}
			10 -> {_localctx = RepeatStatContext(_localctx)
			enterOuterAlt(_localctx, 10)
			if (true){
			this.state = 121
			match(T__8) as Token
			this.state = 122
			block()
			this.state = 123
			match(T__9) as Token
			this.state = 124
			exp(0)
			}}
			11 -> {_localctx = IfStatContext(_localctx)
			enterOuterAlt(_localctx, 11)
			if (true){
			this.state = 126
			match(T__10) as Token
			this.state = 127
			exp(0)
			this.state = 129
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__11) {
				if (true){
				this.state = 128
				match(T__11) as Token
				}
			}

			this.state = 131
			ifbody()
			this.state = 135
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__23) {
				if (true){
				if (true){
				this.state = 132
				elseifbody()
				}
				}
				this.state = 137
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 139
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__20) {
				if (true){
				this.state = 138
				elsebody()
				}
			}

			this.state = 141
			match(T__6) as Token
			}}
			12 -> {_localctx = ForStatContext(_localctx)
			enterOuterAlt(_localctx, 12)
			if (true){
			this.state = 143
			match(T__12) as Token
			this.state = 144
			match(NAME) as Token
			this.state = 145
			match(T__1) as Token
			this.state = 146
			exp(0)
			this.state = 147
			match(T__13) as Token
			this.state = 148
			exp(0)
			this.state = 151
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__13) {
				if (true){
				this.state = 149
				match(T__13) as Token
				this.state = 150
				exp(0)
				}
			}

			this.state = 154
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,6,context) ) {
			1   -> if (true){
			this.state = 153
			match(T__5) as Token
			}
			}
			this.state = 156
			block()
			this.state = 157
			match(T__6) as Token
			}}
			13 -> {_localctx = ForInStatContext(_localctx)
			enterOuterAlt(_localctx, 13)
			if (true){
			this.state = 159
			match(T__12) as Token
			this.state = 160
			namelist()
			this.state = 161
			match(T__14) as Token
			this.state = 162
			explist()
			this.state = 164
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,7,context) ) {
			1   -> if (true){
			this.state = 163
			match(T__5) as Token
			}
			}
			this.state = 166
			block()
			this.state = 167
			match(T__6) as Token
			}}
			14 -> {_localctx = FunctionDefStatContext(_localctx)
			enterOuterAlt(_localctx, 14)
			if (true){
			this.state = 169
			match(T__15) as Token
			this.state = 170
			funcname()
			this.state = 171
			funcbody()
			}}
			15 -> {_localctx = LocalFunctionDefStatContext(_localctx)
			enterOuterAlt(_localctx, 15)
			if (true){
			this.state = 173
			_la = _input!!.LA(1)
			if ( !(_la==T__16 || _la==T__17) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 174
			match(T__15) as Token
			this.state = 175
			match(NAME) as Token
			this.state = 176
			funcbody()
			}}
			16 -> {_localctx = LocalVarListStatContext(_localctx)
			enterOuterAlt(_localctx, 16)
			if (true){
			this.state = 177
			_la = _input!!.LA(1)
			if ( !(_la==T__16 || _la==T__17) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 178
			attnamelist()
			this.state = 181
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__1) {
				if (true){
				this.state = 179
				match(T__1) as Token
				this.state = 180
				explist()
				}
			}

			}}
			17 -> {_localctx = SwitchStatContext(_localctx)
			enterOuterAlt(_localctx, 17)
			if (true){
			this.state = 183
			match(T__18) as Token
			this.state = 184
			exp(0)
			this.state = 186
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 185
				match(T__5) as Token
				}
			}

			this.state = 191
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__25) {
				if (true){
				if (true){
				this.state = 188
				casebody()
				}
				}
				this.state = 193
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 195
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__24) {
				if (true){
				this.state = 194
				defaultbody()
				}
			}

			this.state = 197
			match(T__6) as Token
			}}
			18 -> {_localctx = WhenStatContext(_localctx)
			enterOuterAlt(_localctx, 18)
			if (true){
			this.state = 199
			match(T__19) as Token
			this.state = 200
			exp(0)
			this.state = 201
			prefixexp()
			this.state = 206
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,13,context) ) {
			1   -> if (true){
			this.state = 203
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__20) {
				if (true){
				this.state = 202
				match(T__20) as Token
				}
			}

			this.state = 205
			prefixexp()
			}
			}
			}}
			19 -> {_localctx = LambdaStatContext(_localctx)
			enterOuterAlt(_localctx, 19)
			if (true){
			this.state = 208
			match(T__21) as Token
			this.state = 209
			lambdabody()
			}}
			20 -> {_localctx = DeferStatContext(_localctx)
			enterOuterAlt(_localctx, 20)
			if (true){
			this.state = 210
			match(T__22) as Token
			this.state = 211
			functioncall()
			}}
			21 -> {_localctx = CommentContext(_localctx)
			enterOuterAlt(_localctx, 21)
			if (true){
			this.state = 212
			match(COMMENT) as Token
			}}
			22 -> {_localctx = LineCommentContext(_localctx)
			enterOuterAlt(_localctx, 22)
			if (true){
			this.state = 213
			match(LINE_COMMENT) as Token
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
		enterRule(_localctx, 6, Rules.RULE_ifbody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 216
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
		enterRule(_localctx, 8, Rules.RULE_elseifbody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 218
			match(T__23) as Token
			this.state = 219
			exp(0)
			this.state = 220
			match(T__11) as Token
			this.state = 221
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
		enterRule(_localctx, 10, Rules.RULE_elsebody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 223
			match(T__20) as Token
			this.state = 224
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
		enterRule(_localctx, 12, Rules.RULE_defaultbody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 226
			match(T__24) as Token
			this.state = 227
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
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		fun findParlist() : ParlistContext? = getRuleContext(solver.getType("ParlistContext"),0)
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
		enterRule(_localctx, 14, Rules.RULE_casebody.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 229
			match(T__25) as Token
			if (true){
			this.state = 230
			parlist()
			}
			this.state = 232
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__11 || _la==T__26) {
				if (true){
				this.state = 231
				_la = _input!!.LA(1)
				if ( !(_la==T__11 || _la==T__26) ) {
					errorHandler.recoverInline(this)
				}
				else {
					if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
					errorHandler.reportMatch(this)
					consume()
				}
				}
			}

			this.state = 234
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
		enterRule(_localctx, 16, Rules.RULE_lambdabody.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			if (true){
			this.state = 236
			parlist()
			}
			this.state = 237
			match(T__26) as Token
			if (true){
			this.state = 238
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
		enterRule(_localctx, 18, Rules.RULE_attnamelist.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 240
			match(NAME) as Token
			this.state = 241
			attrib()
			this.state = 247
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__13) {
				if (true){
				if (true){
				this.state = 242
				match(T__13) as Token
				this.state = 243
				match(NAME) as Token
				this.state = 244
				attrib()
				}
				}
				this.state = 249
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
		enterRule(_localctx, 20, Rules.RULE_attrib.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 253
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__27) {
				if (true){
				this.state = 250
				match(T__27) as Token
				this.state = 251
				match(NAME) as Token
				this.state = 252
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
		enterRule(_localctx, 22, Rules.RULE_retstat.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 255
			match(T__29) as Token
			this.state = 257
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 16)) and 0x3f.inv()) == 0 && ((1L shl (_la - 16)) and ((1L shl (T__15 - 16)) or (1L shl (T__21 - 16)) or (1L shl (T__33 - 16)) or (1L shl (T__34 - 16)) or (1L shl (T__35 - 16)) or (1L shl (T__36 - 16)) or (1L shl (T__37 - 16)) or (1L shl (T__41 - 16)) or (1L shl (T__51 - 16)) or (1L shl (T__58 - 16)) or (1L shl (T__61 - 16)) or (1L shl (T__62 - 16)) or (1L shl (NAME - 16)) or (1L shl (NORMALSTRING - 16)) or (1L shl (CHARSTRING - 16)) or (1L shl (LONGSTRING - 16)) or (1L shl (INT - 16)) or (1L shl (HEX - 16)) or (1L shl (FLOAT - 16)) or (1L shl (HEX_FLOAT - 16)))) != 0L)) {
				if (true){
				this.state = 256
				explist()
				}
			}

			this.state = 260
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__0) {
				if (true){
				this.state = 259
				match(T__0) as Token
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
		enterRule(_localctx, 24, Rules.RULE_label.id)
		try {
			this.state = 267
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__30  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 262
			match(T__30) as Token
			this.state = 263
			match(NAME) as Token
			this.state = 264
			match(T__30) as Token
			}}
			T__31  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 265
			match(T__31) as Token
			this.state = 266
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
		enterRule(_localctx, 26, Rules.RULE_funcname.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 269
			match(NAME) as Token
			this.state = 274
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__32) {
				if (true){
				if (true){
				this.state = 270
				match(T__32) as Token
				this.state = 271
				match(NAME) as Token
				}
				}
				this.state = 276
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 279
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__26) {
				if (true){
				this.state = 277
				match(T__26) as Token
				this.state = 278
				match(NAME) as Token
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
		enterRule(_localctx, 28, Rules.RULE_varlist.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 281
			lvar()
			this.state = 286
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__13) {
				if (true){
				if (true){
				this.state = 282
				match(T__13) as Token
				this.state = 283
				lvar()
				}
				}
				this.state = 288
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
		enterRule(_localctx, 30, Rules.RULE_namelist.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 289
			match(NAME) as Token
			this.state = 294
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,24,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 290
					match(T__13) as Token
					this.state = 291
					match(NAME) as Token
					}
					} 
				}
				this.state = 296
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,24,context)
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
		enterRule(_localctx, 32, Rules.RULE_explist.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 297
			exp(0)
			this.state = 302
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,25,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 298
					match(T__13) as Token
					this.state = 299
					exp(0)
					}
					} 
				}
				this.state = 304
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,25,context)
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
		var _startState : Int = 34
		enterRecursionRule(_localctx, 34, Rules.RULE_exp.id, _p)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 320
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,26,context) ) {
			1 -> {if (true){
			this.state = 306
			match(T__33) as Token
			}}
			2 -> {if (true){
			this.state = 307
			match(T__34) as Token
			}}
			3 -> {if (true){
			this.state = 308
			match(T__35) as Token
			}}
			4 -> {if (true){
			this.state = 309
			number()
			}}
			5 -> {if (true){
			this.state = 310
			string()
			}}
			6 -> {if (true){
			this.state = 311
			functioncall()
			}}
			7 -> {if (true){
			this.state = 312
			match(T__36) as Token
			}}
			8 -> {if (true){
			this.state = 313
			functiondef()
			}}
			9 -> {if (true){
			this.state = 314
			prefixexp()
			}}
			10 -> {if (true){
			this.state = 315
			tableconstructor()
			}}
			11 -> {if (true){
			this.state = 316
			operatorUnary()
			this.state = 317
			exp(9)
			}}
			12 -> {if (true){
			this.state = 319
			lambdadef()
			}}
			}
			this.context!!.stop = _input!!.LT(-1)
			this.state = 356
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,28,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent()
					    _prevctx = _localctx
					if (true){
					this.state = 354
					errorHandler.sync(this)
					when ( interpreter!!.adaptivePredict(_input!!,27,context) ) {
					1 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 322
					if (!(precpred(context!!, 10))) throw FailedPredicateException(this, "precpred(context!!, 10)")
					this.state = 323
					operatorPower()
					this.state = 324
					exp(10)
					}}
					2 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 326
					if (!(precpred(context!!, 8))) throw FailedPredicateException(this, "precpred(context!!, 8)")
					this.state = 327
					operatorMulDivMod()
					this.state = 328
					exp(9)
					}}
					3 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 330
					if (!(precpred(context!!, 7))) throw FailedPredicateException(this, "precpred(context!!, 7)")
					this.state = 331
					operatorAddSub()
					this.state = 332
					exp(8)
					}}
					4 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 334
					if (!(precpred(context!!, 6))) throw FailedPredicateException(this, "precpred(context!!, 6)")
					this.state = 335
					operatorStrcat()
					this.state = 336
					exp(6)
					}}
					5 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 338
					if (!(precpred(context!!, 5))) throw FailedPredicateException(this, "precpred(context!!, 5)")
					this.state = 339
					operatorComparison()
					this.state = 340
					exp(6)
					}}
					6 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 342
					if (!(precpred(context!!, 4))) throw FailedPredicateException(this, "precpred(context!!, 4)")
					this.state = 343
					operatorAnd()
					this.state = 344
					exp(5)
					}}
					7 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 346
					if (!(precpred(context!!, 3))) throw FailedPredicateException(this, "precpred(context!!, 3)")
					this.state = 347
					operatorOr()
					this.state = 348
					exp(4)
					}}
					8 -> {if (true){
					_localctx = ExpContext(_parentctx, _parentState)
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_exp.id)
					this.state = 350
					if (!(precpred(context!!, 2))) throw FailedPredicateException(this, "precpred(context!!, 2)")
					this.state = 351
					operatorBitwise()
					this.state = 352
					exp(3)
					}}
					}
					} 
				}
				this.state = 358
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,28,context)
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
		enterRule(_localctx, 36, Rules.RULE_prefixexp.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 359
			varOrExp()
			this.state = 363
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,29,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 360
					nameAndArgs()
					}
					} 
				}
				this.state = 365
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,29,context)
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
		enterRule(_localctx, 38, Rules.RULE_functioncall.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 366
			varOrExp()
			this.state = 368 
			errorHandler.sync(this)
			_alt = 1
			do {
				when (_alt) {
				    1 -> if (true){
				if (true){
				this.state = 367
				nameAndArgs()
				}
				}
				else -> throw NoViableAltException(this)
				}
				this.state = 370 
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,30,context)
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
		enterRule(_localctx, 40, Rules.RULE_varOrExp.id)
		try {
			this.state = 377
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,31,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 372
			lvar()
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 373
			match(T__37) as Token
			this.state = 374
			exp(0)
			this.state = 375
			match(T__38) as Token
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
		enterRule(_localctx, 42, Rules.RULE_lvar.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 385
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			NAME  ->  /*LL1AltBlock*/{if (true){
			this.state = 379
			match(NAME) as Token
			}}
			T__37  ->  /*LL1AltBlock*/{if (true){
			this.state = 380
			match(T__37) as Token
			this.state = 381
			exp(0)
			this.state = 382
			match(T__38) as Token
			this.state = 383
			varSuffix()
			}}
			else -> throw NoViableAltException(this)
			}
			this.state = 390
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,33,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 387
					varSuffix()
					}
					} 
				}
				this.state = 392
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,33,context)
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
		enterRule(_localctx, 44, Rules.RULE_varSuffix.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 396
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (((((_la - 27)) and 0x3f.inv()) == 0 && ((1L shl (_la - 27)) and ((1L shl (T__26 - 27)) or (1L shl (T__37 - 27)) or (1L shl (T__41 - 27)) or (1L shl (NAME - 27)) or (1L shl (NORMALSTRING - 27)) or (1L shl (CHARSTRING - 27)) or (1L shl (LONGSTRING - 27)))) != 0L)) {
				if (true){
				if (true){
				this.state = 393
				nameAndArgs()
				}
				}
				this.state = 398
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 405
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__39  ->  /*LL1AltBlock*/{if (true){
			this.state = 399
			match(T__39) as Token
			this.state = 400
			exp(0)
			this.state = 401
			match(T__40) as Token
			}}
			T__32  ->  /*LL1AltBlock*/{if (true){
			this.state = 403
			match(T__32) as Token
			this.state = 404
			match(NAME) as Token
			}}
			else -> throw NoViableAltException(this)
			}
			this.state = 408
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,36,context) ) {
			1   -> if (true){
			this.state = 407
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
		enterRule(_localctx, 46, Rules.RULE_nameAndArgs.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 412
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__26) {
				if (true){
				this.state = 410
				match(T__26) as Token
				this.state = 411
				match(NAME) as Token
				}
			}

			this.state = 414
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
		enterRule(_localctx, 48, Rules.RULE_args.id)
		var _la: Int
		try {
			this.state = 423
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__37  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 416
			match(T__37) as Token
			this.state = 418
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 16)) and 0x3f.inv()) == 0 && ((1L shl (_la - 16)) and ((1L shl (T__15 - 16)) or (1L shl (T__21 - 16)) or (1L shl (T__33 - 16)) or (1L shl (T__34 - 16)) or (1L shl (T__35 - 16)) or (1L shl (T__36 - 16)) or (1L shl (T__37 - 16)) or (1L shl (T__41 - 16)) or (1L shl (T__51 - 16)) or (1L shl (T__58 - 16)) or (1L shl (T__61 - 16)) or (1L shl (T__62 - 16)) or (1L shl (NAME - 16)) or (1L shl (NORMALSTRING - 16)) or (1L shl (CHARSTRING - 16)) or (1L shl (LONGSTRING - 16)) or (1L shl (INT - 16)) or (1L shl (HEX - 16)) or (1L shl (FLOAT - 16)) or (1L shl (HEX_FLOAT - 16)))) != 0L)) {
				if (true){
				this.state = 417
				explist()
				}
			}

			this.state = 420
			match(T__38) as Token
			}}
			T__41 , NAME  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 421
			tableconstructor()
			}}
			NORMALSTRING , CHARSTRING , LONGSTRING  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 422
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
		enterRule(_localctx, 50, Rules.RULE_functiondef.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 425
			match(T__15) as Token
			this.state = 426
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
		enterRule(_localctx, 52, Rules.RULE_lambdadef.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 428
			match(T__21) as Token
			this.state = 429
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
		enterRule(_localctx, 54, Rules.RULE_funcbody.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 431
			match(T__37) as Token
			this.state = 433
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__36 || _la==NAME) {
				if (true){
				this.state = 432
				parlist()
				}
			}

			this.state = 435
			match(T__38) as Token
			this.state = 436
			block()
			this.state = 437
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
		enterRule(_localctx, 56, Rules.RULE_parlist.id)
		var _la: Int
		try {
			this.state = 445
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			NAME  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 439
			namelist()
			this.state = 442
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__13) {
				if (true){
				this.state = 440
				match(T__13) as Token
				this.state = 441
				match(T__36) as Token
				}
			}

			}}
			T__36  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 444
			match(T__36) as Token
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
		enterRule(_localctx, 58, Rules.RULE_tableconstructor.id)
		var _la: Int
		try {
			this.state = 458
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__41  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 447
			match(T__41) as Token
			this.state = 449
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 16)) and 0x3f.inv()) == 0 && ((1L shl (_la - 16)) and ((1L shl (T__15 - 16)) or (1L shl (T__21 - 16)) or (1L shl (T__33 - 16)) or (1L shl (T__34 - 16)) or (1L shl (T__35 - 16)) or (1L shl (T__36 - 16)) or (1L shl (T__37 - 16)) or (1L shl (T__39 - 16)) or (1L shl (T__41 - 16)) or (1L shl (T__51 - 16)) or (1L shl (T__58 - 16)) or (1L shl (T__61 - 16)) or (1L shl (T__62 - 16)) or (1L shl (NAME - 16)) or (1L shl (NORMALSTRING - 16)) or (1L shl (CHARSTRING - 16)) or (1L shl (LONGSTRING - 16)) or (1L shl (INT - 16)) or (1L shl (HEX - 16)) or (1L shl (FLOAT - 16)) or (1L shl (HEX_FLOAT - 16)))) != 0L)) {
				if (true){
				this.state = 448
				fieldlist()
				}
			}

			this.state = 451
			match(T__42) as Token
			}}
			NAME  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 452
			match(NAME) as Token
			this.state = 453
			match(T__39) as Token
			this.state = 455
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 69)) and 0x3f.inv()) == 0 && ((1L shl (_la - 69)) and ((1L shl (INT - 69)) or (1L shl (HEX - 69)) or (1L shl (FLOAT - 69)) or (1L shl (HEX_FLOAT - 69)))) != 0L)) {
				if (true){
				this.state = 454
				number()
				}
			}

			this.state = 457
			match(T__40) as Token
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
		enterRule(_localctx, 60, Rules.RULE_fieldlist.id)
		var _la: Int
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 460
			field()
			this.state = 466
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,46,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 461
					fieldsep()
					this.state = 462
					field()
					}
					} 
				}
				this.state = 468
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,46,context)
			}
			this.state = 470
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__0 || _la==T__13) {
				if (true){
				this.state = 469
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
		enterRule(_localctx, 62, Rules.RULE_field.id)
		try {
			this.state = 482
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,48,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 472
			match(T__39) as Token
			this.state = 473
			exp(0)
			this.state = 474
			match(T__40) as Token
			this.state = 475
			match(T__1) as Token
			this.state = 476
			exp(0)
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 478
			match(NAME) as Token
			this.state = 479
			match(T__1) as Token
			this.state = 480
			exp(0)
			}}
			3 -> {
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 481
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
		enterRule(_localctx, 64, Rules.RULE_fieldsep.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 484
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
		enterRule(_localctx, 66, Rules.RULE_operatorOr.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 486
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
		enterRule(_localctx, 68, Rules.RULE_operatorAnd.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 488
			match(T__44) as Token
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
		enterRule(_localctx, 70, Rules.RULE_operatorComparison.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 490
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__27) or (1L shl T__28) or (1L shl T__45) or (1L shl T__46) or (1L shl T__47) or (1L shl T__48))) != 0L)) ) {
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
		enterRule(_localctx, 72, Rules.RULE_operatorStrcat.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 492
			match(T__49) as Token
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
		enterRule(_localctx, 74, Rules.RULE_operatorAddSub.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 494
			_la = _input!!.LA(1)
			if ( !(_la==T__50 || _la==T__51) ) {
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
		enterRule(_localctx, 76, Rules.RULE_operatorMulDivMod.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 496
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__52) or (1L shl T__53) or (1L shl T__54) or (1L shl T__55))) != 0L)) ) {
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
		enterRule(_localctx, 78, Rules.RULE_operatorBitwise.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 498
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__56) or (1L shl T__57) or (1L shl T__58) or (1L shl T__59) or (1L shl T__60))) != 0L)) ) {
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
		enterRule(_localctx, 80, Rules.RULE_operatorUnary.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 500
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__51) or (1L shl T__58) or (1L shl T__61) or (1L shl T__62))) != 0L)) ) {
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
		enterRule(_localctx, 82, Rules.RULE_operatorPower.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 502
			match(T__63) as Token
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
		enterRule(_localctx, 84, Rules.RULE_number.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 504
			_la = _input!!.LA(1)
			if ( !(((((_la - 69)) and 0x3f.inv()) == 0 && ((1L shl (_la - 69)) and ((1L shl (INT - 69)) or (1L shl (HEX - 69)) or (1L shl (FLOAT - 69)) or (1L shl (HEX_FLOAT - 69)))) != 0L)) ) {
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
		enterRule(_localctx, 86, Rules.RULE_string.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 506
			_la = _input!!.LA(1)
			if ( !(((((_la - 66)) and 0x3f.inv()) == 0 && ((1L shl (_la - 66)) and ((1L shl (NORMALSTRING - 66)) or (1L shl (CHARSTRING - 66)) or (1L shl (LONGSTRING - 66)))) != 0L)) ) {
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
		17 -> return exp_sempred(_localctx as ExpContext, predIndex)
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