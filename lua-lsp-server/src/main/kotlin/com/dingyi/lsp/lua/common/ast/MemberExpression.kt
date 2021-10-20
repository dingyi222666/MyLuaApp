package com.dingyi.lsp.lua.common.ast

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/20 11:07
 * @description:
 **/
class MemberExpression : ExpressionNode() {
    var base by Delegates.notNull<ExpressionNode>()
    var indexed by Delegates.notNull<Identifier>()
    var identifier by Delegates.notNull<Identifier>()
}