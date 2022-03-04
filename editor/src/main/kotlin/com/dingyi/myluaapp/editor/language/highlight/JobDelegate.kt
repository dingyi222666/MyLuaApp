package com.dingyi.myluaapp.editor.language.highlight

import kotlinx.coroutines.Job
import java.util.concurrent.Future

class JobDelegate:HighlightProvider.Delegate {

    private var job: Job? = null

    fun setFuture(job: Job) {
        this.job = job
    }

    override fun isCancelled(): Boolean {
        val isCompleted = job?.isCompleted

        return (isCompleted ?: false)
    }
}