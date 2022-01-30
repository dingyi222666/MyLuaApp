package com.dingyi.myluaapp.build.api.service

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project

interface HookService {

    fun onCreateModule(module: Module)

    fun onCreateProject(project: Project)
}