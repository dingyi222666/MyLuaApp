package com.dingyi.myluaapp.build.default

import android.util.Log
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.runner.Runner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

            project.getLogger().info("run tasks [$type] in project:${project.getPath()}")

            project.getLogger().info("\n")

            val startTime = System.currentTimeMillis()

            runCatching {
                Log.v("test","print main test start")
                for (task in tasks) {
                    task.prepare()
                    task.run()
                }
                Log.v("test","print main test end")
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