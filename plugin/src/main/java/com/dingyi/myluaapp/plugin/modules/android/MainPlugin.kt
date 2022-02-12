package com.dingyi.myluaapp.plugin.modules.android

import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.modules.android.project.AndroidCreateProjectProvider
import com.dingyi.myluaapp.plugin.modules.android.project.AndroidProjectProvider

class MainPlugin: Plugin {

    override fun onInstall(context: PluginContext) {

    }

    override fun onUninstall(context: PluginContext) {

    }

    override fun onStart(context: PluginContext) {

        context
            .getProjectService()
            .addCreateProjectProvider(AndroidCreateProjectProvider())

        context
            .getProjectService()
            .addProjectProvider(AndroidProjectProvider())


    }

    override fun onStop(context: PluginContext) {

    }

    override val pluginId: String
        get() = "com.dingyi.MyLuaApp.core.plugin.android"
    override val pluginName: String
        get() = "Android项目模块"
    override val pluginVersion: String
        get() = "1.0"
    override val pluginAuthor: String
        get() = "dingyi"
    override val pluginDescription: String
        get() = "提供对Android项目支持的插件"

}