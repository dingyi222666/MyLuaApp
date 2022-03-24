package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.ProjectCreatorProvider
import com.dingyi.myluaapp.plugin.api.project.ProjectTemplate
import com.google.gson.Gson
import java.io.File

class AndroidProjectCreatorProvider(
    private val pluginContext: PluginContext
) : ProjectCreatorProvider {

    private val jsonPath = File(pluginContext.getPluginDir(),"project/android/project.json")

    override fun getTemplates(): List<ProjectTemplate> {

        val allTemplate = Gson()
            .fromJson(
                jsonPath.readText(),
                getJavaClass<Template>()
            ).templates

        allTemplate.forEach { templateData ->
            templateData.path = pluginContext.getPluginDir().path + "/project/android/" + templateData.path
        }

        return allTemplate.map {
            AndroidProjectTemplate(
                it
            )
        }

    }

    data class Template(val templates: List<AndroidProjectTemplate.TemplateData>)
}