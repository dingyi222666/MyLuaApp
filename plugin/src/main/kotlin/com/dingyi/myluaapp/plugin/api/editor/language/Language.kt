package com.dingyi.myluaapp.plugin.api.editor.language

import com.dingyi.myluaapp.editor.language.highlight.HighlightProvider
import io.github.rosemoe.sora.lang.EmptyLanguage

import io.github.rosemoe.sora.lang.analysis.AnalyzeManager

abstract class Language : EmptyLanguage() {

    private var currentHighlightProvider: HighlightProvider? = null

    abstract fun getName(): String

    abstract fun getHighlightProvider(): HighlightProvider

    final override fun getAnalyzeManager(): AnalyzeManager {
        currentHighlightProvider = currentHighlightProvider ?: getHighlightProvider()
        return currentHighlightProvider ?: getHighlightProvider()
    }
}