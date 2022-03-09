package com.dingyi.myluaapp.editor.highlight

import android.os.Bundle
import android.util.Log
import io.github.rosemoe.sora.lang.styling.*
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.Collections.synchronizedList

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Increment highlight provider for state,use state for increment
 */
abstract class IncrementStateHighlightProvider<T> : IncrementHighlightProvider() {


    private val lineStates = synchronizedList(mutableListOf<LineTokenizeResult<T>>())



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


    private fun doInsertHighlight(delegate: Delegate) {

        val data = requireData()
        val startLine = data.startPosition.line
        val endLine = data.endPosition.line

        val modifySpan = styles.spans.modify()

        //insert and update state
        for (line in startLine..endLine) {

                //last state
                val lastState = lineStates

                    .getOrNull(line - 1)

                //result
                val tokenizeResult = tokenizeLine(requireContent().getLine(line), line, lastState)

                //if line == startLine,the line is contains
                if (line == startLine) {

                    modifySpan
                        .setSpansOnLine(line, tokenizeResult.clearSpans())

                    lineStates[line] = tokenizeResult

                } else {
                    modifySpan
                        .addLineAt(line, tokenizeResult.clearSpans())

                    lineStates
                        .add(line, tokenizeResult)
                }


            checkDelegate(delegate)
        }

        //update for all

        for (line in endLine + 1 until requireContent().lineCount) {

                val lastState = lineStates
                    .getOrNull(line - 1)

                val oldState = lineStates
                    .getOrNull(line)

                val tokenizeResult = tokenizeLine(
                    requireContent()
                        .getLine(line), line, lastState
                )

                if (oldState == tokenizeResult) {
                    break
                } else {

                    modifySpan
                        .setSpansOnLine(line, tokenizeResult.clearSpans())

                    lineStates[line] = tokenizeResult


                }



            checkDelegate(delegate)
        }

    }

    private fun doDeleteHighlight(delegate: Delegate) {


        val data = requireData()
        val startLine = data.startPosition.line
        val endLine = data.endPosition.line

        val modifySpan = styles.spans.modify()

        //delete span and state
        var line = startLine + 1

        while (line <= endLine) {

            if (startLine == endLine) {
                break
            }

            modifySpan
                .deleteLineAt(startLine + 1)

            lineStates.removeAt(startLine + 1)

            checkDelegate(delegate)
            line ++
        }


        //update span


        for (line in startLine until requireContent().lineCount) {
            val lastState = lineStates
                .getOrNull(line - 1)

            val oldState = lineStates
                .get(line)

            val tokenizeResult = tokenizeLine(
                requireContent()
                    .getLine(line),line, lastState
            )

            if (oldState == tokenizeResult) {
                break
            } else {

                modifySpan
                    .setSpansOnLine(line, tokenizeResult.clearSpans())

                lineStates.set(line, tokenizeResult)

            }

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


    class LineTokenizeResult<T>(
        var data: T,
        var spans: List<Span>?
    ) {


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