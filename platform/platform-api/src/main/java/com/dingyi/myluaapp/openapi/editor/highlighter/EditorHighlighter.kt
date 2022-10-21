package com.dingyi.myluaapp.openapi.editor.highlighter

import com.dingyi.myluaapp.openapi.editor.colors.EditorColorsScheme
import com.dingyi.myluaapp.openapi.editor.event.DocumentListener

/**
 * Text highlighter for editors,
 * note that it does not support incremental drawing in the conventional sense because of the native editor support,
 * but you can store the last successfully parsed token and provide it to the editor
 */
interface EditorHighlighter : DocumentListener {

    fun createIterator(): HighlighterIterator
    fun setText(text: CharSequence) {}
    fun setEditor(editor: HighlighterClient)
    fun setColorScheme(scheme: EditorColorsScheme) {}


}
