package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.plugin.api.project.CreateProjectProvider
import com.dingyi.myluaapp.plugin.api.project.ProjectTemplate
import com.google.gson.Gson

class AndroidCreateProjectProvider : CreateProjectProvider {

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