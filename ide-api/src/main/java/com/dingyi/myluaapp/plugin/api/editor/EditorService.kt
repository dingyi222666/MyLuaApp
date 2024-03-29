package com.dingyi.myluaapp.plugin.api.editor

import com.dingyi.myluaapp.plugin.api.project.Project
import java.io.File

interface EditorService {

    fun getCurrentEditor(): Editor?

    fun getAllEditor():List<Editor>

    fun addEditorProvider(editorProvider: EditorProvider)

    fun refreshEditorServiceState()

    fun openEditor(editorPath:File): Editor?

    fun closeEditor(editor: Editor)

    fun renameEditor(renamePath:File,targetPath:File)

    fun clearAllEditor()

    //Clear All Editor
    fun saveEditorServiceState()

    fun loadEditorServiceState(project: Project)

    fun closeOtherEditor(editor: Editor)

    fun getEditor(filePath:File): Editor?

    fun getSupportLanguages(): List<String>

    fun addSupportLanguages(vararg language:String)
    fun closeAllEditor()

    fun setCurrentEditor(file: File)

    fun closeEditor(editor: File)

}