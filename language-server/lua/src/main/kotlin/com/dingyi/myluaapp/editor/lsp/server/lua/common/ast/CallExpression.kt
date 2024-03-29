package com.dingyi.myluaapp.editor.lsp.server.lua.common.ast

import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.ExpressionNode
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/9 15:00
 * @description:
 **/
class CallExpression : ExpressionNode() {
    var base by Delegates.notNull<ExpressionNode>()
    val arguments = mutableListOf<ExpressionNode>()
}