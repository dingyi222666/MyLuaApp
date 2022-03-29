package com.dingyi.myluaapp.plugin.api.build

import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.plugin.api.project.Project

interface BuildService<T> {

    fun addBuildService(type: T)

    fun getAllBuildService():List<T>

    fun build(project: Project, command:String)

    fun getMainBuilder(project: Project):MainBuilder

    fun stop()
}