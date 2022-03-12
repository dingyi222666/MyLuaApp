package com.dingyi.myluaapp.plugin.modules.default


import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext

class MainPlugin: Plugin {

    override fun onInstall(context: PluginContext) {
        throw RuntimeException("Stub!");
    }

    override fun onUninstall(context: PluginContext) {
        throw RuntimeException("Stub!");
    }

    override fun onStart(context: PluginContext) {

        throw RuntimeException("Stub!");
    }

    override fun onStop(context: PluginContext) {
        throw RuntimeException("Stub!");
    }

    override val pluginId: String
        get() = "com.dingyi.MyLuaApp.core.plugin.default"
    override val pluginName: String
        get() = "基础模块"
    override val pluginVersion: String
        get() = "1.0"
    override val pluginAuthor: String
        get() = "dingyi"
    override val pluginDescription: String
        get() = "基础模块，不可禁用，提供整体软件框架的基础模块"
}