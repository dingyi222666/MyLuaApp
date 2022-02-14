package com.dingyi.myluaapp.plugin.modules.default.editor

import android.content.Context
import android.view.View
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.editor.CodeEditor

import com.dingyi.myluaapp.plugin.api.editor.Editor

import com.dingyi.myluaapp.plugin.api.editor.language.Language
import com.dingyi.myluaapp.plugin.modules.default.editor.language.EmptyLanguage
import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
import java.io.File
import java.lang.ref.WeakReference

class Editor(
    private val context: Context,
    private val path: File,
    private val id: Int
) : Editor<EditorState> {


    private val currentContext = WeakReference(context)

    private var currentLanguage: Language = EmptyLanguage()

    private val currentEditor = CodeEditor(context)

    private var currentText: CharSequence = ""

    override fun getText(): CharSequence {
        return currentEditor.text
    }

    override fun getCurrentLine(): Int {
        return currentEditor.text.cursor.leftLine
    }

    override fun getCurrentColumn(): Int {
        return currentEditor.text.cursor.leftColumn
    }

    override fun setText(charSequence: CharSequence) {
        currentText = charSequence
        currentEditor.setText(charSequence)
    }

    override fun appendText(charSequence: CharSequence) {
        currentEditor.text.beginBatchEdit()
        currentEditor.text.insert(getCurrentLine(), getCurrentColumn(), charSequence)
        currentEditor.text.endBatchEdit()
    }


    override fun getId(): Int {
        return id
    }


    override fun saveState(): EditorState {
        return EditorState(
            path = path.path,
            line = getCurrentLine(),
            column = getCurrentColumn(),
            scrollX = currentEditor.scroller.currX,
            scrollY = currentEditor.scroller.currY,
            textSize = currentEditor.textSizePx
        )
    }

    override fun restoreState(editorState: EditorState) {
        currentEditor.apply {
            this@Editor.setText(editorState.path.toFile().readText())
            text.cursor.set(editorState.line, editorState.column)
            scroller.startScroll(
                scroller.currX,
                scroller.currY,
                editorState.scrollX - scroller.currX,
                editorState.scrollY - scroller.currY, 0
            )
            if (editorState.textSize > 0) {
                textSizePx = editorState.textSize
            }
        }
    }

    override fun getFile(): File {
        return path
    }

    override fun isModify(): Boolean {
        return currentEditor.text.length == currentText.length
    }

    override fun getLanguage(): Language {
        return currentLanguage
    }

    override fun setLanguage(language: Language) {
        currentLanguage = language
    }

    override fun getCurrentView(): View {
        return currentEditor
    }


}