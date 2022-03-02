package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
import io.github.rosemoe.sora.lang.analysis.SimpleAnalyzeManager
import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference

abstract class LexerHighlightProvider : SimpleAnalyzeManager<IncrementalEditContent>() {

    private var incrementalContent:IncrementalEditContent? = IncrementalEditContent()

    private lateinit var styles: Styles

    final override fun analyze(
        text: StringBuilder,
        delegate: Delegate<IncrementalEditContent>
    ): Styles {


        val spanBuilder = MappedSpans.Builder()

        highlight(text, spanBuilder, delegate)

        delegate.setData(incrementalContent)


        styles = Styles(spanBuilder.build())

        return styles

    }

    override fun insert(start: CharPosition, end: CharPosition, insertedContent: CharSequence) {
        incrementalContent?.apply {
            actionType = IncrementalEditContent.TYPE.INSERT
            startPosition = start
            endPosition = end
            actionContent = insertedContent
        }
        super.insert(start, end, insertedContent)
    }

    override fun reset(content: ContentReference, extraArguments: Bundle) {
        super.reset(content, extraArguments)
        incrementalContent = IncrementalEditContent()
    }


    override fun delete(start: CharPosition, end: CharPosition, deletedContent: CharSequence) {

        incrementalContent?.apply {
            actionType = IncrementalEditContent.TYPE.INSERT
            startPosition = start
            endPosition = end
            actionContent = deletedContent
        }
        super.delete(start, end, deletedContent)
    }


    override fun destroy() {
        incrementalContent = null
        super.destroy()
    }

    /**
     * do highlight in it
     */
    abstract fun highlight(
        text: StringBuilder,
        spans: MappedSpans.Builder,
        delegate: Delegate<IncrementalEditContent>
    )
}