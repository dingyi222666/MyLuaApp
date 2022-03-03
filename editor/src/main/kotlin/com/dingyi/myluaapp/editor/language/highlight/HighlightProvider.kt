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

abstract class HighlightProvider : AnalyzeManager {

    private var ref: ContentReference? = null

    private var receiver: StyleReceiver? = null

    private var data: IncrementalEditContent? = null

    private val superJob = Job()

    protected val coroutineScope = CoroutineScope(Dispatchers.IO + superJob)

    protected val runJobList = mutableListOf<Job>()

    override fun setReceiver(receiver: StyleReceiver?) {
        this.receiver = receiver
    }


    fun getData(): IncrementalEditContent? {
        return data
    }


    private fun cancelAllTask() {
        superJob.cancel()
        coroutineScope.cancel()
    }

    private fun cancelRunTask() {
        runJobList.removeAll {
            it.cancel()
            it.cancelChildren()
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
        val delegate = JobDelegate()

        val job = coroutineScope.launch(start = CoroutineStart.LAZY) {
            runHighlighting(ref, data, delegate)
        }

        runJobList.add(job)
        delegate.setJob(job)
        job.start()
    }


    abstract suspend fun runHighlighting(
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