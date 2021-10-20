// Generated from java-escape by ANTLR 4.7.1
package com.dingyi.lsp.lua.common.parser

import org.antlr.v4.kotlinruntime.tree.ParseTreeListener

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LuaParser}.
 */
interface LuaListener : ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LuaParser#chunk}.
	 * @param ctx the parse tree
	 */
	fun enterChunk(ctx: LuaParser.ChunkContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#chunk}.
	 * @param ctx the parse tree
	 */
	fun exitChunk(ctx: LuaParser.ChunkContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#block}.
	 * @param ctx the parse tree
	 */
	fun enterBlock(ctx: LuaParser.BlockContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#block}.
	 * @param ctx the parse tree
	 */
	fun exitBlock(ctx: LuaParser.BlockContext)
	/**
	 * Enter a parse tree produced by the {@code nil}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterNil(ctx: LuaParser.NilContext)
	/**
	 * Exit a parse tree produced by the {@code nil}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitNil(ctx: LuaParser.NilContext)
	/**
	 * Enter a parse tree produced by the {@code varListStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterVarListStat(ctx: LuaParser.VarListStatContext)
	/**
	 * Exit a parse tree produced by the {@code varListStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitVarListStat(ctx: LuaParser.VarListStatContext)
	/**
	 * Enter a parse tree produced by the {@code functionCallStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterFunctionCallStat(ctx: LuaParser.FunctionCallStatContext)
	/**
	 * Exit a parse tree produced by the {@code functionCallStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitFunctionCallStat(ctx: LuaParser.FunctionCallStatContext)
	/**
	 * Enter a parse tree produced by the {@code labelStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterLabelStat(ctx: LuaParser.LabelStatContext)
	/**
	 * Exit a parse tree produced by the {@code labelStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitLabelStat(ctx: LuaParser.LabelStatContext)
	/**
	 * Enter a parse tree produced by the {@code breakStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterBreakStat(ctx: LuaParser.BreakStatContext)
	/**
	 * Exit a parse tree produced by the {@code breakStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitBreakStat(ctx: LuaParser.BreakStatContext)
	/**
	 * Enter a parse tree produced by the {@code continueStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterContinueStat(ctx: LuaParser.ContinueStatContext)
	/**
	 * Exit a parse tree produced by the {@code continueStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitContinueStat(ctx: LuaParser.ContinueStatContext)
	/**
	 * Enter a parse tree produced by the {@code gotoStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterGotoStat(ctx: LuaParser.GotoStatContext)
	/**
	 * Exit a parse tree produced by the {@code gotoStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitGotoStat(ctx: LuaParser.GotoStatContext)
	/**
	 * Enter a parse tree produced by the {@code doStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterDoStat(ctx: LuaParser.DoStatContext)
	/**
	 * Exit a parse tree produced by the {@code doStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitDoStat(ctx: LuaParser.DoStatContext)
	/**
	 * Enter a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterWhileStat(ctx: LuaParser.WhileStatContext)
	/**
	 * Exit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitWhileStat(ctx: LuaParser.WhileStatContext)
	/**
	 * Enter a parse tree produced by the {@code repeatStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterRepeatStat(ctx: LuaParser.RepeatStatContext)
	/**
	 * Exit a parse tree produced by the {@code repeatStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitRepeatStat(ctx: LuaParser.RepeatStatContext)
	/**
	 * Enter a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterIfStat(ctx: LuaParser.IfStatContext)
	/**
	 * Exit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitIfStat(ctx: LuaParser.IfStatContext)
	/**
	 * Enter a parse tree produced by the {@code forStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterForStat(ctx: LuaParser.ForStatContext)
	/**
	 * Exit a parse tree produced by the {@code forStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitForStat(ctx: LuaParser.ForStatContext)
	/**
	 * Enter a parse tree produced by the {@code forInStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterForInStat(ctx: LuaParser.ForInStatContext)
	/**
	 * Exit a parse tree produced by the {@code forInStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitForInStat(ctx: LuaParser.ForInStatContext)
	/**
	 * Enter a parse tree produced by the {@code functionDefStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterFunctionDefStat(ctx: LuaParser.FunctionDefStatContext)
	/**
	 * Exit a parse tree produced by the {@code functionDefStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitFunctionDefStat(ctx: LuaParser.FunctionDefStatContext)
	/**
	 * Enter a parse tree produced by the {@code localFunctionDefStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterLocalFunctionDefStat(ctx: LuaParser.LocalFunctionDefStatContext)
	/**
	 * Exit a parse tree produced by the {@code localFunctionDefStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitLocalFunctionDefStat(ctx: LuaParser.LocalFunctionDefStatContext)
	/**
	 * Enter a parse tree produced by the {@code localVarListStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterLocalVarListStat(ctx: LuaParser.LocalVarListStatContext)
	/**
	 * Exit a parse tree produced by the {@code localVarListStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitLocalVarListStat(ctx: LuaParser.LocalVarListStatContext)
	/**
	 * Enter a parse tree produced by the {@code switchStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterSwitchStat(ctx: LuaParser.SwitchStatContext)
	/**
	 * Exit a parse tree produced by the {@code switchStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitSwitchStat(ctx: LuaParser.SwitchStatContext)
	/**
	 * Enter a parse tree produced by the {@code whenStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterWhenStat(ctx: LuaParser.WhenStatContext)
	/**
	 * Exit a parse tree produced by the {@code whenStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitWhenStat(ctx: LuaParser.WhenStatContext)
	/**
	 * Enter a parse tree produced by the {@code lambdaStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterLambdaStat(ctx: LuaParser.LambdaStatContext)
	/**
	 * Exit a parse tree produced by the {@code lambdaStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitLambdaStat(ctx: LuaParser.LambdaStatContext)
	/**
	 * Enter a parse tree produced by the {@code deferStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterDeferStat(ctx: LuaParser.DeferStatContext)
	/**
	 * Exit a parse tree produced by the {@code deferStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitDeferStat(ctx: LuaParser.DeferStatContext)
	/**
	 * Enter a parse tree produced by the {@code returnStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterReturnStat(ctx: LuaParser.ReturnStatContext)
	/**
	 * Exit a parse tree produced by the {@code returnStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitReturnStat(ctx: LuaParser.ReturnStatContext)
	/**
	 * Enter a parse tree produced by the {@code commentStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun enterCommentStat(ctx: LuaParser.CommentStatContext)
	/**
	 * Exit a parse tree produced by the {@code commentStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	fun exitCommentStat(ctx: LuaParser.CommentStatContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#comment}.
	 * @param ctx the parse tree
	 */
	fun enterComment(ctx: LuaParser.CommentContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#comment}.
	 * @param ctx the parse tree
	 */
	fun exitComment(ctx: LuaParser.CommentContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#ifbody}.
	 * @param ctx the parse tree
	 */
	fun enterIfbody(ctx: LuaParser.IfbodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#ifbody}.
	 * @param ctx the parse tree
	 */
	fun exitIfbody(ctx: LuaParser.IfbodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#elseifbody}.
	 * @param ctx the parse tree
	 */
	fun enterElseifbody(ctx: LuaParser.ElseifbodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#elseifbody}.
	 * @param ctx the parse tree
	 */
	fun exitElseifbody(ctx: LuaParser.ElseifbodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#elsebody}.
	 * @param ctx the parse tree
	 */
	fun enterElsebody(ctx: LuaParser.ElsebodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#elsebody}.
	 * @param ctx the parse tree
	 */
	fun exitElsebody(ctx: LuaParser.ElsebodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#defaultbody}.
	 * @param ctx the parse tree
	 */
	fun enterDefaultbody(ctx: LuaParser.DefaultbodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#defaultbody}.
	 * @param ctx the parse tree
	 */
	fun exitDefaultbody(ctx: LuaParser.DefaultbodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#casebody}.
	 * @param ctx the parse tree
	 */
	fun enterCasebody(ctx: LuaParser.CasebodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#casebody}.
	 * @param ctx the parse tree
	 */
	fun exitCasebody(ctx: LuaParser.CasebodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#lambdabody}.
	 * @param ctx the parse tree
	 */
	fun enterLambdabody(ctx: LuaParser.LambdabodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#lambdabody}.
	 * @param ctx the parse tree
	 */
	fun exitLambdabody(ctx: LuaParser.LambdabodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#attnamelist}.
	 * @param ctx the parse tree
	 */
	fun enterAttnamelist(ctx: LuaParser.AttnamelistContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#attnamelist}.
	 * @param ctx the parse tree
	 */
	fun exitAttnamelist(ctx: LuaParser.AttnamelistContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#attrib}.
	 * @param ctx the parse tree
	 */
	fun enterAttrib(ctx: LuaParser.AttribContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#attrib}.
	 * @param ctx the parse tree
	 */
	fun exitAttrib(ctx: LuaParser.AttribContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#retstat}.
	 * @param ctx the parse tree
	 */
	fun enterRetstat(ctx: LuaParser.RetstatContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#retstat}.
	 * @param ctx the parse tree
	 */
	fun exitRetstat(ctx: LuaParser.RetstatContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#label}.
	 * @param ctx the parse tree
	 */
	fun enterLabel(ctx: LuaParser.LabelContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#label}.
	 * @param ctx the parse tree
	 */
	fun exitLabel(ctx: LuaParser.LabelContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#funcname}.
	 * @param ctx the parse tree
	 */
	fun enterFuncname(ctx: LuaParser.FuncnameContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#funcname}.
	 * @param ctx the parse tree
	 */
	fun exitFuncname(ctx: LuaParser.FuncnameContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#funcname_self}.
	 * @param ctx the parse tree
	 */
	fun enterFuncname_self(ctx: LuaParser.Funcname_selfContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#funcname_self}.
	 * @param ctx the parse tree
	 */
	fun exitFuncname_self(ctx: LuaParser.Funcname_selfContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#varlist}.
	 * @param ctx the parse tree
	 */
	fun enterVarlist(ctx: LuaParser.VarlistContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#varlist}.
	 * @param ctx the parse tree
	 */
	fun exitVarlist(ctx: LuaParser.VarlistContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#namelist}.
	 * @param ctx the parse tree
	 */
	fun enterNamelist(ctx: LuaParser.NamelistContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#namelist}.
	 * @param ctx the parse tree
	 */
	fun exitNamelist(ctx: LuaParser.NamelistContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#explist}.
	 * @param ctx the parse tree
	 */
	fun enterExplist(ctx: LuaParser.ExplistContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#explist}.
	 * @param ctx the parse tree
	 */
	fun exitExplist(ctx: LuaParser.ExplistContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	fun enterExp(ctx: LuaParser.ExpContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	fun exitExp(ctx: LuaParser.ExpContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#prefixexp}.
	 * @param ctx the parse tree
	 */
	fun enterPrefixexp(ctx: LuaParser.PrefixexpContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#prefixexp}.
	 * @param ctx the parse tree
	 */
	fun exitPrefixexp(ctx: LuaParser.PrefixexpContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#functioncall}.
	 * @param ctx the parse tree
	 */
	fun enterFunctioncall(ctx: LuaParser.FunctioncallContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#functioncall}.
	 * @param ctx the parse tree
	 */
	fun exitFunctioncall(ctx: LuaParser.FunctioncallContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#varOrExp}.
	 * @param ctx the parse tree
	 */
	fun enterVarOrExp(ctx: LuaParser.VarOrExpContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#varOrExp}.
	 * @param ctx the parse tree
	 */
	fun exitVarOrExp(ctx: LuaParser.VarOrExpContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#lvar}.
	 * @param ctx the parse tree
	 */
	fun enterLvar(ctx: LuaParser.LvarContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#lvar}.
	 * @param ctx the parse tree
	 */
	fun exitLvar(ctx: LuaParser.LvarContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#varSuffix}.
	 * @param ctx the parse tree
	 */
	fun enterVarSuffix(ctx: LuaParser.VarSuffixContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#varSuffix}.
	 * @param ctx the parse tree
	 */
	fun exitVarSuffix(ctx: LuaParser.VarSuffixContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#nameAndArgs}.
	 * @param ctx the parse tree
	 */
	fun enterNameAndArgs(ctx: LuaParser.NameAndArgsContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#nameAndArgs}.
	 * @param ctx the parse tree
	 */
	fun exitNameAndArgs(ctx: LuaParser.NameAndArgsContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#args}.
	 * @param ctx the parse tree
	 */
	fun enterArgs(ctx: LuaParser.ArgsContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#args}.
	 * @param ctx the parse tree
	 */
	fun exitArgs(ctx: LuaParser.ArgsContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#functiondef}.
	 * @param ctx the parse tree
	 */
	fun enterFunctiondef(ctx: LuaParser.FunctiondefContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#functiondef}.
	 * @param ctx the parse tree
	 */
	fun exitFunctiondef(ctx: LuaParser.FunctiondefContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#lambdadef}.
	 * @param ctx the parse tree
	 */
	fun enterLambdadef(ctx: LuaParser.LambdadefContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#lambdadef}.
	 * @param ctx the parse tree
	 */
	fun exitLambdadef(ctx: LuaParser.LambdadefContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#funcbody}.
	 * @param ctx the parse tree
	 */
	fun enterFuncbody(ctx: LuaParser.FuncbodyContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#funcbody}.
	 * @param ctx the parse tree
	 */
	fun exitFuncbody(ctx: LuaParser.FuncbodyContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#parlist}.
	 * @param ctx the parse tree
	 */
	fun enterParlist(ctx: LuaParser.ParlistContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#parlist}.
	 * @param ctx the parse tree
	 */
	fun exitParlist(ctx: LuaParser.ParlistContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#tableconstructor}.
	 * @param ctx the parse tree
	 */
	fun enterTableconstructor(ctx: LuaParser.TableconstructorContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#tableconstructor}.
	 * @param ctx the parse tree
	 */
	fun exitTableconstructor(ctx: LuaParser.TableconstructorContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#fieldlist}.
	 * @param ctx the parse tree
	 */
	fun enterFieldlist(ctx: LuaParser.FieldlistContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#fieldlist}.
	 * @param ctx the parse tree
	 */
	fun exitFieldlist(ctx: LuaParser.FieldlistContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#field}.
	 * @param ctx the parse tree
	 */
	fun enterField(ctx: LuaParser.FieldContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#field}.
	 * @param ctx the parse tree
	 */
	fun exitField(ctx: LuaParser.FieldContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#fieldsep}.
	 * @param ctx the parse tree
	 */
	fun enterFieldsep(ctx: LuaParser.FieldsepContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#fieldsep}.
	 * @param ctx the parse tree
	 */
	fun exitFieldsep(ctx: LuaParser.FieldsepContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorOr}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorOr(ctx: LuaParser.OperatorOrContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorOr}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorOr(ctx: LuaParser.OperatorOrContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorAnd}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorAnd(ctx: LuaParser.OperatorAndContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorAnd}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorAnd(ctx: LuaParser.OperatorAndContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorComparison}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorComparison(ctx: LuaParser.OperatorComparisonContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorComparison}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorComparison(ctx: LuaParser.OperatorComparisonContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorStrcat}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorStrcat(ctx: LuaParser.OperatorStrcatContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorStrcat}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorStrcat(ctx: LuaParser.OperatorStrcatContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorAddSub}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorAddSub(ctx: LuaParser.OperatorAddSubContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorAddSub}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorAddSub(ctx: LuaParser.OperatorAddSubContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorMulDivMod(ctx: LuaParser.OperatorMulDivModContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorMulDivMod(ctx: LuaParser.OperatorMulDivModContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorBitwise}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorBitwise(ctx: LuaParser.OperatorBitwiseContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorBitwise}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorBitwise(ctx: LuaParser.OperatorBitwiseContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorUnary}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorUnary(ctx: LuaParser.OperatorUnaryContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorUnary}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorUnary(ctx: LuaParser.OperatorUnaryContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorPower}.
	 * @param ctx the parse tree
	 */
	fun enterOperatorPower(ctx: LuaParser.OperatorPowerContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorPower}.
	 * @param ctx the parse tree
	 */
	fun exitOperatorPower(ctx: LuaParser.OperatorPowerContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#number}.
	 * @param ctx the parse tree
	 */
	fun enterNumber(ctx: LuaParser.NumberContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#number}.
	 * @param ctx the parse tree
	 */
	fun exitNumber(ctx: LuaParser.NumberContext)
	/**
	 * Enter a parse tree produced by {@link LuaParser#string}.
	 * @param ctx the parse tree
	 */
	fun enterString(ctx: LuaParser.StringContext)
	/**
	 * Exit a parse tree produced by {@link LuaParser#string}.
	 * @param ctx the parse tree
	 */
	fun exitString(ctx: LuaParser.StringContext)
}