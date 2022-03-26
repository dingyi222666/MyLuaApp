package com.dingyi.myluaapp.editor.lsp.service.lua.common.ast

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/9 14:58
 * @description:
 **/
class CallStatement : StatementNode() {
    var expression by Delegates.notNull<CallExpression>()
}