package com.dingyi.myluaapp.build.api.execution

import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.tasks.TaskState


/**
 *
 * A `TaskExecutionListener` is notified of the execution of the tasks in a build.
 *
 *
 * You can add a `TaskExecutionListener` to a build using [com.dingyi.myluaapp.build.api.execution.TaskExecutionGraph.addTaskExecutionListener]
 */
interface TaskExecutionListener {
    /**
     * This method is called immediately before a task is executed.
     *
     * @param task The task about to be executed. Never null.
     */
    fun beforeExecute(task: Task)

    /**
     * This method is called immediately after a task has been executed. It is always called, regardless of whether the
     * task completed successfully, or failed with an exception.
     *
     * @param task The task which was executed. Never null.
     * @param state The task state. If the task failed with an exception, the exception is available in this
     * state. Never null.
     */
    fun afterExecute(task: Task, state: TaskState)
}
