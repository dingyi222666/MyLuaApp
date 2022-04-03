package com.dingyi.myluaapp.build.api

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

}