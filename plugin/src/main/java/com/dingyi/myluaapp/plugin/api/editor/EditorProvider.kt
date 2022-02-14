package com.dingyi.myluaapp.plugin.api.editor

import java.io.File

interface EditorProvider {

    fun createEditor(editorPath:File):Editor<*>?
}