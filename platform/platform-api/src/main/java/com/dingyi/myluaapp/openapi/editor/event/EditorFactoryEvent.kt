package com.dingyi.myluaapp.openapi.editor.event

import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.editor.EditorFactory
import org.jetbrains.annotations.NotNull
import java.util.EventObject

class EditorFactoryEvent(editorFactory: EditorFactory, editor: Editor) :
    EventObject(editorFactory) {
    private val myEditor: Editor

    init {
        myEditor = editor
    }

    val factory: EditorFactory
        get() = getSource() as EditorFactory

    val editor: Editor
        get() = myEditor
}
