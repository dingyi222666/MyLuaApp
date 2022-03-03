package com.dingyi.myluaapp.plugin.runtime.editor

import com.dingyi.myluaapp.editor.language.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.language.highlight.IncrementalEditContent
import com.dingyi.myluaapp.plugin.api.editor.language.Language
import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.ContentReference

class EmptyLanguage : Language() {
    override fun getName(): String {
        return "empty"
    }

    override fun getHighlightProvider(): HighlightProvider {
        return EmptyHighlightProvider()
    }

    class EmptyHighlightProvider : HighlightProvider() {
        override fun runHighlighting(ref: ContentReference?, data: IncrementalEditContent?) {
            //
        }

        override suspend fun highlighting(
            text: CharSequence,
            builder: MappedSpans.Builder,
            styles: Styles,
            delegate: Delegate
        ) {
            //
        }

    }
}