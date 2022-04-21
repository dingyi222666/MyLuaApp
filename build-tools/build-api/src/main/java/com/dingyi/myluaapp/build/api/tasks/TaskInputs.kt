package com.dingyi.myluaapp.build.api.tasks

import com.dingyi.myluaapp.build.api.file.collection.FileCollection

interface TaskInputs {
    /**
     * Returns true if this task has declared the inputs that it consumes.
     *
     * @return true if this task has declared any inputs.
     */
    fun getHasInputs(): Boolean

    /**
     * Returns the input files of this task.
     *
     * @return The input files. Returns an empty collection if this task has no input files.
     */
    fun getFiles(): FileCollection

    /**
     * Registers some input files for this task.
     *
     * @param paths The input files. The given paths are evaluated as per [org.gradle.api.Project.files].
     * @return a property builder to further configure the property.
     */
    fun files(vararg paths: Any): TaskInputs

    /**
     * Registers some input file for this task.
     *
     * @param path The input file. The given path is evaluated as per [org.gradle.api.Project.file].
     * @return a property builder to further configure the property.
     */
    fun file(path: Any): TaskInputs

    /**
     * Registers an input directory hierarchy. All files found under the given directory are treated as input files for
     * this task.
     *
     * @param dirPath The directory. The path is evaluated as per [org.gradle.api.Project.file].
     * @return a property builder to further configure the property.
     */
    fun dir(dirPath: Any): TaskInputs

    /**
     * Returns a map of input properties for this task.
     *
     * The returned map is unmodifiable, and does not reflect further changes to the task's properties.
     * Trying to modify the map will result in an [UnsupportedOperationException] being thrown.
     *
     * @return The properties.
     */
    fun getProperties(): Map<String, Any>

    /**
     *
     * Registers an input property for this task. This value is persisted when the task executes, and is compared
     * against the property value for later invocations of the task, to determine if the task is up-to-date.
     *
     *
     * The given value for the property must be Serializable, so that it can be persisted. It should also provide a
     * useful `equals()` method.
     *
     *
     * You can specify a closure or `Callable` as the value of the property. In which case, the closure or
     * `Callable` is executed to determine the actual property value.
     *
     * @param name The name of the property. Must not be null.
     * @param value The value for the property. Can be null.
     */
    fun property(name: String, value: Any):TaskInputs

    /**
     * Registers a set of input properties for this task. See [.property] for details.
     *
     *
     * **Note:** do not use the return value to chain calls.
     * Instead always use call via [org.gradle.api.Task.getInputs].
     *
     * @param properties The properties.
     */
    fun properties(properties: Map<String, *>): TaskInputs

    /**
     * Returns true if this task has declared that it accepts source files.
     *
     * @return true if this task has source files, false if not.
     */
    fun getHasSourceFiles(): Boolean

    /**
     * Returns the set of source files for this task. These are the subset of input files which the task actually does work on.
     * A task is skipped if it has declared it accepts source files, and this collection is empty.
     *
     * @return The set of source files for this task.
     */
    fun getSourceFiles(): FileCollection
}