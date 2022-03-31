package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.file.TaskInput

interface Task {
    val name: String

    /**
     * Prepares the task for execution.
     * @return Task State
     * @see Task.State
     */
    suspend fun prepare(): State

    suspend fun run()


    fun getOutputString(module: Module, state: State?): String {
        var state = state
        if (state == State.DEFAULT) {
            state = null
        }
        return "> Task :${module.name}:$name ${state?.name ?: ""}"
    }

    /**
     * Get task input
     */
    fun getTaskInput(): TaskInput?

    /**
     * A enum of task states.
     */
    enum class State {
        /**
         * The task is up to date.
         */
        `UP-TO-DATE`,

        /**
         * The task can skip.
         */
        SKIPPED,

        /**
         * The task no input source
         */
        `NO-SOURCE`,

        /**
         * The task are increment running
         */
        INCREMENT,

        /**
         * Default state
         */
        DEFAULT
    }



}