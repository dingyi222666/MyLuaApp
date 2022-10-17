package com.dingyi.myluaapp.openapi.util


import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import com.intellij.openapi.diagnostic.ControlFlowException

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


abstract class KeyedExtensionFactory<T : Any, KeyT>(
    private val myInterfaceClass: Class<T>,
    epName: ExtensionPointName<KeyedFactoryEPBean>,
    componentManager: ServiceRegistry
) {
    private val myEpName: ExtensionPointName<KeyedFactoryEPBean>
    private val componentManager: ServiceRegistry

    init {
        myEpName = epName
        this.componentManager = componentManager
    }

    fun get(): T {
        val handler =
            InvocationHandler { proxy, method, args ->
                val epBeans: List<KeyedFactoryEPBean> = myEpName.extensionList
                val keyArg = args[0] as KeyT
                val key = getKey(keyArg)
                var result = getByKey(epBeans, key, method, args)
                if (result == null) {
                    result = getByKey(epBeans, null, method, args)
                }
                result
            }
        return Proxy.newProxyInstance(
            myInterfaceClass.classLoader,
            arrayOf<Class<*>>(myInterfaceClass),
            handler
        ) as T
    }

    fun getByKey(key: KeyT): T {
        return checkNotNull(findByKey(getKey(key), myEpName, componentManager))
    }

    private fun getByKey(
        epBeans: List<KeyedFactoryEPBean>,
        key: String?,
        method: Method,
        args: Array<Any>
    ): T? {
        for (epBean in epBeans) {
            if ((if (epBean.key == null) "" else epBean.key) != key ?: "") {
                continue
            }
            if (epBean.implementationClass != null) {
                return componentManager.instantiateClass(
                    epBean.implementationClass.toString(),
                    epBean.getPluginDescriptor()
                ) as T
            }
            val factory: Any =
                componentManager.instantiateClass(
                    epBean.factoryClass.toString(),
                    epBean.getPluginDescriptor()
                )
            try {
                val result = method.invoke(factory, *args) as T?
                if (result != null) {
                    return result
                }
            } catch (e: RuntimeException) {
                if (e is ControlFlowException) {
                    throw e
                }
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
        return null
    }

    abstract fun getKey(key: KeyT): String

    companion object {
        fun <T> findByKey(
            key: String,
            point: ExtensionPointName<KeyedFactoryEPBean>,
            componentManager: ServiceRegistry
        ): T? {
            for (epBean in point.extensionList) {
                if (key != epBean.key || epBean.implementationClass == null) {
                    continue
                }
                return componentManager.instantiateClass(
                    epBean.implementationClass.toString(),
                    epBean.getPluginDescriptor()
                ) as T?
            }
            return null
        }
    }
}

