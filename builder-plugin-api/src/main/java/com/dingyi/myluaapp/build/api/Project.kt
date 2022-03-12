package com.dingyi.myluaapp.build.api

import androidx.annotation.WorkerThread
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.scheduler.Scheduler
import com.dingyi.myluaapp.build.api.script.ProjectScript
import com.dingyi.myluaapp.build.api.dependency.Dependency

interface Project : ProjectScript {

    val name: String

    fun getTasks(type: String): List<Task>

    fun getAllModule(): List<Module>

    fun getFileManager(): FileManager


    fun getRunner(): Scheduler

    /**
     * Will index all module and build script,causes thread blocked,so only call it in work thread
     */
    @WorkerThread
    fun init()

    fun createModulesWeight(): Map<Int, List<Module>>

    fun getBuilder(): Builder

    fun getMainModule(): Module

    fun indexAllModule()

    fun getMainBuilder(): MainBuilder

    fun getLogger(): ILogger

    fun getModule(name: String): Module?

    fun getAllDependency(): List<Dependency>


    fun getMavenRepository(): MavenRepository

    fun getPath(): String

    fun getCache(): Cache

    fun close()
}