package com.dingyi.myluaapp.plugin.api.editor

import com.dingyi.myluaapp.plugin.api.project.Project
import java.io.File

interface EditorService {

    fun getCurrentEditor(): Editor

    fun getAllEditor():List<Editor>


    fun openEditor(editorPath:File):Editor

    fun closeEditor(editor: Editor)

    //Clear All Editor
    fun saveEditorServiceState()

    fun loadEditorServiceState(project: Project)

}