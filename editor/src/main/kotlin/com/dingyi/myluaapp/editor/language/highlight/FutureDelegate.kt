package com.dingyi.myluaapp.editor.language.highlight

import kotlinx.coroutines.Job
import java.util.concurrent.Future

class FutureDelegate:HighlightProvider.Delegate {

    private var future: Future<*>? = null

    fun setFuture(job:  Future<*>) {
        this.future = job
    }

    override fun isCancelled(): Boolean {
        val isCompleted = future?.isDone

        return (isCompleted ?: false)
    }
}