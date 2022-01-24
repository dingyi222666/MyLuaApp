package com.dingyi.myluaapp.build.api.service

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project

interface HookService {

    fun onCreateModule(module: Module)

    fun onCreateProject(project: Project)
}