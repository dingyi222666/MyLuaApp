package com.dingyi.myluaapp.plugin.runtime.build

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.build.BuildMain
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.plugin.api.build.BuildService
import com.dingyi.myluaapp.plugin.api.project.Project
import com.google.gson.Gson
import java.io.File

class BuildService() : BuildService<com.dingyi.myluaapp.plugin.runtime.build.BuildService.ServicesBean.Service> {


    private lateinit var serviceBean:ServicesBean


    private val buildMain = BuildMain(MainApplication.instance)

    private fun readAllService() {
        val file = File(Paths.assetsDir,"res/build/loadservices.json")
        serviceBean = Gson().fromJson(file.readText(), getJavaClass())
    }

    private fun saveService() {
        val file = File(Paths.assetsDir,"res/build/loadservices.json")
        file.writeText(Gson().toJson(serviceBean))

        buildMain.refreshService()
    }

    override fun addBuildService(type: ServicesBean.Service) {
        if (!this::serviceBean.isInitialized) {

            readAllService()
        }

        serviceBean.services.add(type)

        saveService()
    }

    override fun getAllBuildService(): List<ServicesBean.Service> {
        if (!this::serviceBean.isInitialized) {

            readAllService()
        }

        return serviceBean.services
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

    data class ServicesBean(
        var services: MutableList<Service>
    ) {
        data class Service(var path: String, var className: String)
    }

    override fun stop() {
        buildMain.close()
    }
}