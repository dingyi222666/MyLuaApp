package com.dingyi.myluaapp.plugin.runtime.plugin.dynamic

import android.content.Context
import com.dingyi.myluaapp.plugin.api.Plugin

import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.runtime.properties.Properties

open class WrapperBasePluginContext(
    private val pluginContext: PluginContext,
    private val plugin: Plugin
) : PluginContext by pluginContext {
    private val properties = Properties(plugin.pluginId)

    override fun getProperties(): com.dingyi.myluaapp.plugin.api.Properties {
        return properties
    }


}