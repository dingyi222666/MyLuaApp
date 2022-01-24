package com.dingyi.lsp.lua.common.ast

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