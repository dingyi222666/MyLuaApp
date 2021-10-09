package com.dingyi.lsp.lua.common.ast

import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/7 10:38
 * @description:
 **/
class ConstantsNode(
    var type: TYPE = TYPE.UNKNOWN,
    value: Any = 0
) : ExpressionNode() {

    private var _value: Any = 0

    var rawValue by Delegates.observable(
        initialValue = Any(),
        onChange = { _, _, newValue ->
            _value = switchValue(newValue)
        }
    )

    private fun switchValue(newValue: Any): Any {
        return when (type) {
            TYPE.NUMBER -> {
                newValue.toString().toIntOrNull() ?: newValue.toString().toDoubleOrNull()
                ?: newValue
            }
            else -> newValue
        }
    }


    init {
        this.rawValue = value
    }

    enum class TYPE {
        NUMBER, BOOLEAN, STRING, UNKNOWN
    }

    fun numberOf(): Double {
        return _value as Double
    }

    fun booleanOf(): Boolean {
        return _value as Boolean
    }

    override fun toString(): String {
        return "ConstantsNode(type=$type, value=$_value)"
    }

}