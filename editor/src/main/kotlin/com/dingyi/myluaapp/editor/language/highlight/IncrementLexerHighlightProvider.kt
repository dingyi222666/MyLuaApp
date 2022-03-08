package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
import android.util.Log
import io.github.rosemoe.sora.lang.styling.*
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*
import java.util.Collections.synchronizedList

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

abstract class IncrementLexerHighlightProvider<T> : HighlightProvider() {


    private val lineStates = synchronizedList(mutableListOf<LineTokenizeResult<T>>())
    private val styles = Styles()

    private var shadowContent: Content? = Content()


    private fun requireContent(): Content {
        return shadowContent ?: error("")
    }



    /**
     * Compute code blocks
     * @param text The text. can be safely accessed.
     */
    abstract fun computeBlocks(
        text: CharSequence,
        styles: Styles,
        delegate: Delegate
    ): List<CodeBlock?>?


    abstract fun tokenizeLine(
        lineString: CharSequence,
        line: Int,
        tokenizeResult: LineTokenizeResult<T>?
    ): LineTokenizeResult<T>


    private fun checkDelegate(delegate: Delegate) {
        if (delegate.isCancelled()) {
            error("stop highlighting")
        }
    }

    override fun runHighlighting(
        ref: ContentReference?,
        delegate: Delegate
    ) {


        runComputeBlock()
        processContent()

        val spans = styles.spans ?: LockedSpans()

        styles.spans = spans
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

        styles.spans = spans

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


    final override fun highlighting(
        text: CharSequence,
        builder: MappedSpans.Builder,
        styles: Styles,
        delegate: Delegate
    ) {
        error("The increment highlight provider no support this method")
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


    private class LockedSpans : Spans {

        private val lock: Lock
        private val lines: MutableList<Line>

        override fun adjustOnDelete(start: CharPosition, end: CharPosition) {}
        override fun adjustOnInsert(start: CharPosition, end: CharPosition) {}

        override fun getLineCount(): Int {
            return lines.size
        }

        override fun read(): Spans.Reader {
            return ReaderImpl()
        }

        override fun modify(): Spans.Modifier {
            return ModifierImpl()
        }

        override fun supportsModify(): Boolean {
            return true
        }

        private class Line(var spans: List<Span>) {
            var lock = ReentrantLock()
        }

        private inner class ReaderImpl : Spans.Reader {
            private var line: Line? = null
            override fun moveToLine(line: Int) {
                if (line < 0) {
                    this.line?.lock?.unlock()
                    this.line = null
                } else if (line >= lines.size) {
                    this.line?.lock?.unlock()
                    this.line = null
                } else {

                    this.line?.lock?.unlock()
                    var locked = false
                    try {
                        locked = lock.tryLock(1, TimeUnit.MILLISECONDS)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if (locked) {
                        try {
                            val obj = lines[line]
                            if (obj.lock.tryLock()) {
                                this.line = obj
                            } else {
                                this.line = null
                            }
                        } finally {
                            lock.unlock()
                        }
                    } else {
                        this.line = null
                    }
                }
            }

            override fun getSpanCount(): Int {
                return line?.spans?.size ?: 1
            }

            override fun getSpanAt(index: Int): Span {
                return line?.spans?.getOrNull(index) ?: Span.obtain(
                    0,
                    EditorColorScheme.TEXT_NORMAL.toLong()
                )
            }

            override fun getSpansOnLine(line: Int): List<Span> {
                val spans = ArrayList<Span>()
                var locked = false
                try {
                    locked = lock.tryLock(1, TimeUnit.MILLISECONDS)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (locked) {
                    var obj: Line? = null
                    try {
                        if (line < lines.size) {
                            obj = lines[line]
                        }
                    } finally {
                        lock.unlock()
                    }
                    if (obj != null && obj.lock.tryLock()) {
                        return try {
                            Collections.unmodifiableList(obj.spans)
                        } finally {
                            obj.lock.unlock()
                        }
                    } else {
                        spans.add(getSpanAt(0))
                    }
                } else {
                    spans.add(getSpanAt(0))
                }
                return spans
            }
        }

        private inner class ModifierImpl : Spans.Modifier {
            override fun setSpansOnLine(line: Int, spans: List<Span>) {
                lock.lock()
                try {
                    while (lines.size <= line) {
                        val list = ArrayList<Span>()
                        list.add(Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong()))
                        lines.add(Line(list))
                    }
                    lines[line].spans = spans
                } finally {
                    lock.unlock()
                }
            }

            override fun addLineAt(line: Int, spans: List<Span>) {
                lock.lock()
                try {

                    lines.add(line, Line(spans))

                } finally {
                    lock.unlock()
                }
            }

            override fun deleteLineAt(line: Int) {
                lock.lock()
                try {
                    val obj = lines[line]
                    obj.lock.lock()
                    try {
                        lines.removeAt(line)
                    } finally {
                        obj.lock.unlock()
                    }
                } finally {
                    lock.unlock()
                }
            }
        }

        init {
            lines = ArrayList(128)
            lock = ReentrantLock()
        }
    }

    private fun runComputeBlock() {
        val delegate = JobDelegate()

        coroutine?.launch(start = CoroutineStart.LAZY, context = Dispatchers.IO) {
            Log.v("IncrementHighlightProvider", "Start ComputeBlock")
            try {
                val codeBlock = computeBlocks(shadowContent?.toString() ?: "", styles, delegate)
                styles.blocks = codeBlock
                updateStyle(styles)
            } catch (e: Exception) {
                Log.e(
                    "IncrementHighlightProvider",
                    "Unexpected exception is thrown in the thread.",
                    e
                )
            } finally {
                Log.v("IncrementHighlightProvider", "Complete ComputeBlock")
            }
        }?.let { job ->
            runTaskList.add(job)
            delegate.setJob(job)
            job.start()
        }
    }

    private fun processContent() {
        getData()?.let { incrementalEditContent ->
            when (incrementalEditContent.actionType) {
                IncrementalEditContent.TYPE.DELETE -> {
                    requireContent()
                        .delete(
                            incrementalEditContent.startPosition.line,
                            incrementalEditContent.startPosition.column,
                            incrementalEditContent.endPosition.line,
                            incrementalEditContent.endPosition.column
                        )
                }
                IncrementalEditContent.TYPE.INSERT -> {
                    requireContent()
                        .insert(
                            incrementalEditContent.startPosition.line, incrementalEditContent
                                .startPosition.column, incrementalEditContent.actionContent
                        )
                }
                else -> {

                }
            }
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

        styles.spans = null

        shadowContent = null

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