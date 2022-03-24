package com.dingyi.myluaapp.editor.highlight

import android.os.Bundle
import android.util.Log
import com.dingyi.myluaapp.editor.highlight.HighlightProvider
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

abstract class IncrementHighlightProvider : HighlightProvider() {

    protected val styles = Styles()

    protected var shadowContent: Content? = Content()


    protected fun requireContent(): Content {
        return shadowContent ?: error("")
    }


    final override fun highlighting(
        text: CharSequence,
        builder: MappedSpans.Builder,
        styles: Styles,
        delegate: Delegate
    ) {
        error("The increment highlight provider no support this method")
    }


    override fun runHighlighting(ref: ContentReference?, delegate: Delegate) {

        processContent()

        styles.spans = styles.spans ?: LockedSpans()

        runComputeBlock()
    }

    /**
     * Compute code blocks
     * @param text The text. can be safely accessed.
     */
    abstract fun computeBlocks(
        text: Content,
        styles: Styles,
        delegate: Delegate
    ): List<CodeBlock>?


    private fun runComputeBlock() {
        val delegate = JobDelegate()

        coroutine?.launch(start = CoroutineStart.LAZY, context = Dispatchers.IO) {
            Log.v("IncrementHighlightProvider", "Start ComputeBlock")
            try {
                val codeBlock = computeBlocks(requireContent(), styles, delegate)
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


    override fun reset(content: ContentReference, extraArguments: Bundle) {
        shadowContent = content.reference.copyText(false)
        shadowContent?.apply {
            isUndoEnabled = false
        }

        super.reset(content, extraArguments)

    }

    override fun destroy() {

        styles.spans = null

        shadowContent = null

        System.gc()
    }


    protected fun checkDelegate(delegate: Delegate) {
        if (delegate.isCancelled()) {
            error("stop highlighting")
        }
    }

    class LockedSpans : Spans {

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


}