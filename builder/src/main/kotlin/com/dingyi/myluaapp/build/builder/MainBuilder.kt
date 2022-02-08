package com.dingyi.myluaapp.build.builder

import android.util.Log
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.service.ServiceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import java.util.concurrent.CancellationException

class MainBuilder(
    private val initPath: String,
    private val logger: ILogger,
    private val repository: ServiceRepository,
    private val mavenRepository: MavenRepository
) : MainBuilder {

    private var project: Project? = null


    private var runJob: Job? = null

    fun init() {

        project = repository.onCreateProject(initPath, this)


    }

    override fun getLogger(): ILogger {
        return logger
    }

    override fun getProject(): Project {
        return project ?: error("null for project")
    }

    override fun build(command: String) {


        logger.info("Init Project")

        val commands = command.split(" ")

        if (commands[0] == "build") {
            project?.getCache()?.putCache("build_mode", commands[1])
        }

        project?.init()

        logger.info("Start Build...")


        logger.info("\n")


        runJob = project?.getRunner()?.run(commands[0])


    }

    override fun getServiceRepository(): ServiceRepository {
        return repository
    }

    override fun stop() {
        runJob?.cancelChildren(CancellationException("Stop Build"))
        runJob = null
        project?.getCache()?.close()
        println("stop!")
    }

    override fun getMavenRepository(): MavenRepository {
        return mavenRepository
    }
}