package com.dingyi.myluaapp.editor.highlight

import android.os.Bundle
import io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager
import io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
import io.github.rosemoe.sora.lang.styling.*
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.util.*
import java.util.Collections.synchronizedList

/**
 * Increment highlight provider for state,use state for increment
 */
abstract class IncrementStateHighlightProvider<T> : IncrementHighlightProvider() {


    private val lineStates = synchronizedList(mutableListOf<LineTokenizeResult<T>>())

    /**
     * tokenize line and return tokenize result
     * @param lineString line string
     * @param line line number
     * @param tokenizeResult last line tokenize result
     * @return tokenize result
     */
    abstract fun tokenizeLine(
        lineString: CharSequence,
        line: Int,
        tokenizeResult: LineTokenizeResult<T>?
    ): LineTokenizeResult<T>



    override fun runHighlighting(
        ref: ContentReference?,
        delegate: Delegate
    ) {


        super.runHighlighting(ref,delegate)

        when (requireData().actionType) {
            IncrementalEditContent.TYPE.INSERT -> {
                doInsertHighlight(delegate)
            }
            IncrementalEditContent.TYPE.DELETE -> {
                doDeleteHighlight(delegate)
            }
            IncrementalEditContent.TYPE.EMPTY -> {
                doFullHighlight(delegate)
            }
        }

        updateStyle(styles)
    }


    private fun doDeleteHighlight(delegate: Delegate) {

        val data = requireData()
        val startLine = data.startPosition.line
        val endLine = data.endPosition.line


        if (endLine >= startLine + 1) {
            lineStates.subList(startLine + 1, endLine + 1).clear()
        }

        val mdf = styles.spans.modify()

        var line = startLine + 1
        while (line <= endLine) {
            mdf.deleteLineAt(startLine + 1)
            ++line
        }

        var res = lineStates
            .getOrNull(startLine - 1)
        line = startLine
        while (line < requireContent().lineCount) {
            res = tokenizeLine(
                requireContent()
                    .getLine(line), line, res
            )
            mdf.setSpansOnLine(
                line,
                res.clearSpans()
            )
            if (lineStates.set(line, res) == res) {
                break
            }
            ++line
            checkDelegate(delegate)
        }


    }

    private fun doInsertHighlight(delegate: Delegate) {

        val data = requireData()
        val startLine = data.startPosition.line
        val endLine = data.endPosition.line
        var line = startLine
        val spans = styles.spans.modify()

        var res = lineStates
            .getOrNull(startLine - 1)

        while (line <= endLine) {
            res = tokenizeLine(
                requireContent()
                    .getLine(line), line, res
            )
            if (line == startLine) {
                spans.setSpansOnLine(
                    line,
                    res.clearSpans()
                )
                lineStates[line] = res
            } else {
                spans.addLineAt(
                    line,
                    res.clearSpans()
                )
                lineStates.add(line, res)
            }

            ++line
            checkDelegate(delegate)
        }


        while (line < requireContent().lineCount) {
            res = tokenizeLine(
                requireContent()
                    .getLine(line), line, res
            )
            if (res == lineStates[line]) {
                break
            }
            spans.setSpansOnLine(
                line,
                res.clearSpans()
            )
            lineStates[line] = res
            ++line
            checkDelegate(delegate)
        }

    }



    private fun doFullHighlight(delegate: Delegate) {
        val modifySpan = styles.spans.modify()
        for (line in 0 until requireContent().lineCount) {
            checkDelegate(delegate)
            val lineText = requireContent().getLine(line)
            val lastState = lineStates.getOrNull(line - 1)
            val lineState = tokenizeLine(lineText, line, lastState)
            modifySpan.addLineAt(line, lineState.clearSpans())
            lineStates.add(line, lineState)
        }
    }





    final override fun reset(content: ContentReference, extraArguments: Bundle) {
        shadowContent = content.reference.copyText(false)
        shadowContent?.apply {
            isUndoEnabled = false
        }

        super.reset(content, extraArguments)

    }

    final override fun destroy() {

        lineStates.clear()

        System.gc()
    }


    /**
     * A LineState and spans data class for a line.
     */
    class LineTokenizeResult<T>(
        var data: T,
        var spans: List<Span>?
    ) {


        /**
         * Clear and return the spans.
         * @return The spans.
         */
        fun clearSpans(): List<Span> {
            var result = spans ?: listOf(
                Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong())
            )

            if (result.isEmpty()) {
                result = listOf(
                    Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong())
                )
            }

            this.spans = null

            return result
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LineTokenizeResult<*>

            if (data != other.data) return false

            return true
        }

        override fun hashCode(): Int {
            return data?.hashCode() ?: 0
        }
    }


}