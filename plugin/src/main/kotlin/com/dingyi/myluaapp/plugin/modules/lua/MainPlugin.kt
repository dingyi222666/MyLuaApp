package com.dingyi.myluaapp.plugin.modules.lua

import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.modules.default.action.FileTagMenuAction
import com.dingyi.myluaapp.plugin.modules.default.action.SymbolClickAction
import com.dingyi.myluaapp.plugin.modules.default.editor.EditorProvider
import com.dingyi.myluaapp.plugin.modules.lua.action.CreateEditorAction
import com.dingyi.myluaapp.plugin.modules.lua.build.LuaBuildService

class MainPlugin: Plugin {

    override fun onInstall(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override fun onUninstall(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override fun onStart(context: PluginContext) {


        context.apply {
            getEditorService()
                .apply {
                    addSupportLanguages("lua", "aly")
                }

            getActionService()
                .apply {
                    registerAction(
                        getJavaClass<CreateEditorAction>(),
                        DefaultActionKey.CREATE_EDITOR_ACTION
                    )
                }

            getBuildService<Service>()
                .addBuildService(LuaBuildService())
        }
    }

    override fun onStop(context: PluginContext) {

    }

    override val pluginId: String
        get() = "com.dingyi.MyLuaApp.core.plugin.lua"
    override val pluginName: String
        get() = "lua语言支持模块"
    override val pluginVersion: String
        get() = "1.0"
    override val pluginAuthor: String
        get() = "dingyi"
    override val pluginDescription: String
        get() = "lua语言支持模块，提供对lua语言的编辑器支持和项目管理"
}