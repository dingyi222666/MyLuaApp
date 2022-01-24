package com.dingyi.lsp.lua.common.ast

import java.io.FileOutputStream
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/8 20:08
 * @description:
 **/
class DoStatement : StatementNode() {

    var body by Delegates.notNull<BlockNode>()

}