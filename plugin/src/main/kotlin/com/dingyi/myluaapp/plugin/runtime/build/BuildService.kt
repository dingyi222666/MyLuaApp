package com.dingyi.myluaapp.plugin.runtime.build

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.build.BuildMain
import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.api.build.BuildService
import com.dingyi.myluaapp.plugin.api.project.Project
import com.google.gson.Gson
import java.io.File

class BuildService() : BuildService<Service> {



    private val buildMain = BuildMain(MainApplication.instance)


    override fun addBuildService(type: Service) {
        buildMain.getServiceRepository().addService(type)
    }

    override fun getAllBuildService(): List<Service> {
        return buildMain.getServiceRepository().getServices()
    }

    override fun build(project: Project, command: String) {
        val array = command.split(" ")
        when (array[0]) {
            "build" -> buildMain.build(
                project.path.path,
                array.slice(1..array.lastIndex).joinToString(separator = "")
            )
            "clean" -> buildMain.clean(project.path.path)
            "sync" -> buildMain.sync(project.path.path)
        }
    }



    override fun stop() {
        buildMain.close()
    }
}