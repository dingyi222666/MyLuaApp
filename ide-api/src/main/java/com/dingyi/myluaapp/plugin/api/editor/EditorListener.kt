package com.dingyi.myluaapp.plugin.api.editor

import io.github.rosemoe.sora.event.ContentChangeEvent

interface EditorListener {

    fun onEditorChange(currentEditor:Editor,event: ContentChangeEvent)

    fun onEditorClose()

    fun onEditorSave()

    fun onEditorOpen()
}