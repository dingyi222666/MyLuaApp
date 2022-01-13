package com.dingyi.myluaapp.builder.api.internal



interface ProjectContainer {

    fun registerProject(project: ProjectInternal)

    fun getBuilderInternal():BuilderInternal

    fun removeProject(project: ProjectInternal)

    fun removeProjectByName(name: String)

    fun findProjectByName(name:String):ProjectInternal?


    fun getAllProject():Set<ProjectInternal>

}