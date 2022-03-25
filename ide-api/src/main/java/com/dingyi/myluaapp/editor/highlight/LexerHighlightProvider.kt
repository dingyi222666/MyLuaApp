package com.dingyi.myluaapp.editor.highlight

import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Span
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.ContentReference
import kotlinx.coroutines.*

abstract class LexerHighlightProvider : HighlightProvider() {

    private val textContentTmp = StringBuilder()


    override suspend fun runHighlighting(
        ref: ContentReference?,
        data: IncrementalEditContent,
        delegate: Delegate
    ) {

        val styles = Styles()
        val builder = MappedSpans.Builder()
        fillContent(textContentTmp, ref)
        highlighting(textContentTmp, builder, styles, delegate)
        styles.spans = builder.build()
        updateStyle(styles)


    }



}