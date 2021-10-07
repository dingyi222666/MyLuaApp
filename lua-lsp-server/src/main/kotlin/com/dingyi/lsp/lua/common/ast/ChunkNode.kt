package com.dingyi.lsp.lua.common.ast

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/7 10:10
 * @description:
 **/
class ChunkNode:ASTNode() {
    var body by Delegates.notNull<BlockNode>()
}