package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.default.DefaultTask

class ResolveDependencyMavenMetaData(private val module: Module) : DefaultTask(module) {


    override val name: String
        get() = this.javaClass.simpleName

    override suspend fun prepare(): Task.State {
        val mavenDependencyList = module
            .getProject()
            .getAllDependency()
            .filterIsInstance<MavenDependency>()
            .map {  }



        return super.prepare()
    }

    override suspend fun run() {
        TODO("Not yet implemented")
    }
}