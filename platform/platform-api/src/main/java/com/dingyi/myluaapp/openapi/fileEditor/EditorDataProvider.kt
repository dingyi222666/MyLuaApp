package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.editor.Caret
import com.dingyi.myluaapp.openapi.editor.Editor

interface EditorDataProvider {

    fun getData(dataId: String, e: Editor, caret: Caret): Any?
}