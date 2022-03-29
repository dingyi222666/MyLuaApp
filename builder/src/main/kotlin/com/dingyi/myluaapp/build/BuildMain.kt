package com.dingyi.myluaapp.build

import android.app.Application
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.builder.MainBuilder
import com.dingyi.myluaapp.build.dependency.repository.LocalMavenRepository
import com.dingyi.myluaapp.build.log.Logger
import com.dingyi.myluaapp.build.service.ServiceRepository
import com.dingyi.myluaapp.common.ktx.Paths
import kotlin.RuntimeException

class BuildMain(
    private val application: Application
) {

    private var logger: Logger? = Logger(application)

    private val repository = ServiceRepository()

    private var nowBuilder: MainBuilder? = null

    private val mavenRepository = LocalMavenRepository(Paths.localMavenDir, createLogger())

   init {
        repository.init()
    }

    private fun createLogger(): Logger {
        logger = logger ?: Logger(application)
        return logger ?: error("")
    }

    fun getBuilder(path: String) = MainBuilder(path,createLogger(),repository, mavenRepository)

    fun build(path: String, command: String) {

        if (nowBuilder != null) {
            nowBuilder?.stop()
            return
        }


        MainBuilder(path, createLogger(), repository, mavenRepository).apply {
            init()
            nowBuilder = this
            setBuildCompleteListener {
                nowBuilder = null
            }
        }.build("build $command")


    }

    fun clean(path: String) {

        if (nowBuilder != null) {
            nowBuilder?.stop()
            return
        }

        MainBuilder(path, createLogger(), repository, mavenRepository).apply {
            init()
            nowBuilder = this
            setBuildCompleteListener {
                nowBuilder = null
            }
        }.build("clean")


    }

    fun sync(path: String) {


        if (nowBuilder != null) {
            nowBuilder?.stop()
            return
        }

        MainBuilder(path, createLogger(), repository, mavenRepository).apply {
            init()
            nowBuilder = this
            setBuildCompleteListener {
                nowBuilder = null
            }
        }.build("sync")

    }

    fun syncAndReturnProject(path: String):Project {
        return MainBuilder(path, createLogger(), repository, mavenRepository).apply {
            init()
            nowBuilder = this
            setBuildCompleteListener {
                nowBuilder = null
            }
        }.sync()
    }

    fun close() {
        nowBuilder?.stop()

        logger?.close()
        logger = null
    }


    fun getServiceRepository() = repository

}





