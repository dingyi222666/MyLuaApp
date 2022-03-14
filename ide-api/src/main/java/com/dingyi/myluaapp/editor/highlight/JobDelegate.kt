package com.dingyi.myluaapp.editor.highlight

import kotlinx.coroutines.Job
import java.util.concurrent.Future

class JobDelegate: HighlightProvider.Delegate {

    private var job: Job? = null

    fun setJob(job: Job) {
        this.job = job
    }

    override fun isCancelled(): Boolean {
        val isCompleted = job?.isCompleted

        return (isCompleted ?: false)
    }
}