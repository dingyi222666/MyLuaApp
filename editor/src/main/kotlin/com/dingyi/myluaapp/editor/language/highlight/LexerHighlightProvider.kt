package com.dingyi.myluaapp.editor.language.highlight

import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Span
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.ContentReference
import kotlinx.coroutines.*

abstract class LexerHighlightProvider : HighlightProvider() {

    private val textContentTmp = StringBuilder()


    override suspend fun runHighlighting(
        ref: ContentReference?,
        data: IncrementalEditContent?,
        delegate: Delegate
    ) {

        val styles = Styles()
        val builder = MappedSpans.Builder()
        fillContent(textContentTmp, ref)
        highlighting(textContentTmp, builder, styles, delegate)
        styles.spans = builder.build()
        updateStyle(styles)


    }

    private fun fillContent(textContentTmp: StringBuilder, ref: ContentReference?) =

        ref?.let { ref ->
            // Collect line contents
            textContentTmp.setLength(0)
            textContentTmp.ensureCapacity(ref.length)

            var i = 0
            while (i < ref.lineCount) {
                if (i != 0) {
                    textContentTmp.append('\n')
                }
                ref.appendLineTo(textContentTmp, i)
                i++
            }
        }


}