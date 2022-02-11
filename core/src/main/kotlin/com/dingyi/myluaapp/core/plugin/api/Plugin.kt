package com.dingyi.myluaapp.core.plugin.api

import android.app.Activity
import com.dingyi.myluaapp.core.plugin.api.context.PluginContext
import java.io.InputStream

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