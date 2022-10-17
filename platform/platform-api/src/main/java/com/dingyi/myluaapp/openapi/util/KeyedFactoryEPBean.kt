package com.dingyi.myluaapp.openapi.util

import com.dingyi.myluaapp.openapi.extensions.PluginAware
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor


class KeyedFactoryEPBean(
    private var pluginDescriptor: PluginDescriptor
) : PluginAware {

    // these must be public for scrambling compatibility

    lateinit var key: String

    var implementationClass: String? = null

     var factoryClass: String? = null

    fun getPluginDescriptor(): PluginDescriptor {
        return pluginDescriptor
    }

    override fun setPluginDescriptor(pluginDescriptor: PluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor
    }
}