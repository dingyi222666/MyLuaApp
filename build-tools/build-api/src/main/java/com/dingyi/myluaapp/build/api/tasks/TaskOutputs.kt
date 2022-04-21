package com.dingyi.myluaapp.build.api.tasks

import com.dingyi.myluaapp.build.api.file.collection.FileCollection

interface TaskOutputs {
    /**
     * Returns true if this task has declared any outputs. Note that a task may be able to produce output files and
     * still have an empty set of output files.
     *
     * @return true if this task has declared any outputs, otherwise false.
     */
    fun getHasOutput(): Boolean

    /**
     * Returns the output files of this task.
     *
     * @return The output files. Returns an empty collection if this task has no output files.
     */
    fun getFiles(): FileCollection

    /**
     * Registers some output files for this task.
     *
     *
     * When the given `paths` is a [java.util.Map], then each output file
     * will be associated with an identity.
     * The keys of the map must be non-empty strings.
     * The values of the map will be evaluated to individual files as per
     * [org.gradle.api.Project.file].
     *
     *
     * Otherwise the given files will be evaluated as per
     * [org.gradle.api.Project.files].
     *
     * @param paths The output files.
     *
     * @see CacheableTask
     */
    fun files(vararg paths: Any): TaskOutputs

    /**
     * Registers some output directories for this task.
     *
     *
     * When the given `paths` is a [java.util.Map], then each output directory
     * will be associated with an identity.
     * The keys of the map must be non-empty strings.
     * The values of the map will be evaluated to individual directories as per
     * [org.gradle.api.Project.file].
     *
     *
     * Otherwise the given directories will be evaluated as per
     * [org.gradle.api.Project.files].
     *
     * @param paths The output files.
     *
     * @see CacheableTask
     *
     *
     * @since 3.3
     */
    fun dirs(vararg paths: Any): TaskOutputs

    /**
     * Registers some output file for this task.
     *
     * @param path The output file. The given path is evaluated as per [org.gradle.api.Project.file].
     * @return a property builder to further configure this property.
     */
    fun file(path: Any): TaskOutputs

    /**
     * Registers an output directory for this task.
     *
     * @param path The output directory. The given path is evaluated as per [org.gradle.api.Project.file].
     * @return a property builder to further configure this property.
     */
    fun dir(path: Any): TaskOutputs
}