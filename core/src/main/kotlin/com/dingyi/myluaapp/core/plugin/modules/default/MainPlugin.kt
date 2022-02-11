package com.dingyi.myluaapp.core.plugin.modules.default

import android.util.Log
import com.dingyi.myluaapp.core.plugin.api.Plugin
import com.dingyi.myluaapp.core.plugin.api.context.PluginContext

class MainPlugin:Plugin {

    override fun onInstall(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override fun onUninstall(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override fun onStart(context: PluginContext) {
        Log.e("test","Hello Plugin")
    }

    override fun onStop(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override val pluginId: String
        get() = "com.dingyi.MyLuaApp.core.plugin.default"
    override val pluginName: String
        get() = "默认模块"
    override val pluginVersion: String
        get() = "1.0"
    override val pluginAuthor: String
        get() = "dingyi"
    override val pluginDescription: String
        get() = "基础模块，提供多种功能"
}