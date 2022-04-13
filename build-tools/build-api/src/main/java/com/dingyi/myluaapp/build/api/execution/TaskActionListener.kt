package com.dingyi.myluaapp.build.api.execution

import com.dingyi.myluaapp.build.api.Task


/**
 *
 * A `TaskActionListener` is notified of the actions that a task performs.
 */
interface TaskActionListener {
    /**
     * This method is called immediately before the task starts performing its actions.
     *
     * @param task The task which is to perform some actions.
     */
    fun beforeActions(task: Task)

    /**
     * This method is called immediately after the task has completed performing its actions.
     *
     * @param task The task which has performed some actions.
     */
    fun afterActions(task: Task)
}
