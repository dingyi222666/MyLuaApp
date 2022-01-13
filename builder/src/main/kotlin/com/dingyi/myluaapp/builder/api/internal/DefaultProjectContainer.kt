package com.dingyi.myluaapp.builder.api.internal

import com.dingyi.myluaapp.builder.api.Project

class DefaultProjectContainer(
    private val builderInternal: BuilderInternal
):ProjectContainer {


    private val allProject = mutableSetOf<ProjectInternal>()

    override fun registerProject(project: ProjectInternal) {
        if (allProject.add(project).not()) {
            throw Exception("The Project is registered!")
        }
    }

    override fun getBuilderInternal(): BuilderInternal {
        return builderInternal
    }

    override fun removeProject(project: ProjectInternal) {
       allProject.remove(project)
    }

    override fun removeProjectByName(name: String) {
        allProject.remove(findProjectByName(name))
    }

    override fun findProjectByName(name: String): ProjectInternal? {
        return allProject.find { it.name == name }
    }



    override fun getAllProject(): Set<ProjectInternal> {
        return allProject
    }

}
