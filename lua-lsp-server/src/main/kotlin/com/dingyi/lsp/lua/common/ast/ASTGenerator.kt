package com.dingyi.lsp.lua.common.ast

import com.dingyi.lsp.lua.common.parser.LuaBaseVisitor
import com.dingyi.lsp.lua.common.parser.LuaLexer
import com.dingyi.lsp.lua.common.parser.LuaParser
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import org.antlr.v4.kotlinruntime.ParserRuleContext
import kotlin.properties.Delegates


/**
 * @author: dingyi
 * @date: 2021/10/7 10:50
 * @description: convert cst to ast
 **/
class ASTGenerator() {


    fun generate(inputCode: String): ChunkNode {
        val targetLexer = LuaLexer(CharStreams.fromString(inputCode))
        val targetParser = LuaParser(CommonTokenStream(targetLexer))
        return CSTVisitor().visitChunk(targetParser.chunk())
    }

    class CSTVisitor : LuaBaseVisitor<ASTNode>() {


        private fun getStringContent(text: String): String? {
            //char or default
            return if (text[0] == '"' || text[0] == '\'') {
                text.substring(1, text.length - 1)
            } else { //[[
                var startLength = 1
                var nowChar: Char
                while (true) {
                    nowChar = text[startLength + 1]
                    if (nowChar == '=' || nowChar == '[') {
                        startLength++
                    } else {
                        break
                    }
                }
                text.substring(startLength + 1, text.length - startLength - 1)
            }
        }

        private fun getStringContent(string: LuaParser.StringContext): String {
            return when {
                string.CHARSTRING() != null -> {
                    string.CHARSTRING()?.text?.let { getStringContent(it) }
                }
                string.LONGSTRING() != null -> {
                    string.LONGSTRING()?.text?.let { getStringContent(it) }
                }
                string.NORMALSTRING() != null -> {
                    string.NORMALSTRING()?.text?.let { getStringContent(it) }
                }
                else -> ""
            } ?: ""
        }

        override fun visitChunk(ctx: LuaParser.ChunkContext): ChunkNode {
            val chunkNode = ChunkNode()
            ctx.findBlock()?.let {
                chunkNode.body = visitBlock(it)
            }
            return chunkNode
        }

        private fun visitStat(ctx: LuaParser.StatContext): StatementNode {
            when (ctx) {
                is LuaParser.LocalVarListStatContext -> {
                    return visitLocalVarListStat(ctx)
                }
                is LuaParser.DoStatContext -> {
                    return visitDoStat(ctx)
                }
                is LuaParser.FunctionCallStatContext -> {
                    return visitFunctionCallStat(ctx)
                }
                is LuaParser.WhileStatContext -> {
                    return visitWhileStat(ctx)
                }
            }
            return LocalStatement()
        }

       override fun visitWhileStat(ctx:LuaParser.WhileStatContext):StatementNode {
           val result = WhileStatement()
           result.condition = ctx.findExp()?.let { visitExp(it) } ?: ConstantsNode()
           result.body = ctx.findBlock()?.let { visitBlock(it) } ?: BlockNode()
           return result
       }

        private fun visitAttrNameList(ctx: LuaParser.AttnamelistContext): List<ASTNode> {
            val result = mutableListOf<ASTNode>()
            ctx.NAME().forEach {
                result.add(
                    Identifier(
                        name = it.text
                    )
                )
            }
            return result
        }

        override fun visitExp(ctx: LuaParser.ExpContext): ExpressionNode {
            return when {
                ctx.findNumber() != null -> {
                    ConstantsNode(
                        type = ConstantsNode.TYPE.NUMBER,
                        value = ctx.findNumber()?.text ?: "0"
                    )
                }
                else -> ConstantsNode(
                    type = ConstantsNode.TYPE.UNKNOWN,
                    value = ""
                )
            }
        }

        private fun visitExpList(ctx: LuaParser.ExplistContext): List<ExpressionNode> {
            val result = mutableListOf<ExpressionNode>()
            ctx.findExp().forEach {
                result.add(visitExp(it))
            }
            return result
        }

        override fun visitLocalVarListStat(ctx: LuaParser.LocalVarListStatContext): LocalStatement {
            return LocalStatement(
                variables = mutableListOf<ASTNode>()
                    .apply {
                        ctx.findAttnamelist()
                            ?.let { visitAttrNameList(it) }
                            ?.let {
                                addAll(
                                    it
                                )
                            }
                    },
                init = mutableListOf<ExpressionNode>()
                    .apply {
                        ctx.findExplist()
                            ?.let {
                                visitExpList(it)
                            }
                            ?.let {
                                addAll(it)
                            }
                    }
            )
        }


        private fun parseCallExpressionArguments(
            ctx: LuaParser.FunctioncallContext,
            expression: CallExpression
        ) {

            //parser arguments
            ctx.findNameAndArgs().let {
                it.getOrNull(it.size - 1)
            }?.run {
                findArgs()
            }?.run {
                when {
                    findString() != null -> {
                        findString()?.text?.let { textNode ->
                            getStringContent(textNode)?.let {
                                expression.arguments.add(Identifier(it))
                            }
                        }
                    }
                    findExplist() != null -> {
                        findExplist()?.let { expListContext ->
                            expListContext.findExp().forEach {
                                expression.arguments.add(visitExp(it))
                            }
                        }
                    }
                    else -> null
                }
            }

        }


        override fun visitFunctioncall(ctx: LuaParser.FunctioncallContext): CallExpression {
            val expression = CallExpression()

            expression.base = parseCallExpressionBase(ctx)

            //parser base

            if (ctx.findNameAndArgs().isNotEmpty()) {
                parseCallExpressionArguments(ctx, expression)
            }

            return expression

        }

        private fun parseCallExpressionBase(ctx: LuaParser.FunctioncallContext): ExpressionNode {


            

            val list = buildCallExpressionParseList(ctx)


            list
                .toMutableList() //copy list
                .apply { reverse() }
                .forEachIndexed { index, parserRuleContext ->
                    if (index == 1) {
                        return@forEachIndexed //break
                    }

                    when (parserRuleContext) {
                        is LuaParser.NameAndArgsContext -> {

                        }
                        is LuaParser.VarSuffixContext -> {

                        }
                    }

                }

            TODO("")

        }

        private fun buildCallExpressionParseList(ctx:LuaParser.FunctioncallContext): MutableList<ParserRuleContext> {

            val result = mutableListOf<ParserRuleContext>()


            ctx.findVarOrExp()?.findLvar()?.findVarSuffix()?.forEach {
                result.add(it)
            }

            ctx.findNameAndArgs().forEach {
                result.add(it)
            }


            return result
        }

        override fun visitFunctionCallStat(ctx: LuaParser.FunctionCallStatContext): StatementNode {
            val statement = CallStatement()
            statement.expression =
                ctx.findFunctioncall()?.let { visitFunctioncall(it) } ?: CallExpression()
            return statement
        }

        override fun visitDoStat(ctx: LuaParser.DoStatContext): DoStatement {
            val statement = DoStatement()
            statement.body = ctx.findBlock()?.let { it -> visitBlock(it) } ?: BlockNode()
            return statement
        }

        override fun visitBlock(ctx: LuaParser.BlockContext): BlockNode {
            val blockNode = BlockNode()
            ctx.findStat().forEach {
                blockNode.addStatement(visitStat(it))
            }
            return blockNode
        }
    }

}