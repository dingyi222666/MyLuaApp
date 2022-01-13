package com.dingyi.myluaapp.builder.api.internal.plugin

import com.dingyi.myluaapp.builder.api.Plugin
import com.dingyi.myluaapp.builder.api.internal.BuilderInternal

class DefaultPluginContainer(builder: BuilderInternal):PluginContainer {

    private val allPlugin = mutableMapOf<String,Plugin<*>>()

    override fun registerPlugin(plugin: Plugin<*>) {
        allPlugin[plugin.id] = plugin
    }

    override fun removePlugin(id: String) {
        allPlugin.remove(id)
    }

    override fun <T : Plugin<*>> getPlugin(id: String): T {
        return allPlugin.getValue(id) as T
    }


}