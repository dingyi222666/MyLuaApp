package com.dingyi.lsp.lua.common.ast

/**
 * @author: dingyi
 * @date: 2021/10/7 10:11
 * @description:
 **/
class BlockNode() : ASTNode() {

    constructor(vararg statements: StatementNode) : this() {
        this.statements.addAll(statements)
    }

    val statements = mutableListOf<StatementNode>()

    var returnStatement : StatementNode? = null

    fun addStatement(statement:StatementNode) {
        statements.add(statement)
    }


}