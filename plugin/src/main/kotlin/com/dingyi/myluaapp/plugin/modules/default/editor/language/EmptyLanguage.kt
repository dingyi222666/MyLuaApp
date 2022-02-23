package com.dingyi.myluaapp.plugin.modules.default.editor.language

import com.dingyi.myluaapp.plugin.api.editor.language.HighlightProvider
import com.dingyi.myluaapp.plugin.api.editor.language.Language

class EmptyLanguage:Language {
    override fun getName(): String {
        return "empty"
    }

    override fun getHighlightProvider(): HighlightProvider {
        TODO("Not yet implemented")
    }
}