package com.dingyi.lsp.lua.common.ast

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/20 11:07
 * @description:
 **/
class MemberExpression : ExpressionNode() {


    var identifier by Delegates.notNull<Identifier>()

    var indexer by Delegates.notNull<String>()

    var base by Delegates.notNull<ExpressionNode>()

}