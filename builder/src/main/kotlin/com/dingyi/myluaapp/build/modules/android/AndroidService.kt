package com.dingyi.myluaapp.build.modules.android

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.service.Service

class AndroidService:Service {
    override fun onCreateProject(path: String): Project? {
        TODO("Not yet implemented")
    }

    override fun onCreateModule(path: String, project: Project): Module? {
        TODO("Not yet implemented")
    }

    override fun onCreateBuilder(path: String, module: Module): Builder? {
        TODO("Not yet implemented")
    }
}