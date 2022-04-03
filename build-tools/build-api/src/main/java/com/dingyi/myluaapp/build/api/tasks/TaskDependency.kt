package com.dingyi.myluaapp.build.api.tasks

import com.dingyi.myluaapp.build.api.Task


/**
 *
 * A `TaskDependency` represents an *unordered* set of tasks which a [Task] depends on.
 * Gradle ensures that all the dependencies of a task are executed before the task itself is executed.
 *
 *
 * You can add a `TaskDependency` to a task by calling the task's [Task.dependsOn]
 * method.
 */
interface TaskDependency {
    /**
     *
     * Determines the dependencies for the given [Task]. This method is called when Gradle assembles the task
     * execution graph for a build. This occurs after all the projects have been evaluated, and before any task
     * execution begins.
     *
     * @param task The task to determine the dependencies for.
     * @return The tasks which the given task depends on. Returns an empty set if the task has no dependencies.
     */
    fun getDependencies(task: Task): Set<Task>
}
