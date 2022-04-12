package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.tasks.TaskState

interface Task {

    fun getAction(): List<Action<out Task>>

    fun getName(): String

    fun getDescription(): String

    fun getGroup(): String

    fun setGroup(group: String): Task


    /**
     *
     * Sets the dependencies of this task. See [here](#dependencies) for a description of the types of
     * objects which can be used as task dependencies.
     *
     * @param dependsOnTasks The set of task paths.
     */
    fun setDependsOn(dependsOnTasks: Iterable<*>)

    /**
     *
     * Adds the given dependencies to this task. See [here](#dependencies) for a description of the types
     * of objects which can be used as task dependencies.
     *
     * @param paths The dependencies to add to this task.
     *
     * @return the task object this method is applied to
     */
    fun dependsOn(vararg paths: Any): Task


    /**
     *
     * Adds the given [Action] to the beginning of this task's action list.
     *
     * @param action The action to add
     * @return the task object this method is applied to
     */
    fun doFirst(action: Action<in Task>): Task

    /**
     *
     * Adds the given [Action] to the beginning of this task's action list.
     *
     * @param actionName An arbitrary string that is used for logging.
     * @param action The action to add
     * @return the task object this method is applied to
     *
     * @since 4.2
     */
    fun doFirst(
        actionName: String,
        action: Action<in Task>
    ): Task

    /**
     *
     * Adds the given [Action] to the end of this task's action list.
     *
     * @param action The action to add.
     * @return the task object this method is applied to
     */
    fun doLast(action: Action<in Task>): Task

    /**
     *
     * Adds the given [Action] to the end of this task's action list.
     *
     * @param actionName An arbitrary string that is used for logging.
     * @param action The action to add.
     * @return the task object this method is applied to
     *
     * @since 4.2
     */
    fun doLast(
        actionName: String,
        action:Action<in Task>
    ): Task


    /**
     * Returns the execution state of this task. This provides information about the execution of this task, such as
     * whether it has executed, been skipped, has failed, etc.
     *
     * @return The execution state of this task. Never returns null.
     */

    fun getState(): TaskState

    /**
     * Sets whether the task actually did any work.  Most built-in tasks will set this automatically, but
     * it may be useful to manually indicate this for custom user tasks.
     * @param didWork indicates if the task did any work
     */
    fun setDidWork(didWork: Boolean)

    /**
     *
     * Checks if the task actually did any work.  Even if a Task executes, it may determine that it has nothing to
     * do.  For example, a compilation task may determine that source files have not changed since the last time a the
     * task was run.
     *
     * @return true if this task did any work
     */
    fun getDidWork(): Boolean

}