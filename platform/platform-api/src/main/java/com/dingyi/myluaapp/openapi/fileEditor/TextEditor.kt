package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.editor.Editor


interface TextEditor : FileEditor {
    fun getEditor(): Editor
}
