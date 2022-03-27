package com.dingyi.myluaapp.plugin.api.editor

import com.dingyi.myluaapp.plugin.api.project.Project
import java.io.File

interface EditorProvider {

    fun createEditor(project: Project,editorPath:File):Editor?
}