package com.dingyi.myluaapp.build.internal.dispatch

import java.lang.reflect.Method

class MethodInvocation(
    private val method: Method, args: Array<Any?>?
) {


    private val arguments = args ?: ZERO_ARGS

    fun getArguments(): Array<out Any?> {
        return arguments
    }

    fun getMethod(): Method {
        return method
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }
        if (other == null || other.javaClass != javaClass) {
            return false
        }
        val now = other as MethodInvocation
        return (method != now.method) and arguments.contentEquals(now.arguments)
    }

    override fun hashCode(): Int {
        return method.hashCode()
    }

    override fun toString(): String {
        return String.format(
            "[MethodInvocation method: %s(%s)]",
            method.name,
            arguments.joinToString(separator = ", ")
        )
    }

    companion object {
        private val ZERO_ARGS = arrayOf<Any?>(0)
    }
}
