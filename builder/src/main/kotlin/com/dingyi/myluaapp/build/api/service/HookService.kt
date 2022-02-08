package com.dingyi.myluaapp.build.api.service

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder

interface HookService : Service {

    fun onCreateModule(module: Module)

    fun onCreateProject(project: Project)

    fun onCreateBuilder(builder: MainBuilder)

    override fun onCreateBuilder(path: String, module: Module): Builder? {
        return null
    }

    override fun onCreateModule(path: String, project: Project): Module? {
        return null
    }

    override fun onCreateProject(path: String, builder: MainBuilder): Project? {
        return null
    }
}