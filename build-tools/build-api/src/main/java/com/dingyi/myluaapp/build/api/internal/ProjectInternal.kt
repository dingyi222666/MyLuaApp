package com.dingyi.myluaapp.build.api.internal

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.internal.project.ProjectIdentifier

interface ProjectInternal: Project,ProjectIdentifier {

    val HELP_TASK: String
        get() = "help"

    val TASKS_TASK: String
        get() = "tasks"
    
    val PROJECTS_TASK: String
        get() = "projects"

    override fun getBuildTool():BuildToolInternal


    fun addChildProject(childProject:ProjectInternal)


    override fun project(path: String):ProjectInternal


    fun project(
        referrer:ProjectInternal,
        path: String
    ):ProjectInternal

    fun project(
        referrer:ProjectInternal,
        path: String,
        configureAction: Action<in Project>
    ):ProjectInternal

    fun findProject(path: String):ProjectInternal

    fun findProject(
        referrer:ProjectInternal,
        path: String
    ):ProjectInternal

    fun getSubprojects(referrer:ProjectInternal): Set<ProjectInternal>

    fun subprojects(
        referrer:ProjectInternal,
        configureAction: Action<in Project>
    )

    fun getAllprojects(referrer:ProjectInternal): Set<ProjectInternal>

    fun allprojects(
        referrer:ProjectInternal,
        configureAction: Action<in Project>
    )

  
}