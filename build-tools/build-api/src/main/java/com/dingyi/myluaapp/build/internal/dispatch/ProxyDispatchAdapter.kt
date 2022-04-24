package com.dingyi.myluaapp.build.internal.dispatch

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.system.measureNanoTime

// in Android, only have one class loader, so we can use the same class loader to load the proxy class
class ProxyDispatchAdapter<T : Any>(
    dispatch: Dispatch<in MethodInvocation>,
    private val type: Class<T>
) {

    private val source: T =
        Proxy.newProxyInstance(
            type.classLoader,
            arrayOf(type),
            DispatchingInvocationHandler(dispatch, type)
        ) as T

    fun getType(): Class<T> {
        return type
    }

    fun getSource(): T {
        return source
    }

    class DispatchingInvocationHandler(
        private val dispatch: Dispatch<in MethodInvocation>,
        private val type: Class<*>
    ) : InvocationHandler {


        override fun invoke(target: Any, targetMethod: Method, parameters: Array<Any?>): Any {

            if (targetMethod.name == "equals") {
                val parameter = parameters[0]
                if (parameter == null || !Proxy.isProxyClass(parameter.javaClass)) {
                    return false
                }
                val handler: Any = Proxy.getInvocationHandler(parameter)
                if (!DispatchingInvocationHandler::class.java.isInstance(handler)) {
                    return false
                }
                val otherHandler = handler as DispatchingInvocationHandler
                return otherHandler.type == type && otherHandler.dispatch === dispatch
            }

            if (targetMethod.name == "hashCode") {
                return dispatch.hashCode()
            }
            if (targetMethod.name == "toString") {
                return type.simpleName + " broadcast"
            }

            val methodInvocation = MethodInvocation(targetMethod, parameters)
            return dispatch.dispatch(methodInvocation)
        }

    }


}