package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager
import io.github.rosemoe.sora.lang.analysis.StyleReceiver
import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

abstract class HighlightProvider : AnalyzeManager {

    private var ref: ContentReference? = null

    private var receiver: StyleReceiver? = null

    private var data: IncrementalEditContent? = null


    protected var threadPoolExecutor:ExecutorService? = Executors.newFixedThreadPool(3)

    protected val runTaskList = mutableListOf<Future<*>>()

    override fun setReceiver(receiver: StyleReceiver?) {
        this.receiver = receiver
    }


    fun getData(): IncrementalEditContent? {
        return data
    }


    private fun cancelAllTask() {
        cancelRunTask()
        threadPoolExecutor?.shutdown()
    }

    private fun cancelRunTask() {

        runTaskList.removeAll {
            if (!it.isDone) {
                it.cancel(true)
            }
            true
        }
    }

    @UiThread
    fun updateStyle(styles: Styles) {
        receiver?.setStyles(this@HighlightProvider, styles)
    }


    override fun insert(start: CharPosition, end: CharPosition, insertedContent: CharSequence) {
        data?.apply {
            actionType = IncrementalEditContent.TYPE.INSERT
            startPosition = start
            endPosition = end
            actionContent = insertedContent
        }

        rerun()
    }

    override fun reset(content: ContentReference, extraArguments: Bundle) {
        ref = content
        data = IncrementalEditContent()
        threadPoolExecutor = Executors.newFixedThreadPool(4)
        System.gc()
        rerun()
    }


    override fun delete(start: CharPosition, end: CharPosition, deletedContent: CharSequence) {

        data?.apply {
            actionType = IncrementalEditContent.TYPE.INSERT
            startPosition = start
            endPosition = end
            actionContent = deletedContent
        }

        rerun()

    }

    override fun rerun() {
        cancelRunTask()
        runHighlighting()
    }

    private fun runHighlighting() {
        val delegate = FutureDelegate()

        threadPoolExecutor?.submit {
            runHighlighting(ref, data, delegate)
        }?.let { job ->

            runTaskList.add(job)
            delegate.setFuture(job)
            threadPoolExecutor?.execute {
                if (!job.isDone) {
                    job.get()
                }
            }
        }
    }


    abstract fun runHighlighting(
        ref: ContentReference?,
        data: IncrementalEditContent?,
        delegate: Delegate
    )

    override fun destroy() {
        data = null
        receiver = null
        cancelAllTask()
    }

    /**
     * Default highlight method,must run in background thread
     */
    @WorkerThread
    abstract fun highlighting(
        text: CharSequence,
        builder: MappedSpans.Builder,
        styles: Styles,
        delegate: Delegate
    )

    interface Delegate {
        fun isCancelled(): Boolean
    }



}