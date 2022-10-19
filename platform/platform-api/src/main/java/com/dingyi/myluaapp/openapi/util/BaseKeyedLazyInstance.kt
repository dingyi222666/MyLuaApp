package com.dingyi.myluaapp.openapi.util

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.extensions.PluginAware
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor

abstract class BaseKeyedLazyInstance<T> :
    LazyExtensionInstance<T>, PluginAware {
    private var pluginDescriptor: PluginDescriptor? = null

    protected constructor() {}


    protected constructor(instance: T) : super(instance) {
    }


    fun getPluginDescriptor(): PluginDescriptor {
        return checkNotNull(pluginDescriptor)
    }

    override fun setPluginDescriptor(value: PluginDescriptor) {
        pluginDescriptor = value
    }


    abstract override val implementationClassName: String?


    override fun getInstance(): T {
        return getInstance(ApplicationManager.getApplication(), pluginDescriptor)
    }


    // todo get rid of it - pluginDescriptor must be not null
    val loaderForClass: ClassLoader
        get() = pluginDescriptor?.classLoader ?: error("")
}
