package com.dingyi.myluaapp.editor.lsp.server.lua.common.ast

import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.ASTNode
import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.ExpressionNode
import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.StatementNode

/**
 * @author: dingyi
 * @date: 2021/10/7 10:23
 * @description:
 **/
class LocalStatement(
    val variables: MutableList<ASTNode> = mutableListOf(),
    val init: MutableList<ExpressionNode> = mutableListOf(),
) : StatementNode() {

    override fun toString(): String {
        return "LocalStatement(variables=$variables, init=$init)"
    }
}