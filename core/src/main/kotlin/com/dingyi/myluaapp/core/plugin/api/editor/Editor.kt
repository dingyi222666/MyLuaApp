package com.dingyi.myluaapp.core.plugin.api.editor

import android.view.View
import java.io.File

interface Editor {

    fun getText():CharSequence

    fun getCurrentLine():Int

    fun getCurrentColumn():Int

    fun setText(charSequence: CharSequence)

    fun appendText(charSequence: CharSequence)

    fun getHighlightProvider()

    fun getId():Int

    fun saveState():EditorState

    fun restoreState(editorState: EditorState)

    fun getFile():File

    fun isModify():Boolean

    fun getLanguage():String

    fun getCurrentView():View

}