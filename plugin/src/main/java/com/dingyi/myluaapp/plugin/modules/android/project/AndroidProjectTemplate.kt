package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.plugin.api.project.ProjectTemplate
import java.io.File

class AndroidProjectTemplate(
    private val templateData: TemplateData
): ProjectTemplate {



    data class TemplateData(
        var path:String,
        val name:Map<String,String>,
        val replaces:List<String>
    )

    private fun getDefaultName():String {
        return templateData.name["default"].toString()
    }

    override val name: String
        get() = getDefaultName()
    override val path: String
        get() = templateData.path

    override fun create(projectPath: File, packageName: String, name: String) {
        TODO("Not yet implemented")
    }
}