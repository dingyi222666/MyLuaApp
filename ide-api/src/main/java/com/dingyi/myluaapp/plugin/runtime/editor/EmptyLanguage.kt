package com.dingyi.myluaapp.plugin.runtime.editor

import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.highlight.LexerHighlightProvider
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.editor.complete.AutoCompleteProvider
import io.github.rosemoe.sora.lang.completion.CompletionPublisher

import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference


open class EmptyLanguage : Language() {
    override fun getName(): String {
        return "empty"
    }

    override fun getHighlightProvider() = EmptyHighlightProvider()

    override fun getAutoCompleteProvider() = EmptyAutoCompleteProvider()

    class EmptyHighlightProvider : LexerHighlightProvider() {


        override fun highlighting(
            text: CharSequence,
            builder: MappedSpans.Builder,
            styles: Styles,
            delegate: Delegate
        ) {

        }


    }



    class EmptyAutoCompleteProvider(): AutoCompleteProvider {
        override suspend fun requireAutoComplete(
            content: ContentReference,
            position: CharPosition,
            publisher: CompletionPublisher
        ) {

        }

    }
}