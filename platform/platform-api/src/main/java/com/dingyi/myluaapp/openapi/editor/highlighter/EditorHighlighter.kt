package com.dingyi.myluaapp.openapi.editor.highlighter

import com.dingyi.myluaapp.openapi.editor.colors.EditorColorsScheme
import com.dingyi.myluaapp.openapi.editor.event.DocumentListener

interface EditorHighlighter : DocumentListener {

    fun createIterator(): HighlighterIterator
    fun setText(text: CharSequence) {}
    fun setEditor(editor: HighlighterClient)
    fun setColorScheme(scheme: EditorColorsScheme) {}


}
