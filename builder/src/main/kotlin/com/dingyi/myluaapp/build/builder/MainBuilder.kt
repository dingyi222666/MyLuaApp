package com.dingyi.myluaapp.build.builder

import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.service.ServiceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import java.util.concurrent.CancellationException
import kotlin.properties.Delegates

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


        project?.init()

    }

    override fun getLogger(): ILogger {
        return logger
    }

    override fun getProject(): Project {
        return project ?: error("null for project")
    }

    override fun build(command: String) {


        val commands = command.split(" ")


        runJob = when (commands[0]) {
            "clean" -> project?.getRunner()?.run("clean")
            "build" -> {

                project?.getMainBuilderScript()?.put("build_mode", commands[1])
                println("type ${project?.getMainBuilderScript()?.get("build_mode")}")
                project?.getRunner()?.run("build")
            }
            "sync" -> project?.getRunner()?.run("sync")
            else -> project?.getRunner()?.run("")
        }

    }

    override fun getServiceRepository(): ServiceRepository {
        return repository
    }

    override fun stop() {
        runJob?.cancelChildren(CancellationException("Stop Build"))
        runJob = null
    }

    override fun getMavenRepository(): MavenRepository {
        return mavenRepository
    }
}