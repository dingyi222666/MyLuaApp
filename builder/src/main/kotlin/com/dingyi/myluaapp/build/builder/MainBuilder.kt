package com.dingyi.myluaapp.build.builder

import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.service.ServiceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.properties.Delegates

class MainBuilder(
    private val initPath: String,
    private val logger: ILogger,
    private val repository: ServiceRepository
) : MainBuilder {

    private var project: Project? = null


    private var runJob: Job? = null

    fun init() {

        repository.getServices().forEach {
            val targetProject = it.onCreateProject(initPath, this)

            if (targetProject != null) {
                project = targetProject
                return@forEach
            }
        }

        project?.init()


        println(project)
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
        runJob?.cancel("Stop Build")
        runJob = null
    }
}