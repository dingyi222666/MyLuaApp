package com.dingyi.myluaapp.openapi.util

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.extensions.PluginAware
import com.dingyi.myluaapp.openapi.service.ServiceRegistry


abstract class CustomLoadingExtensionPointBean<T> :
    BaseKeyedLazyInstance<T> {



    var factoryClass: String? = null


    lateinit var factoryArgument: String

    constructor() : super()
    constructor(instance: T) : super(instance)


    fun createInstance(componentManager: ServiceRegistry): T {
        val instance = if (factoryClass == null) {
            super.createInstance(componentManager, getPluginDescriptor())
        } else {
            val factory = componentManager.instantiateClass(
                factoryClass.toString(),
                getPluginDescriptor().checkNotNull()
            ) as ExtensionFactory
            factory.createInstance(factoryArgument, implementationClassName) as T
        }
        if (instance is PluginAware) {
            (instance as PluginAware).setPluginDescriptor(getPluginDescriptor())
        }
        return instance
    }
}
