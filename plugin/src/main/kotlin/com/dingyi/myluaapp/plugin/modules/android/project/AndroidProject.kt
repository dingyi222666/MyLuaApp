package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.FileTemplate
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.dingyi.myluaapp.plugin.runtime.project.DefaultFileTemplate
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AndroidProject(
    private val projectPath: File,
    private val pluginContext: PluginContext
):Project {

    private val configMap = mutableMapOf<String,String>()

    init {
        val configFile = File(projectPath,".MyLuaApp/.config.json")
        configMap.putAll(
            Gson().fromJson(configFile.readText(), getJavaClass())
        )
    }

    override fun backup(exportPath: File) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFile(targetFile: File): Unit = withContext(Dispatchers.IO) {
        if (targetFile.isFile) {
            pluginContext
                .getEditorService()
                .closeEditor(targetFile)
        } else {
            targetFile
                .walkBottomUp()
                .filter { it.isFile }
                .filter { file ->
                    pluginContext.getEditorService()
                        .getAllEditor().find { editor ->
                            editor
                                .getFile().path == file.path
                        } != null
                }
                .forEach {
                    pluginContext
                        .getEditorService()
                        .closeEditor(it)
                }


        }

        targetFile.deleteRecursively()

    }


    override fun walkProjectFile(): FileTreeWalk {
        TODO("Not yet implemented")
    }

    override fun runProject() {
        pluginContext
            .getBuildService()
            .build(this, "build debug")

        pluginContext
            .getActionService()
            .callAction<Unit>(
                pluginContext
                    .getActionService()
                    .createActionArgument(), DefaultActionKey.OPEN_LOG_FRAGMENT
            )

    }


    override fun getFileTemplates(): List<FileTemplate> {
        return readDefaultFileTemplates(
            File(Paths.assetsDir, "plugin/default_template.json"),
            File(Paths.assetsDir, "plugin/lua_project_template.json")
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
    override val packageName: String?
        get() = configMap["appPackageName"].toString()
    override val path: File
        get() = projectPath
    override val iconPath: String?
        get() = projectPath.path + "/" + configMap["iconPath"]

    override val type: String
        get() = "AndroidProject"

}
