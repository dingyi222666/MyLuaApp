package com.dingyi.myluaapp.plugin.modules.default

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.modules.default.action.FileTagMenuAction
import com.dingyi.myluaapp.plugin.modules.default.action.SymbolClickAction
import com.dingyi.myluaapp.plugin.modules.default.editor.EditorProvider

class MainPlugin: Plugin {

    override fun onInstall(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override fun onUninstall(context: PluginContext) {
        TODO("Not yet implemented")
    }

    override fun onStart(context: PluginContext) {


        context
            .getEditorService()
            .apply {
                addEditorProvider(EditorProvider(context))
                addSupportLanguages("json", "lua", "java", "aly", "xml")
            }

        context
            .getActionService()
            .apply {
                registerAction(
                    getJavaClass<SymbolClickAction>(),
                    DefaultActionKey.CLICK_SYMBOL_VIEW
                )
                registerAction(
                    getJavaClass<FileTagMenuAction>(),
                    DefaultActionKey.SHOW_FILE_TAG_MENU
                )
            }
    }

    override fun onStop(context: PluginContext) {

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