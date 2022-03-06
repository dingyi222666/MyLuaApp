package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import io.github.rosemoe.sora.lang.styling.*
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

abstract class IncrementLexerHighlightProvider<T> : HighlightProvider() {


    private val lineStates = SparseArray<LineTokenizeResult<T>>(128)

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


    abstract fun lexerForLine(
        lineString: CharSequence,
        result: LineTokenizeResult<T>?
    ): LineTokenizeResult<T>


    private fun checkDelegate(delegate: Delegate) {
        if (delegate.isCancelled()) {
            error("stop highlighting")
        }
    }

    override fun runHighlighting(
        ref: ContentReference?,
        data: IncrementalEditContent?,
        delegate: Delegate
    ) {


        runComputeBlock()

        val spans = styles.spans ?: LockedSpans()

        checkNotNull(ref)

        styles.spans = spans
        when (requireData().actionType) {
            IncrementalEditContent.TYPE.DELETE -> {

            }
            IncrementalEditContent.TYPE.INSERT -> {

            }
            IncrementalEditContent.TYPE.EMPTY -> {
                doFullHighlight(delegate)
            }
        }

        styles.spans = spans
        processContent()
        updateStyle(styles)
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
            val lastState = lineStates.get(line - 1)
            val lineState = lexerForLine(lineText, lastState)
            val lineSpans = lineState.spans?.toMutableList() ?: mutableListOf()
            if (lineSpans.isEmpty()) {
                lineSpans.add(Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong()))
            }
            modifySpan.addLineAt(line, lineSpans)
            //recycler span
            lineState.spans = null
            lineStates.put(line, lineState)
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
            delegate.setFuture(job)
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