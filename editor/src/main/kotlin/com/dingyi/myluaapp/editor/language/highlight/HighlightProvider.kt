package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
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

    private val superJob = SupervisorJob()

    protected val coroutineScope = CoroutineScope(Dispatchers.IO + superJob)

    private val runJobList = mutableListOf<Job>()

    override fun setReceiver(receiver: StyleReceiver?) {

        this.receiver = receiver
    }


    fun getData(): IncrementalEditContent? {
        return data
    }

    fun postAsyncTask(block: suspend CoroutineScope.()->Unit):Job {
        val runJob = coroutineScope.launch(start = CoroutineStart.LAZY) {
            block()
        }
        runJobList.add(runJob)
        return runJob
    }

    private fun cancelAllTask() {
        superJob.cancel()
        coroutineScope.cancel()
    }

    private fun cancelRunTask() {
        runJobList.removeAll {
            it.cancel()
            true
        }
    }

    suspend fun updateStyle(styles: Styles) = withContext(Dispatchers.Main) {
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
        runHighlighting(ref,data)
    }



    abstract fun runHighlighting(ref: ContentReference?, data: IncrementalEditContent?)

    override fun destroy() {
        data = null
        receiver = null
        cancelAllTask()
    }

    /**
     * Default highlight method,must run in background thread
     */
    abstract suspend fun highlighting(
        text: CharSequence,
        builder: MappedSpans.Builder,
        styles: Styles,
        delegate: Delegate
    )

    interface Delegate {
        fun isCancelled(): Boolean
    }



}