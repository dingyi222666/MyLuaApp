package com.dingyi.myluaapp.builder.api.project

import com.dingyi.myluaapp.builder.api.builder.Builder
import com.dingyi.myluaapp.builder.api.file.FileManager
import com.dingyi.myluaapp.builder.api.runner.Runner
import com.dingyi.myluaapp.builder.api.script.ProjectScript
import com.dingyi.myluaapp.builder.api.script.Script
import com.dingyi.myluaapp.builder.api.task.Task

interface Project:ProjectScript {

    val name:String

    fun getTasks():List<Task>

    fun getModules():List<Module>

    fun getFileManager():FileManager


    fun getRunner(): Runner



    fun createModulesWeight():Map<Int,List<Module>>

    fun getBuilder(): Builder

    fun getMainModule():Module

    fun indexAllModule()

}