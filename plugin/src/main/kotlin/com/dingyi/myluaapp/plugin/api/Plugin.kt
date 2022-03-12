package com.dingyi.myluaapp.plugin.api

import com.dingyi.myluaapp.plugin.api.context.PluginContext

interface Plugin {


    /**
     * Call this when install plugin
     */
    fun onInstall(context: PluginContext)

    /**
     * Call this when uninstall plugin
     */
    fun onUninstall(context: PluginContext)


    /**
     * Call this when start plugin
     */
    fun onStart(context: PluginContext)

    /**
     * Call this when stop plugin
     */
    fun onStop(context: PluginContext)


    val pluginId:String

    val pluginName:String

    val pluginVersion:String

    val pluginAuthor:String

    val pluginDescription:String

    val targetApiVersion: Int
        get() = 1
}