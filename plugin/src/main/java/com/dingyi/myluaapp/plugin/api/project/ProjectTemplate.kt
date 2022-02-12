package com.dingyi.myluaapp.plugin.api.project

import java.io.File
import java.nio.file.Path

interface ProjectTemplate {

    val name:String

    val path:String

    fun create(projectPath: File, packageName:String, name:String)
}