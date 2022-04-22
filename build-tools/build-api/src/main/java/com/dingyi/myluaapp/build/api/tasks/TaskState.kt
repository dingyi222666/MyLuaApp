package com.dingyi.myluaapp.build.api.tasks


/**
 * `TaskState` provides information about the execution state of a [com.dingyi.myluaapp.build.api.Task]. You can obtain a
 * `TaskState` instance by calling [com.dingyi.myluaapp.build.api.Task.getState].
 */

interface TaskState {
    /**
     *
     * Returns true if this task has been executed.
     *
     * @return true if this task has been executed.
     */
    fun getExecuted(): Boolean


    /**
     * Returns the exception describing the task failure, if any.
     *
     * @return The exception, or null if the task did not fail.
     */
    fun getFailure(): Throwable?

    /**
     * Throws the task failure, if any. Does nothing if the task did not fail.
     */
    fun rethrowFailure()

    /**
     *
     * Checks if the task actually did any work.  Even if a task executes, it may determine that it has nothing to
     * do.  For example, a compilation task may determine that source files have not changed since the last time a the
     * task was run.
     *
     * @return true if this task has been executed and did any work.
     */

    fun getDidWork(): Boolean

    /**
     * Returns true if the execution of this task was skipped for some reason.
     *
     * @return true if this task has been executed and skipped.
     */
    fun getSkipped(): Boolean

    /**
     * Returns a message describing why the task was skipped.
     *
     * @return the message. returns null if the task was not skipped.
     */
    fun getSkipMessage(): String?

    /**
     * Returns true if the execution of this task was skipped because the task was up-to-date.
     *
     * @return true if this task has been considered up-to-date
     * @since 2.5
     */
    fun getUpToDate(): Boolean

    /**
     * Returns true if the execution of this task was skipped due to task inputs are empty.
     *
     * @return true if this task has no input files assigned
     * @since 3.4
     */
    fun getNoSource(): Boolean
}
