package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.api.project.ProjectProvider
import com.google.gson.Gson
import java.io.File

class AndroidProjectProvider(private val pluginContext: PluginContext) : ProjectProvider {
    override fun indexProject(projectPath: String): Project? {
        val configFile = File(projectPath, ".MyLuaApp/.config.json")

        val config = Gson().fromJson(configFile.readText(), getJavaClass<Map<String, String>>())

        if (config["projectType"] == "AndroidProject") {
            return AndroidProject(projectPath.toFile(), pluginContext)
        }
        return null

    }
}