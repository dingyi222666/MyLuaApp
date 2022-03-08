package com.dingyi.myluaapp.editor.language

import com.dingyi.myluaapp.editor.language.highlight.HighlightProvider
import io.github.rosemoe.sora.lang.EmptyLanguage

import io.github.rosemoe.sora.lang.analysis.AnalyzeManager
import io.github.rosemoe.sora.util.MyCharacter

abstract class Language : EmptyLanguage() {

    protected var currentHighlightProvider: HighlightProvider? = null

    abstract fun getName(): String

    abstract fun getHighlightProvider(): HighlightProvider

    final override fun getAnalyzeManager(): AnalyzeManager {
        currentHighlightProvider = currentHighlightProvider ?: getHighlightProvider()
        return currentHighlightProvider ?: getHighlightProvider()
    }

}