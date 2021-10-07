package com.dingyi.lsp.lua.common.ast

import com.dingyi.lsp.lua.common.parser.LuaBaseVisitor
import com.dingyi.lsp.lua.common.parser.LuaLexer
import com.dingyi.lsp.lua.common.parser.LuaParser
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream

/**
 * @author: dingyi
 * @date: 2021/10/7 10:50
 * @description: convert cst to ast
 **/
class ASTGenerator(private val inputCode: String) {

    private val targetLexer = LuaLexer(CharStreams.fromString(inputCode))
    private val targetParser = LuaParser(CommonTokenStream(targetLexer))

    fun generate(): ChunkNode {
        return CSTVisitor().visitChunk(targetParser.chunk())
    }

    class CSTVisitor : LuaBaseVisitor<ASTNode>() {
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
            }
            return LocalStatement()
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

        override fun visitBlock(ctx: LuaParser.BlockContext): BlockNode {
            val blockNode = BlockNode()
            ctx.findStat().forEach {
                blockNode.addStatement(visitStat(it))
            }
            return blockNode
        }
    }

}