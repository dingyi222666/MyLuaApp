package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.logging.Logger
import java.io.File

interface Project {



    fun getBuildTool(): BuildTool

    /**
     *
     * Returns this project. This method is useful in build files to explicitly access project properties and
     * methods. For example, using `project.name` can express your intent better than using
     * `name`. This method also allows you to access project properties from a scope where the property may
     * be hidden, such as, for example, from a method or closure.
     *
     * @return This project. Never returns null.
     */
    fun getProject(): Project

    /**
     *
     * Returns the set containing this project and its subprojects.
     *
     * @return The set of projects.
     */
    fun getAllprojects(): Set<Project>

    /**
     *
     * The directory containing the project build file.
     *
     * @return The project directory. Never returns null.
     */
    fun getProjectDir(): File?


    /**
     *
     * Resolves a file path relative to the project directory of this project. This method converts the supplied path
     * based on its type:
     *
     *
     *
     *  * A [CharSequence], including [String] or [groovy.lang.GString]. Interpreted relative to the project directory. A string that starts with `file:` is treated as a file URL.
     *
     *  * A [File]. If the file is an absolute file, it is returned as is. Otherwise, the file's path is
     * interpreted relative to the project directory.
     *
     * @param path The object to resolve as a File.
     * @return The resolved file. Never returns null.
     */
    fun file(path: CharSequence): File?

    /**
     * Creates a directory and returns a file pointing to it.
     *
     * @param path The path for the directory to be created. Evaluated as per [.file].
     * @return the created directory
     * @throws org.gradle.api.InvalidUserDataException If the path points to an existing file.
     */
    fun mkdir(path: Any?): File?

    /**
     * Deletes files and directories.
     *
     *
     * This will not follow symlinks. If you need to follow symlinks too use [.delete].
     *
     * @param paths Any type of object accepted by [org.gradle.api.Project.files]
     * @return true if anything got deleted, false otherwise
     */
    fun delete(vararg paths: Any?): Boolean


    /**
     * Adds an action to execute immediately before this project is evaluated.
     *
     * @param action the action to execute.
     */
    fun beforeEvaluate(action: Action<in Project>)

    /**
     * Adds an action to execute immediately after this project is evaluated.
     *
     * @param action the action to execute.
     */
    fun afterEvaluate(action: Action<in Project>)

    /**
     *
     * Adds a closure to be called immediately before this project is evaluated. The project is passed to the closure
     * as a parameter.
     *
     * @param closure The closure to call.
     */
    fun beforeEvaluate(closure: (Project) -> Unit)

    /**
     *
     * Adds a closure to be called immediately after this project has been evaluated. The project is passed to the
     * closure as a parameter. Such a listener gets notified when the build file belonging to this project has been
     * executed. A parent project may for example add such a listener to its child project. Such a listener can further
     * configure those child projects based on the state of the child projects after their build files have been
     * run.
     *
     * @param closure The closure to call.
     */
    fun afterEvaluate(closure: (Project) -> Unit)


    /**
     *
     * Sets a property of this project.  This method searches for a property with the given name in the following
     * locations, and sets the property on the first location where it finds the property.
     *
     *
     *
     *  1. The project object itself.  For example, the `rootDir` project property.
     *
     *  1. The project's [Convention] object.  For example, the `srcRootName` java plugin
     * property.
     *
     *  1. The project's extra properties.
     *
     *
     *
     * If the property is not found, a [groovy.lang.MissingPropertyException] is thrown.
     *
     * @param name The name of the property
     * @param value The value of the property
     */
    fun setProperty(name: String, value: Any?)


    /**
     *
     * Determines if this project has the given property. See [here](#properties) for details of the
     * properties which are available for a project.
     *
     * @param propertyName The name of the property to locate.
     * @return True if this project has the given property, false otherwise.
     */
    fun hasProperty(propertyName: String?): Boolean

    /**
     *
     * Returns the properties of this project. See [here](#properties) for details of the properties which
     * are available for a project.
     *
     * @return A map from property name to value.
     */
    fun getProperties(): Map<String?, *>?

    /**
     *
     * Returns the value of the given property.  This method locates a property as follows:
     *
     *
     *
     *  1. If this project object has a property with the given name, return the value of the property.
     *
     *  1. If this project has an extension with the given name, return the extension.
     *
     *  1. If this project's convention object has a property with the given name, return the value of the
     * property.
     *
     *  1. If this project has an extra property with the given name, return the value of the property.
     *
     *  1. If this project has a task with the given name, return the task.
     *
     *  1. Search up through this project's ancestor projects for a convention property or extra property with the
     * given name.
     *
     *  1. If not found, a [MissingPropertyException] is thrown.
     *
     *
     *
     * @param propertyName The name of the property.
     * @return The value of the property, possibly null.
     * @throws MissingPropertyException When the given property is unknown.
     * @see Project.findProperty
     */

    fun property(propertyName: String?): Any?

    /**
     *
     * Returns the value of the given property or null if not found.
     * This method locates a property as follows:
     *
     *
     *
     *  1. If this project object has a property with the given name, return the value of the property.
     *
     *  1. If this project has an extension with the given name, return the extension.
     *
     *  1. If this project's convention object has a property with the given name, return the value of the
     * property.
     *
     *  1. If this project has an extra property with the given name, return the value of the property.
     *
     *  1. If this project has a task with the given name, return the task.
     *
     *  1. Search up through this project's ancestor projects for a convention property or extra property with the
     * given name.
     *
     *  1. If not found, null value is returned.
     *
     *
     *
     * @param propertyName The name of the property.
     * @since 2.13
     * @return The value of the property, possibly null or null if not found.
     * @see Project.property
     */
    fun findProperty(propertyName: String?): Any?

    /**
     *
     * Returns the logger for this project. You can use this in your build file to write log messages.
     *
     * @return The logger. Never returns null.
     */
    fun getLogger(): Logger


    /**
     *
     * Returns the build directory of this project.  The build directory is the directory which all artifacts are
     * generated into.  The default value for the build directory is `*projectDir*
    build`
     *
     * @ return The build directory. Never returns null.
     */
    fun getBuildDir(): File?

    /**
     *
     * Sets the build directory of this project. The build directory is the directory which all artifacts are
     * generated into.
     *
     * @param path The build directory
     * @since 4.0
     */
    fun setBuildDir(path: File?)

    /**
     *
     * Sets the build directory of this project. The build directory is the directory which all artifacts are
     * generated into. The path parameter is evaluated as described for [.file]. This mean you can use,
     * amongst other things, a relative or absolute path or File object to specify the build directory.
     *
     * @param path The build directory. This is evaluated as per [.file]
     */
    fun setBuildDir(path: Any?)


}