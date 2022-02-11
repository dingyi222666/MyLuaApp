package com.dingyi.myluaapp.core.plugin.api

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.core.plugin.api.context.PluginContext
import com.dingyi.myluaapp.core.plugin.api.ui.UIProvider


interface PluginModule:PluginContext {

    fun init(activity: AppCompatActivity)


    fun loadPlugin(pluginId:String)

    fun loadAllPlugin()

    fun installPlugin(pluginPath:String)

    fun uninstallPlugin(pluginId: String)

    fun getAllPlugin():List<Plugin>

    fun stop()

}