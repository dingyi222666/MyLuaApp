package com.dingyi.myluaapp.editor.language.highlight.textmate.language

import android.os.Bundle
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.editor.language.highlight.HighlightProvider

import com.dingyi.myluaapp.editor.language.highlight.textmate.highlight.TextMateHighlightProvider

import io.github.rosemoe.sora.lang.analysis.AnalyzeManager
import io.github.rosemoe.sora.lang.completion.CompletionPublisher
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandler
import io.github.rosemoe.sora.langs.textmate.theme.TextMateColorScheme
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.SymbolPairMatch

class TextMateLanguage: Language() {

    override fun getName(): String {
        return "TextMate"
    }

    override fun getHighlightProvider(): HighlightProvider {
        return TextMateHighlightProvider(this)
    }



    override fun requireAutoComplete(
        content: ContentReference,
        position: CharPosition,
        publisher: CompletionPublisher,
        extraArguments: Bundle
    ) {
        //

    }


    override fun destroy() {
        currentHighlightProvider?.destroy()
    }




}