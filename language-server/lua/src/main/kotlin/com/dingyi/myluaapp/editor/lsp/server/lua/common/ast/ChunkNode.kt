package com.dingyi.myluaapp.editor.lsp.server.lua.common.ast

import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.ASTNode
import com.dingyi.myluaapp.editor.lsp.server.lua.common.ast.BlockNode
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/7 10:10
 * @description:
 **/
class ChunkNode : ASTNode() {
    var body by Delegates.notNull<BlockNode>()
}