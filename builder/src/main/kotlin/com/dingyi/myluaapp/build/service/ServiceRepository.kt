package com.dingyi.myluaapp.build.service

import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.build.api.service.ServiceRepository
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dalvik.system.DexClassLoader
import java.io.File

class ServiceRepository : ServiceRepository {

    private val services = mutableListOf<Service>()

    override fun loadService(className: String): Service {
        return runCatching {
            val targetClass = Class.forName(className)
            (targetClass.newInstance() as Service).apply {
                services.add(this)
            }
        }.onFailure {
            it.printStackTrace()
        }
            .getOrNull() ?: error("")

    }

    override fun loadService(className: String, dexPath: String): Service {
        return runCatching {
            val classLoader =
                DexClassLoader(dexPath, "/sdcard", "/sdcard", ClassLoader.getSystemClassLoader())
            (classLoader.loadClass(className).newInstance() as Service).apply {
                services.add(this)
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull() ?: error("")

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
                val dexPath = params["dexPath"]

                if (dexPath == null) {
                    loadService(className)
                } else {
                    loadService(className, dexPath.toString())
                }

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
}