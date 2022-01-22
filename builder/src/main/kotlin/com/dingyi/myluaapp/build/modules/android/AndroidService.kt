package com.dingyi.myluaapp.build.modules.android

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.default.DefaultModule
import com.dingyi.myluaapp.build.default.DefaultProject

class AndroidService:Service {


    override fun onCreateProject(path: String,builder: MainBuilder): Project? {
        return DefaultProject(path,builder)
    }

    override fun onCreateModule(path: String, project: Project): Module? {
        return DefaultModule(project,path)
    }

    override fun onCreateBuilder(path: String, module: Module): Builder? {
        return DefaultBuilder(module)
    }
}