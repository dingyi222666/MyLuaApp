// Generated from java-escape by ANTLR 4.7.1
package com.dingyi.lsp.lua.common.parser

import org.antlr.v4.kotlinruntime.tree.ParseTreeVisitor

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LuaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
interface LuaVisitor<T> : ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link LuaParser#chunk}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitChunk(ctx: LuaParser.ChunkContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#block}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBlock(ctx: LuaParser.BlockContext): T

    /**
     * Visit a parse tree produced by the {@code nil}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNil(ctx: LuaParser.NilContext): T

    /**
     * Visit a parse tree produced by the {@code varListStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarListStat(ctx: LuaParser.VarListStatContext): T

    /**
     * Visit a parse tree produced by the {@code functionCallStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctionCallStat(ctx: LuaParser.FunctionCallStatContext): T

    /**
     * Visit a parse tree produced by the {@code labelStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLabelStat(ctx: LuaParser.LabelStatContext): T

    /**
     * Visit a parse tree produced by the {@code breakStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBreakStat(ctx: LuaParser.BreakStatContext): T

    /**
     * Visit a parse tree produced by the {@code continueStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitContinueStat(ctx: LuaParser.ContinueStatContext): T

    /**
     * Visit a parse tree produced by the {@code gotoStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitGotoStat(ctx: LuaParser.GotoStatContext): T

    /**
     * Visit a parse tree produced by the {@code doStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDoStat(ctx: LuaParser.DoStatContext): T

    /**
     * Visit a parse tree produced by the {@code whileStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitWhileStat(ctx: LuaParser.WhileStatContext): T

    /**
     * Visit a parse tree produced by the {@code repeatStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitRepeatStat(ctx: LuaParser.RepeatStatContext): T

    /**
     * Visit a parse tree produced by the {@code ifStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIfStat(ctx: LuaParser.IfStatContext): T

    /**
     * Visit a parse tree produced by the {@code forStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitForStat(ctx: LuaParser.ForStatContext): T

    /**
     * Visit a parse tree produced by the {@code forInStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitForInStat(ctx: LuaParser.ForInStatContext): T

    /**
     * Visit a parse tree produced by the {@code functionDefStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctionDefStat(ctx: LuaParser.FunctionDefStatContext): T

    /**
     * Visit a parse tree produced by the {@code localFunctionDefStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLocalFunctionDefStat(ctx: LuaParser.LocalFunctionDefStatContext): T

    /**
     * Visit a parse tree produced by the {@code localVarListStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLocalVarListStat(ctx: LuaParser.LocalVarListStatContext): T

    /**
     * Visit a parse tree produced by the {@code switchStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitSwitchStat(ctx: LuaParser.SwitchStatContext): T

    /**
     * Visit a parse tree produced by the {@code whenStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitWhenStat(ctx: LuaParser.WhenStatContext): T

    /**
     * Visit a parse tree produced by the {@code lambdaStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLambdaStat(ctx: LuaParser.LambdaStatContext): T

    /**
     * Visit a parse tree produced by the {@code deferStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDeferStat(ctx: LuaParser.DeferStatContext): T

    /**
     * Visit a parse tree produced by the {@code returnStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitReturnStat(ctx: LuaParser.ReturnStatContext): T

    /**
     * Visit a parse tree produced by the {@code commentStat}
     * labeled alternative in {@link LuaParser#stat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCommentStat(ctx: LuaParser.CommentStatContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#comment}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitComment(ctx: LuaParser.CommentContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#ifbody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIfbody(ctx: LuaParser.IfbodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#elseifbody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitElseifbody(ctx: LuaParser.ElseifbodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#elsebody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitElsebody(ctx: LuaParser.ElsebodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#defaultbody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDefaultbody(ctx: LuaParser.DefaultbodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#casebody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCasebody(ctx: LuaParser.CasebodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#lambdabody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLambdabody(ctx: LuaParser.LambdabodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#attnamelist}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAttnamelist(ctx: LuaParser.AttnamelistContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#attrib}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAttrib(ctx: LuaParser.AttribContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#retstat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitRetstat(ctx: LuaParser.RetstatContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#label}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLabel(ctx: LuaParser.LabelContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#funcname}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFuncname(ctx: LuaParser.FuncnameContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#funcname_self}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFuncname_self(ctx: LuaParser.Funcname_selfContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#varlist}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarlist(ctx: LuaParser.VarlistContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#namelist}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNamelist(ctx: LuaParser.NamelistContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#explist}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitExplist(ctx: LuaParser.ExplistContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#exp}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitExp(ctx: LuaParser.ExpContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#prefixexp}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitPrefixexp(ctx: LuaParser.PrefixexpContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#functioncall}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctioncall(ctx: LuaParser.FunctioncallContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#varOrExp}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarOrExp(ctx: LuaParser.VarOrExpContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#lvar}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLvar(ctx: LuaParser.LvarContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#varSuffix}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarSuffix(ctx: LuaParser.VarSuffixContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#nameAndArgs}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNameAndArgs(ctx: LuaParser.NameAndArgsContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#args}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitArgs(ctx: LuaParser.ArgsContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#functiondef}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunctiondef(ctx: LuaParser.FunctiondefContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#lambdadef}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLambdadef(ctx: LuaParser.LambdadefContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#funcbody}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFuncbody(ctx: LuaParser.FuncbodyContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#parlist}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitParlist(ctx: LuaParser.ParlistContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#tableconstructor}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitTableconstructor(ctx: LuaParser.TableconstructorContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#fieldlist}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFieldlist(ctx: LuaParser.FieldlistContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#field}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitField(ctx: LuaParser.FieldContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#fieldsep}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFieldsep(ctx: LuaParser.FieldsepContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorOr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorOr(ctx: LuaParser.OperatorOrContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorAnd}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorAnd(ctx: LuaParser.OperatorAndContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorComparison}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorComparison(ctx: LuaParser.OperatorComparisonContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorStrcat}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorStrcat(ctx: LuaParser.OperatorStrcatContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorAddSub}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorAddSub(ctx: LuaParser.OperatorAddSubContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorMulDivMod}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorMulDivMod(ctx: LuaParser.OperatorMulDivModContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorBitwise}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorBitwise(ctx: LuaParser.OperatorBitwiseContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorUnary}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorUnary(ctx: LuaParser.OperatorUnaryContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#operatorPower}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitOperatorPower(ctx: LuaParser.OperatorPowerContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#number}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNumber(ctx: LuaParser.NumberContext): T

    /**
     * Visit a parse tree produced by {@link LuaParser#string}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitString(ctx: LuaParser.StringContext): T
}