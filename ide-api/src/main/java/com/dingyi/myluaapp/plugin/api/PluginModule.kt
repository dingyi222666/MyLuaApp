package com.dingyi.myluaapp.plugin.api


import com.dingyi.myluaapp.plugin.api.context.PluginContext


interface PluginModule: PluginContext {

    fun init()


    fun loadPlugin(pluginId:String)

    fun loadAllPlugin()

    /**
     * install a plugin for given plugin path
     * @return install status
     */
    suspend fun installPlugin(pluginPath:String):Int

    suspend fun uninstallPlugin(pluginId: String)

    fun getAllPlugin():List<Plugin>

    fun stop()





}