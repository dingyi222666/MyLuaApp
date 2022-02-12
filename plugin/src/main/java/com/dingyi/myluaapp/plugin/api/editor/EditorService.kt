package com.dingyi.myluaapp.plugin.api.editor

import java.io.File

interface EditorService {

    fun getCurrentEditor(): Editor

    fun getAllEditor():List<Editor>

    fun getEditorProvider(): EditorProvider

    fun openEditor(editorPath:File)

    fun closeEditor(editor: Editor)

    fun saveEditorServiceState()

    fun loadEditorServiceState()

}