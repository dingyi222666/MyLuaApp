package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.properties.Properties
import com.dingyi.myluaapp.build.api.sepcs.Spec
import com.dingyi.myluaapp.build.api.tasks.TaskState

interface Task:Properties {

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

    fun getProject():Project



    /**
     *
     * Sets the sequence of [Action] objects which will be executed by this task.
     *
     * @param actions The actions.
     */
    fun setActions(actions: List<Action<in Task>>)


    /**
     *
     * Returns the dependencies of this task.
     *
     * @return The dependencies of this task. Returns an empty set if this task has no dependencies.
     */
    fun getDependsOn(): Set<Any>


    /**
     *
     * Execute the task only if the given spec is satisfied. The spec will be evaluated at task execution time, not
     * during configuration. If the Spec is not satisfied, the task will be skipped.
     *
     *
     * You may add multiple such predicates. The task is skipped if any of the predicates return false.
     *
     *
     * Typical usage (from Java):
     * <pre>myTask.onlyIf(new Spec&lt;Task&gt;() {
     * boolean isSatisfiedBy(Task task) {
     * return isProductionEnvironment();
     * }
     * });
    </pre> *
     *
     * @param onlyIfSpec specifies if a task should be run
     */
    fun onlyIf(onlyIfSpec: Spec<in Task>)



    /**
     *
     * Execute the task only if the given spec is satisfied. The spec will be evaluated at task execution time, not
     * during configuration. If the Spec is not satisfied, the task will be skipped.
     *
     *
     * The given predicate replaces all such predicates for this task.
     *
     * @param onlyIfSpec specifies if a task should be run
     */
    fun setOnlyIf(onlyIfSpec: Spec<in Task>)


    /**
     *
     * Returns if this task is enabled or not.
     *
     * @see .setEnabled
     */

    fun getEnabled(): Boolean

    /**
     *
     * Set the enabled state of a task. If a task is disabled none of the its actions are executed. Note that
     * disabling a task does not prevent the execution of the tasks which this task depends on.
     *
     * @param enabled The enabled state of this task (true or false)
     */
    fun setEnabled(enabled: Boolean)

    /**
     *
     * Applies the statements of the closure against this task object. The delegate object for the closure is set to
     * this task.
     *
     * @param configureClosure The closure to be applied (can be null).
     * @return This task
     */
    fun configure(configureClosure: Action<in Task>): Task

}

