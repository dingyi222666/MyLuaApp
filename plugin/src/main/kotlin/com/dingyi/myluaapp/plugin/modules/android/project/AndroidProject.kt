package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.FileTemplate
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.runtime.project.DefaultFileTemplate
import com.dingyi.myluaapp.plugin.runtime.project.DefaultProject
import com.google.gson.Gson
import java.io.File

class AndroidProject(
    private val projectPath: File,
    private val pluginContext: PluginContext
):DefaultProject(pluginContext) {

    private val configMap = mutableMapOf<String,String>()

    init {
        val configFile = File(projectPath,".MyLuaApp/.config.json")
        configMap.putAll(
            Gson().fromJson(configFile.readText(), getJavaClass())
        )
    }


    override fun runProject() {

        pluginContext
            .getEditorService()
            .saveEditorServiceState()

        pluginContext
                .getBuildService<Service>()
            .build(this, "build debug")

        pluginContext
            .getActionService()
            .callAction<Unit>(
                pluginContext
                    .getActionService()
                    .createActionArgument(), DefaultActionKey.BUILD_STARTED_KEY
            )

    }


    override fun getFileTemplates(): List<FileTemplate> {
        return readDefaultFileTemplates(
            File(pluginContext.getPluginDir(), "default_template.json"),
            File(pluginContext.getPluginDir(), "lua_project_template.json")
        )
    }


    private fun readDefaultFileTemplates(vararg file: File): List<FileTemplate> {
        return file.map { nowFile ->
            Gson().fromJson(nowFile.readText(), getJavaClass<FileTemplateBean>()) to nowFile
        }.flatMap { pair ->
            pair.first.map {
                DefaultFileTemplate(pair.second.parentFile?.path + "/" + it.templatePath, it.templateName)
            }
        }
    }

     class FileTemplateBean: ArrayList<FileTemplateBean.FileTemplateBeanItem>() {
        data class FileTemplateBeanItem(
            val templateName: String, // Lua Empty Layout
            val templatePath: String // file/lua_empty_layout.aly'
        )
    }

    override val name: String
        get() = configMap["appName"] ?: projectPath.name
    override val packageName: String
        get() = configMap["appPackageName"].toString()
    override val path: File
        get() = projectPath
    override val iconPath: String?
        get() = projectPath.path + "/" + configMap["iconPath"]

    override val type: String
        get() = "AndroidProject"

}
