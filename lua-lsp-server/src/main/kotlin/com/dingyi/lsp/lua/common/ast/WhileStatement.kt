package com.dingyi.lsp.lua.common.ast

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/20 11:41
 * @description:
 **/
class WhileStatement : StatementNode() {
    var condition by Delegates.notNull<ASTNode>()
    var body by Delegates.notNull<BlockNode>()

    override fun toString(): String {
        return "WhileStatement(condition=$condition, body=$body)"
    }


}