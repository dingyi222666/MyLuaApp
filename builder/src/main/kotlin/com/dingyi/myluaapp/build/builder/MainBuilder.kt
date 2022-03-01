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

    private lateinit var buildCompleteListener: () -> Unit
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


        getLogger().info(
            """
            Welcome to MyLuaApp Build Tools! The Build Tools is running lasted version(0.0.1).
            
            MyLuaApp Build Tools is based kotlin,a light and increment build tools to build MyLuaApp Project.
            
            """.trimIndent()
        )

        logger.info("index all module...")

        val commands = command.split(" ")

        if (commands[0] == "build") {
            project?.getCache()?.putCache("build_mode", commands[1])
        }

        project?.init()

        logger.info("\n")


        logger.info("run ${commands[0]} for all module...")

        logger.info("\n")

        runJob = project?.getRunner()?.run(commands[0])



    }

    override fun getServiceRepository(): ServiceRepository {
        return repository
    }

    override fun stop() {
        getLogger().info("BUILD COMPLETED FLAG")
        runJob?.cancel(CancellationException("Stop Build"))
        runJob?.cancelChildren(CancellationException("Stop Build"))
        runJob = null
        project?.getCache()?.close()
        mavenRepository.clear()
        buildCompleteListener.invoke()
        println("stop!")
    }

    override fun getMavenRepository(): MavenRepository {
        return mavenRepository
    }

    override fun setBuildCompleteListener(listener: () -> Unit) {
        buildCompleteListener = listener
    }
}