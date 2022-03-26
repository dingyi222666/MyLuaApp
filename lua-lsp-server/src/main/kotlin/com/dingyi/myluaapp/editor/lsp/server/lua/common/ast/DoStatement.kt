package com.dingyi.myluaapp.editor.lsp.server.lua.common.ast

import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.BlockNode
import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.StatementNode
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/8 20:08
 * @description:
 **/
class DoStatement : StatementNode() {

    var body by Delegates.notNull<BlockNode>()

}