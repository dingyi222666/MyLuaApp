package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.file.collection.FileCollection
import com.dingyi.myluaapp.build.api.file.collection.FileTree
import com.dingyi.myluaapp.build.api.internal.BuildToolInternal
import com.dingyi.myluaapp.build.api.plugins.ExtensionAware
import com.dingyi.myluaapp.build.api.plugins.PluginAware
import com.dingyi.myluaapp.build.api.plugins.PluginContainer
import com.dingyi.myluaapp.build.api.properties.Properties
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



}