package com.dingyi.myluaapp.plugin.api.editor

import android.view.View
import com.dingyi.myluaapp.plugin.api.editor.language.Language
import io.github.rosemoe.sora.widget.CodeEditor
import com.dingyi.myluaapp.plugin.runtime.editor.EditorState
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.io.File

interface Editor {

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

    fun getLanguage():Language

    fun setLanguage(language: Language)


    fun getCurrentView(): View?
    fun undo()

    fun redo()

}