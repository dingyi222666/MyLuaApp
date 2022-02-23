package com.dingyi.myluaapp.plugin.api

import com.dingyi.myluaapp.plugin.api.context.PluginContext

interface Plugin {


    fun onInstall(context: PluginContext)

    fun onUninstall(context: PluginContext)


    fun onStart(context: PluginContext)

    fun onStop(context: PluginContext)


    val pluginId:String

    val pluginName:String

    val pluginVersion:String

    val pluginAuthor:String

    val pluginDescription:String
}