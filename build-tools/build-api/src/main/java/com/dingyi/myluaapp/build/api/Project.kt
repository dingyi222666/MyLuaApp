package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.file.collection.FileCollection
import com.dingyi.myluaapp.build.api.file.collection.FileTree
import com.dingyi.myluaapp.build.api.plugins.ExtensionAware
import com.dingyi.myluaapp.build.api.plugins.PluginAware
import com.dingyi.myluaapp.build.api.properties.Properties
import com.dingyi.myluaapp.build.api.tasks.TaskContainer
import java.io.File

interface Project:Properties,PluginAware,ExtensionAware {

    /**
     * The default project build file name.
     */
    val DEFAULT_BUILD_FILE: String
        get() = "build.gradle"


    /**
     * The hierarchy separator for project and task path names.
     */
    val PATH_SEPARATOR: String
        get() = ":"

    /**
     * The default build directory name.
     */
    val DEFAULT_BUILD_DIR_NAME: String
        get() = "build"

    val GRADLE_PROPERTIES: String
        get() = "gradle.properties"

    val SYSTEM_PROP_PREFIX: String
        get() = "systemProp"

    val DEFAULT_VERSION: String
        get() = "unspecified"

    val DEFAULT_STATUS: String
        get() = "release"

    fun getAllProject():Set<Project>

    fun getPath():String

    fun getName():String


    fun file(path:Any): File

    fun fileTree(arg:Map<String,String>): FileTree

    fun files(vararg path: Any):FileCollection

    fun getBuildTool(): BuildTool



    /**
     * <p>Returns the build directory of this project.  The build directory is the directory which all artifacts are
     * generated into.  The default value for the build directory is <code><i>projectDir</i>/build</code></p>
     *
     * @return The build directory. Never returns null.
     */
    fun getBuildDir():File

    /**
     * <p>Sets the build directory of this project. The build directory is the directory which all artifacts are
     * generated into.</p>
     *
     * @param path The build directory
     * @since 4.0
     */
    fun setBuildDir(path:File);


    /**
     *
     * Returns the group of this project. Gradle always uses the `toString()` value of the group. The group
     * defaults to the path with dots as separators.
     *
     * @return The group of this project. Never returns null.
     */
    fun getGroup(): Any

    /**
     *
     * Sets the group of this project.
     *
     * @param group The group of this project. Must not be null.
     */
    fun setGroup(group: Any)


    /**
     *
     * Returns the status of this project. Gradle always uses the `toString()` value of the status. The status
     * defaults to {@value #DEFAULT_STATUS}.
     *
     *
     * The status of the project is only relevant, if you upload libraries together with a module descriptor. The
     * status specified here, will be part of this module descriptor.
     *
     * @return The status of this project. Never returns null.
     */
    fun getStatus(): Any

    /**
     * Sets the status of this project.
     *
     * @param status The status. Must not be null.
     */
    fun setStatus(status: Any)


    /**
     *
     * Returns this project. This method is useful in build files to explicitly access project properties and
     * methods. For example, using `project.name` can express your intent better than using
     * `name`. This method also allows you to access project properties from a scope where the property may
     * be hidden, such as, for example, from a method or closure.
     *
     * @return This project. Never returns null.
     */
    fun getProject():Project

    /**
     *
     * Returns the set containing this project and its subprojects.
     *
     * @return The set of projects.
     */
    fun getAllprojects(): Set<Project>

    /**
     *
     * Returns the set containing the subprojects of this project.
     *
     * @return The set of projects.  Returns an empty set if this project has no subprojects.
     */
    fun getSubprojects(): Set<Project>


    /**
     *
     * Returns the tasks of this project.
     *
     * @return the tasks of this project.
     */
    fun getTasks(): TaskContainer


    /**
     *
     * Locates a project by path. If the path is relative, it is interpreted relative to this project.
     *
     * @param path The path.
     * @return The project with the given path. Never returns null.
     * @throws UnknownProjectException If no project with the given path exists.
     */
 
    fun project(path: String): Project

 
    /**
     *
     * Locates a project by path and configures it using the given action. If the path is relative, it is
     * interpreted relative to this project.
     *
     * @param path The path.
     * @param configureAction The action to use to configure the project.
     * @return The project with the given path. Never returns null.
     * @throws UnknownProjectException If no project with the given path exists.
     *
     * @since 3.4
     */
    fun project(
        path: String,
        configureAction: Action<in Project>
    ): Project


    /**
     *
     * Configures the sub-projects of this project
     *
     *
     * This method executes the given [Action] against the sub-projects of this project.
     *
     * @param action The action to execute.
     */
    fun subprojects(action: Action<in Project>)


    /**
     *
     * Configures this project and each of its sub-projects.
     *
     *
     * This method executes the given [Action] against this project and each of its sub-projects.
     *
     * @param action The action to execute.
     */
    fun allprojects(action: Action<in Project>)

  
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
}