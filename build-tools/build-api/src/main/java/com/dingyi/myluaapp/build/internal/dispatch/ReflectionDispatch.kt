package com.dingyi.myluaapp.build.internal.dispatch

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ReflectionDispatch(private val target: Any) :
    Dispatch<MethodInvocation> {
    override fun dispatch(message: MethodInvocation) {
        kotlin.runCatching {
            val method = message.getMethod()
            method.isAccessible = true
            method.invoke(target, message.getArguments())
        }.onFailure {
            if (it is InvocationTargetException) {
                throw it.targetException
            }
            throw it
        }
    }
}

