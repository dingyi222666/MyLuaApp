package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.file.TaskInput
import com.dingyi.myluaapp.build.api.logging.Logger
import com.dingyi.myluaapp.build.api.plugins.ExtensionAware

interface Task: ExtensionAware {


    val name: String


    fun execute()

    fun prepare():TaskState

    /**
     *
     * Returns the [Project] which this task belongs to.
     *
     * @return The project this task belongs to. Never returns null.
     */

    fun getProject():Project

    /**
     *
     * Returns the sequence of [Action] objects which will be executed by this task, in the order of
     * execution.
     *
     * @return The task actions in the order they are executed. Returns an empty list if this task has no actions.
     */

    fun getActions(): List<Action<in Task>>

    /**
     *
     * Sets the sequence of [Action] objects which will be executed by this task.
     *
     * @param actions The actions.
     */
    fun setActions(actions: List<Action<in Task>>)


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
        actionName: String?,
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
        action: Action<in Task>
    ): Task?



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
     * Returns the logger for this task. You can use this in your build file to write log messages.
     *
     * @return The logger. Never returns null.
     */

    fun getLogger(): Logger


    /**
     *
     * Returns the value of the given property of this task.  This method locates a property as follows:
     *
     *
     *
     *  1. If this task object has a property with the given name, return the value of the property.
     *
     *  1. If this task has an extension with the given name, return the extension.
     *
     *  1. If this task's convention object has a property with the given name, return the value of the property.
     *
     *  1. If this task has an extra property with the given name, return the value of the property.
     *
     *  1. If not found, throw [MissingPropertyException]
     *
     *
     *
     * @param propertyName The name of the property.
     * @return The value of the property, possibly null.
     * @throws MissingPropertyException When the given property is unknown.
     */
    fun property(propertyName: String?): Any?

    /**
     *
     * Determines if this task has the given property. See [here](#properties) for details of the
     * properties which are available for a task.
     *
     * @param propertyName The name of the property to locate.
     * @return True if this project has the given property, false otherwise.
     */
    fun hasProperty(propertyName: String?): Boolean

    /**
     *
     * Sets a property of this task.  This method searches for a property with the given name in the following
     * locations, and sets the property on the first location where it finds the property.
     *
     *
     *
     *  1. The task object itself.  For example, the `enabled` project property.
     *
     *  1. The task's convention object.
     *
     *  1. The task's extra properties.
     *
     *
     *
     * If the property is not found, a [groovy.lang.MissingPropertyException] is thrown.
     *
     * @param name The name of the property
     * @param value The value of the property
     */

    fun setProperty(name: String?, value: Any?)


    /**
     * Returns the description of this task.
     *
     * @return the description. May return null.
     */

    fun getDescription(): String

    /**
     * Sets a description for this task. This should describe what the task does to the user of the build. The
     * description will be displayed when `gradle tasks` is called.
     *
     * @param description The description of the task. Might be null.
     */
    fun setDescription(description: String)

    /**
     * Returns the task group which this task belongs to. The task group is used in reports and user interfaces to
     * group related tasks together when presenting a list of tasks to the user.
     *
     * @return The task group for this task. Might be null.
     */

    fun getGroup(): String

    /**
     * Sets the task group which this task belongs to. The task group is used in reports and user interfaces to
     * group related tasks together when presenting a list of tasks to the user.
     *
     * @param group The task group for this task. Can be null.
     */
    fun setGroup(group: String)


    fun getTaskInput(): TaskInput

}