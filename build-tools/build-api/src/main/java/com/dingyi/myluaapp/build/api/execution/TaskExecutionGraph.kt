package com.dingyi.myluaapp.build.api.execution

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.Task


/**
 *
 * A `TaskExecutionGraph` is responsible for managing the execution of the [Task] instances which
 * are part of the build. The `TaskExecutionGraph` maintains an execution plan of tasks to be executed (or
 * which have been executed), and you can query this plan from your build file.
 *
 *
 * You can access the `TaskExecutionGraph` by calling [org.gradle.api.invocation.Gradle.getTaskGraph].
 * In your build file you can use `gradle.taskGraph` to access it.
 *
 *
 * The `TaskExecutionGraph` is populated only after all the projects in the build have been evaluated. It
 * is empty before then. You can receive a notification when the graph is populated, using [ ][.whenReady] or [.addTaskExecutionGraphListener].
 */
interface TaskExecutionGraph {
    /**
     *
     * Adds a listener to this graph, to be notified when this graph is ready.
     *
     * @param listener The listener to add. Does nothing if this listener has already been added.
     */
    fun addTaskExecutionGraphListener(listener:TaskExecutionGraphListener)

    /**
     *
     * Remove a listener from this graph.
     *
     * @param listener The listener to remove. Does nothing if this listener was never added to this graph.
     */
    fun removeTaskExecutionGraphListener(listener: TaskExecutionGraphListener)

    /**
     *
     * Adds a listener to this graph, to be notified as tasks are executed.
     *
     * @param listener The listener to add. Does nothing if this listener has already been added.
     */
    fun addTaskExecutionListener(listener: TaskExecutionListener)

    /**
     *
     * Remove a listener from this graph.
     *
     * @param listener The listener to remove. Does nothing if this listener was never added to this graph.
     */
    fun removeTaskExecutionListener(listener: TaskExecutionListener)


    /**
     *
     * Adds an action to be called when this graph has been populated. This graph is passed to the action as a
     * parameter.
     *
     * @param action The action to execute when this graph has been populated.
     *
     * @since 3.1
     */
    fun whenReady(action: Action<TaskExecutionGraph>)


    /**
     *
     * Adds an action to be called immediately before a task is executed. The task is passed to the action as a
     * parameter.
     *
     * @param action The action to execute when a task is about to be executed.
     *
     * @since 3.1
     */
    fun beforeTask(action: Action<Task>)



    /**
     *
     * Adds an action to be called immediately after a task has executed. The task is passed to the action as the
     * first parameter.
     *
     * @param action The action to execute when a task has been executed
     *
     * @since 3.1
     */
    fun afterTask(action: Action<in Task>)

    /**
     *
     * Determines whether the given task is included in the execution plan.
     *
     * @param path the *absolute* path of the task.
     * @return true if a task with the given path is included in the execution plan.
     * @throws IllegalStateException When this graph has not been populated.
     */
    fun hasTask(path: String): Boolean

    /**
     *
     * Determines whether the given task is included in the execution plan.
     *
     * @param task the task
     * @return true if the given task is included in the execution plan.
     * @throws IllegalStateException When this graph has not been populated.
     */
    fun hasTask(task: Task): Boolean

    /**
     *
     * Returns the tasks which are included in the execution plan. The tasks are returned in the order that they will
     * be executed.
     *
     * @return The tasks. Returns an empty set if no tasks are to be executed.
     * @throws IllegalStateException When this graph has not been populated.
     */
    fun getAllTasks():List<Task>

    /**
     *
     * Returns the dependencies of a task which are part of the execution graph.
     *
     * @return The tasks. Returns an empty set if there are no dependent tasks.
     * @throws IllegalStateException When this graph has not been populated or the task is not part of it.
     *
     * @since 4.6
     */
    fun getDependencies(task: Task): Set<Task>
}
