package com.dingyi.myluaapp.plugin.modules.android

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.editor.lsp.server.definition.SocketLanguageServerDefinition
import com.dingyi.myluaapp.editor.lsp.server.java.Main
import com.dingyi.myluaapp.plugin.api.Plugin
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.modules.android.action.AndroidProjectMenuAction

import com.dingyi.myluaapp.plugin.modules.android.project.AndroidProjectCreatorProvider
import com.dingyi.myluaapp.plugin.modules.android.project.AndroidProjectProvider
import com.dingyi.myluaapp.plugin.modules.default.action.CommonActionKey
import com.dingyi.myluaapp.plugin.modules.android.action.CreateJavaEditorAction
import com.dingyi.myluaapp.plugin.runtime.ktx.getRandomPort

class MainPlugin: Plugin {

    override fun onInstall(context: PluginContext) {

    }

    override fun onUninstall(context: PluginContext) {

    }

    override fun onStart(context: PluginContext) {

        context
            .getProjectService().apply {
                addProjectCreatorProvider(AndroidProjectCreatorProvider(context))
                addProjectProvider(AndroidProjectProvider(context))
            }


        val port = getRandomPort()



        val javaLanguageServerDefinition = SocketLanguageServerDefinition("java", port) {
            Main.startWithSocket(arrayOf(port.toString()))
        }


        context.getActionService()
            .apply {
                registerAction(
                    getJavaClass<AndroidProjectMenuAction>(),
                    CommonActionKey.ADD_PROJECT_MENU
                )
                context.apply {



                    getActionService()
                        .apply {

                            registerForwardArgument(
                                CommonActionKey.CREATE_EDITOR_ACTION,
                                getJavaClass<CreateJavaEditorAction>()
                            ) {
                                it.addArgument(javaLanguageServerDefinition)
                            }
                            registerAction(
                                getJavaClass<CreateJavaEditorAction>(),
                                CommonActionKey.CREATE_EDITOR_ACTION
                            )
                        }
                }

            }
    }

    override fun onStop(context: PluginContext) {
        Main.close()
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