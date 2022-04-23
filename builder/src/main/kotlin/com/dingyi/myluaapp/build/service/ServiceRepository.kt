package com.dingyi.myluaapp.build.service

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.service.HookService
import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.build.api.service.ServiceRepository
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.convertObject
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.google.gson.Gson
import java.io.File

class ServiceRepository : ServiceRepository {

    private val services = mutableListOf<Service>()

    private val hookServices = mutableListOf<HookService>()


    override fun loadService(className: String): Service {
        return runCatching {
            val targetClass = Class.forName(className)

            val instance = targetClass.newInstance().convertObject<Service>()

            if (instance is HookService) hookServices.add(instance) else services.add(instance)

            instance
        }.onFailure {
            it.printStackTrace()
        }.getOrNull() ?: error("")
    }


    override fun addService(service: Service) {
        if (service is HookService) hookServices.add(service) else services.add(service)

    }

    override fun init() {
        val loadServices =
            Gson().fromJson(
                File("${Paths.buildPath}/loadservices.json").readText(),
                getJavaClass<Map<String, List<Map<String, String?>>>>()
            )["services"]


        runCatching {

            loadServices?.forEach { params ->
                val className = params["class"] ?: ""

                loadService(className)
            }
        }.onFailure {

        }

    }

    override fun getServices(): List<Service> {
        return services
    }

    override fun shutdown() {
        services.clear()
    }


    override fun onCreateProject(path: String, builder: MainBuilder): Project? {
        services.forEach {
            val tmp = it.onCreateProject(path, builder)
            if (tmp != null) {
                return hookServices.fold(tmp) { acc, hookService -> hookService.onCreateProject(acc) }
            }
        }
        return null
    }

    override fun onCreateModule(path: String, project: Project): Module? {
        services.forEach {
            val tmp = it.onCreateModule(path, project)
            if (tmp != null) {
                return hookServices.fold(tmp) { acc, hookService -> hookService.onCreateModule(acc) }
            }
        }
        return null
    }

    override fun onCreateBuilder(path: String, module: Module): Builder? {
        services.forEach {
            val tmp = it.onCreateBuilder(path, module)
            if (tmp != null) {
                return hookServices.fold(tmp) { acc, hookService -> hookService.onCreateBuilder(acc) }
            }
        }
        return null
    }
}