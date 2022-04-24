package com.dingyi.myluaapp.build.api


/**
 * [ProjectState] provides information about the execution state of a project.
 */
interface ProjectState {
    /**
     *
     * Returns true if this project has been evaluated.
     *
     * @return true if this project has been evaluated.
     */
    fun getExecuted(): Boolean

    /**
     * Returns the exception describing the project failure, if any.
     *
     * @return The exception, or null if project evaluation did not fail.
     */
    fun getFailure(): Throwable?

    /**
     * Throws the project failure, if any. Does nothing if the project did not fail.
     */
    fun rethrowFailure()
}
