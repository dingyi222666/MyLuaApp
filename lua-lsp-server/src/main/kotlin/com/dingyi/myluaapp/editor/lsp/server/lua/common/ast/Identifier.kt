package com.dingyi.myluaapp.editor.lsp.server.lua.common.ast

import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.ExpressionNode

/**
 * @author: dingyi
 * @date: 2021/10/7 10:48
 * @description:
 **/
class Identifier(var name: String = "") : ExpressionNode() {
    override fun toString(): String {
        return "Identifier(name='$name')"
    }
}