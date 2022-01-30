package com.dingyi.myluaapp.build.default

import android.util.Log
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.runner.Runner
import com.dingyi.myluaapp.build.api.Task
import kotlinx.coroutines.*
import kotlin.math.roundToInt

class DefaultRunner(
    private val project: Project
) : Runner {

    override fun run(type: String): Job {


        val builder = project.getBuilder()

        val tasks = when (type) {
            "clean" -> builder.clean()
            "build" -> builder.getTasks()
            "sync" -> builder.sync()
            else -> builder.getTasks()
        }


        Log.e("tasks","$tasks")

        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.IO + job)

        coroutineScope.launch(Dispatchers.IO) {

            project.getLogger().info("run tasks [:$type] in project:${project.getPath()}")

            project.getLogger().info("\n")

            val startTime = System.currentTimeMillis()

            runCatching {
                for (task in tasks) {
                    when (task.prepare()) {
                        Task.State.INCREMENT, Task.State.DEFAULT -> task.run()
                        else -> {}
                    }
                }
            }.onFailure {
                project.getLogger().info("\n")
                project.getLogger().error(it.stackTraceToString())
                project.getLogger().info("\n")
                project.getLogger().error("FAILURE: Build failed with an exception.")
                endBuild(System.currentTimeMillis() - startTime,false)
            }.onSuccess {
                endBuild(System.currentTimeMillis() - startTime)
            }

        }

        return job

    }

    private fun endBuild(time: Long,buildStatus:Boolean = true) {
        project.getLogger().info("\n")
        val second = (time.toFloat() / 1000).roundToInt()
        if (buildStatus) {
            project.getLogger().info(
                "BUILD SUCCESSFUL IN ${second}s"
            )
        } else {
            project.getLogger().error(
                "BUILD FAILED IN ${second}s"
            )
        }
    }
}