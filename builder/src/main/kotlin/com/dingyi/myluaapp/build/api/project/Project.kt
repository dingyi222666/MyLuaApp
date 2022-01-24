package com.dingyi.myluaapp.build.api.project

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.runner.Runner
import com.dingyi.myluaapp.build.api.script.ProjectScript
import com.dingyi.myluaapp.build.api.task.Task

interface Project : ProjectScript {

    val name: String

    fun getTasks(type: String): List<Task>

    fun getModules(): List<Module>

    fun getFileManager(): FileManager


    fun getRunner(): Runner

    fun init()

    fun createModulesWeight(): Map<Int, List<Module>>

    fun getBuilder(): Builder

    fun getMainModule(): Module

    fun indexAllModule()

    fun getMainBuilder(): MainBuilder

    fun getLogger(): ILogger

    fun getModule(name: String): Module

}