package com.dingyi.myluaapp.plugin.api


import com.dingyi.myluaapp.plugin.api.context.PluginContext


interface PluginModule: PluginContext {

    fun init()


    fun loadPlugin(pluginId:String)

    fun loadAllPlugin()

    fun installPlugin(pluginPath:String)

    fun uninstallPlugin(pluginId: String)

    fun getAllPlugin():List<Plugin>

    fun stop()

}