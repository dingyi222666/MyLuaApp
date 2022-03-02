package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.project.ProjectCreatorProvider
import com.dingyi.myluaapp.plugin.api.project.ProjectTemplate
import com.google.gson.Gson

class AndroidProjectCreatorProvider : ProjectCreatorProvider {

    private val jsonPath = Paths.tempateDir + "/project/android/project.json"

    override fun getTemplates(): List<ProjectTemplate> {

        val allTemplate = Gson()
            .fromJson(
                jsonPath.toFile().readText(),
                getJavaClass<Template>()
            ).templates

        allTemplate.forEach { templateData ->
            templateData.path = Paths.tempateDir + "/project/android/" + templateData.path
        }

        return allTemplate.map {
            AndroidProjectTemplate(
                it
            )
        }

    }

    data class Template(val templates: List<AndroidProjectTemplate.TemplateData>)
}