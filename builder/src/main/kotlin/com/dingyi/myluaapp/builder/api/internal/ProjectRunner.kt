package com.dingyi.myluaapp.builder.api.internal

interface ProjectRunner {

    fun getBuilderInternal():BuilderInternal

    fun runAllProject(group:String = "default")
    fun run(group:String = "default")
    fun runProject(projectInternal: ProjectInternal,group:String = "default")

}