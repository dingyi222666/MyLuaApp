package com.dingyi.myluaapp.build.api.service

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project

interface Service {

    fun onCreateProject(path: String, builder: MainBuilder): Project?

    fun onCreateModule(path: String, project: Project): Module?

    fun onCreateBuilder(path: String, module: Module): Builder?
}