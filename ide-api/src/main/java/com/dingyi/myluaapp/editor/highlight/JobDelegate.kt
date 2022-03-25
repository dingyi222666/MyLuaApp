package com.dingyi.myluaapp.editor.highlight

import kotlinx.coroutines.Job

class JobDelegate: HighlightProvider.Delegate {

    private var job: Job? = null

    fun setJob(job: Job) {
        this.job = job
    }

    override fun isCancelled(): Boolean {
        return job?.isCompleted ?: false
    }
}