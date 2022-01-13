package com.dingyi.myluaapp.builder.api

import com.dingyi.myluaapp.builder.api.dependency.Dependency
import com.dingyi.myluaapp.builder.api.task.Task
import com.dingyi.myluaapp.builder.api.task.TaskList
import java.io.File

interface Project {


    var name: String

    var path: File

    var buildPath: File

    fun addTask(name: String, task: Task)

    fun removeTask(name: String, task: Task)

    fun getDependencies(): List<Dependency>


    fun execScript(script: Script)

    fun delete(path: String): Boolean

    fun file(path: String): File


    fun getRootProject(): Project

    fun getAllProject(): Set<Project>

    /**
     * if not rootProject,always return null
     */
    fun getChildProject(): Set<Project>?

    fun getBuilder(): Builder

    fun getRunArgments():Map<String,Any>


}