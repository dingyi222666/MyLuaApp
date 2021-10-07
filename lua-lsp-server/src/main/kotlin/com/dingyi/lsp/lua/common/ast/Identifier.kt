package com.dingyi.lsp.lua.common.ast

/**
 * @author: dingyi
 * @date: 2021/10/7 10:48
 * @description:
 **/
class Identifier(var name:String = ""):ASTNode() {
    override fun toString(): String {
        return "Identifier(name='$name')"
    }
}