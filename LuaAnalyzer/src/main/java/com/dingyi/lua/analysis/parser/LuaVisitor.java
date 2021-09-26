// Generated from D:/android studio project/MyLuaApp2/LuaAnalyzer/src/main/java/com/dingyi/lua/analysis/parser\Lua.g4 by ANTLR 4.9.1
package com.dingyi.lua.analysis.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LuaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LuaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LuaParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChunk(LuaParser.ChunkContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(LuaParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code defaultStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultStat(LuaParser.DefaultStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varListStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarListStat(LuaParser.VarListStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCallStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallStat(LuaParser.FunctionCallStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code labelStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelStat(LuaParser.LabelStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStat(LuaParser.BreakStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStat(LuaParser.ContinueStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gotoStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGotoStat(LuaParser.GotoStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStat(LuaParser.DoStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(LuaParser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code repeatStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatStat(LuaParser.RepeatStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(LuaParser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStat(LuaParser.ForStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forInStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInStat(LuaParser.ForInStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionDefStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefStat(LuaParser.FunctionDefStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localFunctionDefStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalFunctionDefStat(LuaParser.LocalFunctionDefStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localVarListStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVarListStat(LuaParser.LocalVarListStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code switchStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStat(LuaParser.SwitchStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whenStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenStat(LuaParser.WhenStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaStat(LuaParser.LambdaStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code deferStat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeferStat(LuaParser.DeferStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comment}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(LuaParser.CommentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lineComment}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineComment(LuaParser.LineCommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#ifbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfbody(LuaParser.IfbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#elseifbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseifbody(LuaParser.ElseifbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#elsebody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsebody(LuaParser.ElsebodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#defaultbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultbody(LuaParser.DefaultbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#casebody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCasebody(LuaParser.CasebodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#lambdabody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdabody(LuaParser.LambdabodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#attnamelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttnamelist(LuaParser.AttnamelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#attrib}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrib(LuaParser.AttribContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#retstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetstat(LuaParser.RetstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(LuaParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#funcname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncname(LuaParser.FuncnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#varlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarlist(LuaParser.VarlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#namelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamelist(LuaParser.NamelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#explist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplist(LuaParser.ExplistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(LuaParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#prefixexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexp(LuaParser.PrefixexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#functioncall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctioncall(LuaParser.FunctioncallContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#varOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrExp(LuaParser.VarOrExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(LuaParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#varSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarSuffix(LuaParser.VarSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#nameAndArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameAndArgs(LuaParser.NameAndArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(LuaParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#functiondef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondef(LuaParser.FunctiondefContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#lambdadef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdadef(LuaParser.LambdadefContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#funcbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncbody(LuaParser.FuncbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#parlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParlist(LuaParser.ParlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#tableconstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableconstructor(LuaParser.TableconstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#fieldlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldlist(LuaParser.FieldlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(LuaParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#fieldsep}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldsep(LuaParser.FieldsepContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorOr(LuaParser.OperatorOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAnd(LuaParser.OperatorAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorComparison(LuaParser.OperatorComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorStrcat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorStrcat(LuaParser.OperatorStrcatContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAddSub(LuaParser.OperatorAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorMulDivMod(LuaParser.OperatorMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorBitwise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorBitwise(LuaParser.OperatorBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorUnary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorUnary(LuaParser.OperatorUnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorPower}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorPower(LuaParser.OperatorPowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(LuaParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(LuaParser.StringContext ctx);
}