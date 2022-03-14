package com.dingyi.myluaapp.plugin.api


import com.dingyi.myluaapp.plugin.api.context.PluginContext


interface PluginModule: PluginContext {

    fun init()


    fun loadPlugin(pluginId:String)

    fun loadAllPlugin()

    suspend fun installPlugin(pluginPath:String)

    suspend fun uninstallPlugin(pluginId: String)

    fun getAllPlugin():List<Plugin>

    fun stop()



}