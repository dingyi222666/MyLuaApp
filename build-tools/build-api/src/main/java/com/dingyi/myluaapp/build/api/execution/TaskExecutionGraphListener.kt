package com.dingyi.myluaapp.build.api.execution


/**
 *
 * A `TaskExecutionGraphListener` is notified when the [TaskExecutionGraph] has been populated. You can
 * use this interface in your build file to perform some action based on the contents of the graph, before any tasks are
 * actually executed.
 */
interface TaskExecutionGraphListener {
    /**
     *
     * This method is called when the [TaskExecutionGraph] has been populated, and before any tasks are
     * executed.
     *
     * @param graph The graph. Never null.
     */
    fun graphPopulated(graph: TaskExecutionGraph)
}
