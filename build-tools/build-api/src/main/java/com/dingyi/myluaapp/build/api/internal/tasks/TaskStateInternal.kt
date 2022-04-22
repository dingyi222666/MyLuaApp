package com.dingyi.myluaapp.build.api.internal.tasks

import com.dingyi.myluaapp.build.api.tasks.TaskState


class TaskStateInternal : TaskState {

    var isExecuting = false
    var isActionable = true
    var isDidWork = false
    private var failure: RuntimeException? = null
    private var outcome: TaskExecutionOutcome? = null
    var isExecuted = false



    override fun getExecuted(): Boolean {
        return outcome != null
    }

    fun isConfigurable(): Boolean {
        return !getExecuted() && !isExecuted
    }



    fun setOutcome(outcome: TaskExecutionOutcome) {
        this.outcome = outcome
    }

    /**
     * Marks this task as executed with the given failure. This method can be called at most once.
     */
    fun setOutcome(failure: RuntimeException) {
        outcome = TaskExecutionOutcome.EXECUTED
        this.failure = failure
    }


    override fun getFailure(): Throwable? {
        return failure
    }

    override fun rethrowFailure() {
        if (failure != null) {
            throw failure as RuntimeException
        }
    }



    override fun getSkipped():Boolean {
        return  outcome?.isSkipped ?: false
    }

   override fun getSkipMessage():String {
        return  outcome?.message ?:""
    }

    override fun getUpToDate():Boolean {
        return outcome?.isUpToDate?: false
    }


    override fun getDidWork(): Boolean {
        return isDidWork
    }


    fun getExecuting(): Boolean {
        return isExecuting
    }

    @JvmName("setExecuting1")
    fun setExecuting(executing: Boolean) {
        isExecuting = executing
    }


    override fun getNoSource(): Boolean {
        return outcome == TaskExecutionOutcome.NO_SOURCE;
    }
}
