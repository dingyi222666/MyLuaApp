package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
import android.util.Log
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

abstract class IncrementLexerHighlightProvider : HighlightProvider() {


    private var lexerState: LexerState<*>? = null


    private val styles = Styles()

    private var shadowContent: Content? = Content()

    fun <T : State> createLexerState(): LexerState<T>? {
        lexerState = LexerState<T>()
        return lexerState as LexerState<T>?
    }

    fun <T : State> requireLexerState(): LexerState<T> {
        return lexerState as LexerState<T>? ?: error("")
    }

    fun requireContent(): Content {
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


    abstract fun lexerForLine(line: Int, lineString: CharSequence): List<Span>


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

        processContent()
        runComputeBlock()

        val spans = styles.spans ?: LockedSpans()

        checkNotNull(ref)

        styles.spans = spans
        when (data?.actionType) {
            IncrementalEditContent.TYPE.INSERT -> {
                val modifySpan = styles.spans.modify()
                val readerSpan = spans.read()
                val moveLine = data.endPosition.line - data.startPosition.line

                val startLine = data.startPosition.line

                //移动旧的span到新的span
                for (i in data.startPosition.line until ref.lineCount) {
                    val oldLine = readerSpan.getSpansOnLine(i)
                    modifySpan
                        .setSpansOnLine(i + moveLine, oldLine)

                    requireLexerState<State>()
                        .moveState(i, i + moveLine)

                    checkDelegate(delegate)
                }

                //更新插入的line

                for (line in data.startPosition.line..data.endPosition.line) {

                    val lineSpans =
                        lexerForLine(line, requireContent().getLine(line)).toMutableList()

                    if (lineSpans.isEmpty()) {
                        lineSpans.add(Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong()))
                    }

                    if (line == startLine) {
                        modifySpan
                            .setSpansOnLine(line, lineSpans)
                    } else {
                        modifySpan.addLineAt(
                            line,
                            lineSpans
                        )
                    }

                    checkDelegate(delegate)
                }

                //向下更新状态 直到旧的状态和新的状态相同
                for (i in data.endPosition.line until ref.lineCount) {
                    val oldState = requireLexerState<State>()
                        .getStateForLine(i)

                    val lineSpans = lexerForLine(i, requireContent().getLine(i)).toMutableList()
                    if (lineSpans.isEmpty()) {
                        lineSpans.add(Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong()))
                    }

                    val newState = requireLexerState<State>()
                        .getStateForLine(i)

                    if (oldState != newState) {
                        modifySpan.setSpansOnLine(i, lineSpans)
                    } else {
                        break
                    }
                    checkDelegate(delegate)
                }

            }
            IncrementalEditContent.TYPE.DELETE -> {

                val modifySpan = styles.spans.modify()

                //删除对应的span行


                //重新从头开始渲染

                for (i in data.startPosition.line..data.endPosition.line) {
                    val oldState = requireLexerState<State>()
                        .getStateForLine(i)

                    val lineSpans = lexerForLine(i, requireContent().getLine(i)).toMutableList()
                    if (lineSpans.isEmpty()) {
                        lineSpans.add(Span.obtain(0, EditorColorScheme.TEXT_NORMAL.toLong()))
                    }

                    val newState = requireLexerState<State>()
                        .getStateForLine(i)

                    if (oldState != newState) {
                        modifySpan.setSpansOnLine(i, lineSpans)
                    } else {
                        break
                    }
                    checkDelegate(delegate)
                }



            }
            else -> {
                doFullHighlight(delegate)
            }
        }

        styles.spans = spans

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
            if (delegate.isCancelled()) {
                return
            }
            val lineText = requireContent().getLine(line)
            val lineSpans = lexerForLine(line, lineText).toMutableList()
            if (lineSpans.isEmpty()) {
                lineSpans.add(Span.obtain(0,EditorColorScheme.TEXT_NORMAL.toLong()))
            }
            modifySpan.addLineAt(line, lineSpans)

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
        shadowContent = content.reference.copyText(true)

        super.reset(content, extraArguments)
        init()
    }

    final override fun destroy() {
        lexerState?.clear()
        styles.spans = null

        shadowContent = null
        lexerState = null
        System.gc()
    }

    abstract fun init()

    interface State {
        fun notifyMoveLine(fromLine: Int, toLine: Int)
    }

    class LexerState<T : State> {
        private val mapData = mutableMapOf<Int, T>()


        fun getStateForLine(line: Int): T? {
            return mapData[line]
        }

        fun clear() {
            mapData.clear()
        }

        fun updateStateForLine(line: Int, data: T) {
            mapData[line] = data
        }

        fun removeStateForLine(line: Int): Boolean {
            return mapData.remove(line) != null
        }

        fun removeStateForIterable(indices: IntProgression) {
            indices.forEach {
                mapData.remove(it)
            }
        }

        fun moveState(fromLine: Int, toLine: Int) {
            val fromState = mapData[fromLine]
            fromState?.notifyMoveLine(fromLine, toLine)
            mapData[toLine] = fromState as T
        }

        fun getStateForIterable(indices: IntProgression): List<T> {
            return mapData.filter {
                indices.contains(it.key)
            }.map { it.value }
        }
    }

}