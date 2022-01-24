// Generated from java-escape by ANTLR 4.7.1
package com.dingyi.lsp.lua.common.parser

import org.antlr.v4.kotlinruntime.ParserRuleContext
import org.antlr.v4.kotlinruntime.tree.ErrorNode
import org.antlr.v4.kotlinruntime.tree.TerminalNode

/**
 * This class provides an empty implementation of {@link LuaListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
open class LuaBaseListener : LuaListener {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterChunk(ctx: LuaParser.ChunkContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitChunk(ctx: LuaParser.ChunkContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterBlock(ctx: LuaParser.BlockContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitBlock(ctx: LuaParser.BlockContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterNil(ctx: LuaParser.NilContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitNil(ctx: LuaParser.NilContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterVarListStat(ctx: LuaParser.VarListStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitVarListStat(ctx: LuaParser.VarListStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFunctionCallStat(ctx: LuaParser.FunctionCallStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFunctionCallStat(ctx: LuaParser.FunctionCallStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLabelStat(ctx: LuaParser.LabelStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLabelStat(ctx: LuaParser.LabelStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterBreakStat(ctx: LuaParser.BreakStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitBreakStat(ctx: LuaParser.BreakStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterContinueStat(ctx: LuaParser.ContinueStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitContinueStat(ctx: LuaParser.ContinueStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterGotoStat(ctx: LuaParser.GotoStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitGotoStat(ctx: LuaParser.GotoStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterDoStat(ctx: LuaParser.DoStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitDoStat(ctx: LuaParser.DoStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterWhileStat(ctx: LuaParser.WhileStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitWhileStat(ctx: LuaParser.WhileStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterRepeatStat(ctx: LuaParser.RepeatStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitRepeatStat(ctx: LuaParser.RepeatStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterIfStat(ctx: LuaParser.IfStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitIfStat(ctx: LuaParser.IfStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterForStat(ctx: LuaParser.ForStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitForStat(ctx: LuaParser.ForStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterForInStat(ctx: LuaParser.ForInStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitForInStat(ctx: LuaParser.ForInStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFunctionDefStat(ctx: LuaParser.FunctionDefStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFunctionDefStat(ctx: LuaParser.FunctionDefStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLocalFunctionDefStat(ctx: LuaParser.LocalFunctionDefStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLocalFunctionDefStat(ctx: LuaParser.LocalFunctionDefStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLocalVarListStat(ctx: LuaParser.LocalVarListStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLocalVarListStat(ctx: LuaParser.LocalVarListStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterSwitchStat(ctx: LuaParser.SwitchStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitSwitchStat(ctx: LuaParser.SwitchStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterWhenStat(ctx: LuaParser.WhenStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitWhenStat(ctx: LuaParser.WhenStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLambdaStat(ctx: LuaParser.LambdaStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLambdaStat(ctx: LuaParser.LambdaStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterDeferStat(ctx: LuaParser.DeferStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitDeferStat(ctx: LuaParser.DeferStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterReturnStat(ctx: LuaParser.ReturnStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitReturnStat(ctx: LuaParser.ReturnStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterCommentStat(ctx: LuaParser.CommentStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitCommentStat(ctx: LuaParser.CommentStatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterComment(ctx: LuaParser.CommentContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitComment(ctx: LuaParser.CommentContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterIfbody(ctx: LuaParser.IfbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitIfbody(ctx: LuaParser.IfbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterElseifbody(ctx: LuaParser.ElseifbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitElseifbody(ctx: LuaParser.ElseifbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterElsebody(ctx: LuaParser.ElsebodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitElsebody(ctx: LuaParser.ElsebodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterDefaultbody(ctx: LuaParser.DefaultbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitDefaultbody(ctx: LuaParser.DefaultbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterCasebody(ctx: LuaParser.CasebodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitCasebody(ctx: LuaParser.CasebodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLambdabody(ctx: LuaParser.LambdabodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLambdabody(ctx: LuaParser.LambdabodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterAttnamelist(ctx: LuaParser.AttnamelistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitAttnamelist(ctx: LuaParser.AttnamelistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterAttrib(ctx: LuaParser.AttribContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitAttrib(ctx: LuaParser.AttribContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterRetstat(ctx: LuaParser.RetstatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitRetstat(ctx: LuaParser.RetstatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLabel(ctx: LuaParser.LabelContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLabel(ctx: LuaParser.LabelContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFuncname(ctx: LuaParser.FuncnameContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFuncname(ctx: LuaParser.FuncnameContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFuncname_self(ctx: LuaParser.Funcname_selfContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFuncname_self(ctx: LuaParser.Funcname_selfContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterVarlist(ctx: LuaParser.VarlistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitVarlist(ctx: LuaParser.VarlistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterNamelist(ctx: LuaParser.NamelistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitNamelist(ctx: LuaParser.NamelistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterExplist(ctx: LuaParser.ExplistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitExplist(ctx: LuaParser.ExplistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterExp(ctx: LuaParser.ExpContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitExp(ctx: LuaParser.ExpContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterPrefixexp(ctx: LuaParser.PrefixexpContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitPrefixexp(ctx: LuaParser.PrefixexpContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFunctioncall(ctx: LuaParser.FunctioncallContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFunctioncall(ctx: LuaParser.FunctioncallContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterVarOrExp(ctx: LuaParser.VarOrExpContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitVarOrExp(ctx: LuaParser.VarOrExpContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLvar(ctx: LuaParser.LvarContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLvar(ctx: LuaParser.LvarContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterVarSuffix(ctx: LuaParser.VarSuffixContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitVarSuffix(ctx: LuaParser.VarSuffixContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterNameAndArgs(ctx: LuaParser.NameAndArgsContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitNameAndArgs(ctx: LuaParser.NameAndArgsContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterArgs(ctx: LuaParser.ArgsContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitArgs(ctx: LuaParser.ArgsContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFunctiondef(ctx: LuaParser.FunctiondefContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFunctiondef(ctx: LuaParser.FunctiondefContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterLambdadef(ctx: LuaParser.LambdadefContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitLambdadef(ctx: LuaParser.LambdadefContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFuncbody(ctx: LuaParser.FuncbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFuncbody(ctx: LuaParser.FuncbodyContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterParlist(ctx: LuaParser.ParlistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitParlist(ctx: LuaParser.ParlistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterTableconstructor(ctx: LuaParser.TableconstructorContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitTableconstructor(ctx: LuaParser.TableconstructorContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFieldlist(ctx: LuaParser.FieldlistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFieldlist(ctx: LuaParser.FieldlistContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterField(ctx: LuaParser.FieldContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitField(ctx: LuaParser.FieldContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterFieldsep(ctx: LuaParser.FieldsepContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitFieldsep(ctx: LuaParser.FieldsepContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorOr(ctx: LuaParser.OperatorOrContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorOr(ctx: LuaParser.OperatorOrContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorAnd(ctx: LuaParser.OperatorAndContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorAnd(ctx: LuaParser.OperatorAndContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorComparison(ctx: LuaParser.OperatorComparisonContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorComparison(ctx: LuaParser.OperatorComparisonContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorStrcat(ctx: LuaParser.OperatorStrcatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorStrcat(ctx: LuaParser.OperatorStrcatContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorAddSub(ctx: LuaParser.OperatorAddSubContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorAddSub(ctx: LuaParser.OperatorAddSubContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorMulDivMod(ctx: LuaParser.OperatorMulDivModContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorMulDivMod(ctx: LuaParser.OperatorMulDivModContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorBitwise(ctx: LuaParser.OperatorBitwiseContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorBitwise(ctx: LuaParser.OperatorBitwiseContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorUnary(ctx: LuaParser.OperatorUnaryContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorUnary(ctx: LuaParser.OperatorUnaryContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterOperatorPower(ctx: LuaParser.OperatorPowerContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitOperatorPower(ctx: LuaParser.OperatorPowerContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterNumber(ctx: LuaParser.NumberContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitNumber(ctx: LuaParser.NumberContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterString(ctx: LuaParser.StringContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitString(ctx: LuaParser.StringContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun enterEveryRule(ctx: ParserRuleContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun exitEveryRule(ctx: ParserRuleContext) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun visitTerminal(node: TerminalNode) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    override fun visitErrorNode(node: ErrorNode) {}
}