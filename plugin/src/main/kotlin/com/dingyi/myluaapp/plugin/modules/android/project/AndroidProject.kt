package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey
import com.google.gson.Gson
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

    override fun deleteFile(targetFile: File) {
        TODO("Not yet implemented")
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
