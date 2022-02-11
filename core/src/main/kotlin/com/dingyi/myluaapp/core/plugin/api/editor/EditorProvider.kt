package com.dingyi.myluaapp.core.plugin.api.editor

import android.app.Activity

interface EditorProvider {

    fun createEditor(editorPath:String,context:Activity):Editor?

}