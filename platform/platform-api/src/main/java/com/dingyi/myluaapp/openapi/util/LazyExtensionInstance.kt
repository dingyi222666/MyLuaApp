package com.dingyi.myluaapp.openapi.util

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.service.ServiceRegistry


/**
 * Use only if you need to cache created instance.
 *
 * @see com.intellij.openapi.extensions.ExtensionPointName.processWithPluginDescriptor
 */
abstract class LazyExtensionInstance<T> {
    @Volatile
    protected open var _instance: T? = null

    protected constructor() {}


    protected constructor(instance: T) {
        this._instance = instance
    }


    protected abstract val implementationClassName: String?

    open fun getInstance() = _instance

    fun getInstance(
        componentManager: ServiceRegistry,
        pluginDescriptor: PluginDescriptor?
    ): T {
        var result = _instance
        if (result != null) {
            return result
        }
        synchronized(this) {
            result = _instance
            if (result != null) {
                return result as (T & Any)
            }
            result = createInstance(componentManager, pluginDescriptor)
            _instance = result
        }
        return checkNotNull(result)
    }


    fun createInstance(
        componentManager: ServiceRegistry,
        pluginDescriptor: PluginDescriptor?
    ): T {

        checkNotNull(pluginDescriptor)

        val className = implementationClassName
            ?: error(
                "implementation class is not specified ${pluginDescriptor}"
            )
        return componentManager.instantiateClass(className, pluginDescriptor) as T
    }
}
