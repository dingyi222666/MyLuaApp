package com.dingyi.myluaapp.plugin.api.editor

import android.view.View
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.plugin.api.project.Project
import io.github.rosemoe.sora.widget.CodeEditor
import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.io.File

interface Editor {

    /**
     * Get current editor document.
     */
    fun getText(): CharSequence?


    fun getCurrentLine():Int

    fun getCurrentColumn():Int

    fun setText(charSequence: CharSequence)

    fun appendText(charSequence: CharSequence)

    fun getId():Int

    fun saveState(): EditorState

    fun save()

    suspend fun read()

    fun setColorScheme(scheme:EditorColorScheme )

    fun getColorScheme():EditorColorScheme

    fun binCurrentView(r:CodeEditor)

    fun restoreState(editorState: EditorState)

    fun getFile():File

    fun isModified():Boolean

    fun getLanguage(): Language

    fun setLanguage(language: Language)

    fun addEditorListener(listener: EditorListener)

    fun removeEditorListener(listener: EditorListener)

    fun getCurrentView(): View?

    fun getProject():Project

    fun undo()
    fun redo()
    fun close()

    /**
     * Format the current code
     */
    fun format()

}