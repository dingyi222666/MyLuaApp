package com.dingyi.myluaapp.editor.language

import com.dingyi.myluaapp.editor.complete.AutoCompleteProvider
import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.plugin.api.editor.Editor


abstract class WrapperLanguage(language: Language,private val editor:Editor):Language() {

    private val wrapperLanguage =language




    override fun getName(): String {
        return wrapperLanguage.getName()
    }

    override fun getHighlightProvider(): HighlightProvider {
       return wrapperLanguage.getHighlightProvider()
    }

    override fun getAutoCompleteProvider(): AutoCompleteProvider {
        return wrapperLanguage.getAutoCompleteProvider()
    }
}