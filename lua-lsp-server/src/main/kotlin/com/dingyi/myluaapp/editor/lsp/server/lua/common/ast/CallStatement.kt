package com.dingyi.myluaapp.editor.lsp.server.lua.common.ast

import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.CallExpression
import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.StatementNode
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/9 14:58
 * @description:
 **/
class CallStatement : StatementNode() {
    var expression by Delegates.notNull<CallExpression>()
}