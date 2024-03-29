package com.dingyi.myluaapp.build.default

import android.util.Log
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.scheduler.Scheduler
import com.dingyi.myluaapp.build.api.Task
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


class DefaultScheduler(
    private val project: Project
) : Scheduler {

    override fun run(type: String): Job {


        val builder = project.getBuilder()

        builder.init()

        val tasks = when (type) {
            "clean" -> builder.clean()
            "build" -> builder.getTasks()
            "sync" -> builder.sync()
            else -> builder.getTasks()
        }



        Log.e("tasks", "$tasks")


        val job = SupervisorJob()
        val coroutineScope = CoroutineScope(Dispatchers.IO + job)



        coroutineScope.launch(coroutineScope.coroutineContext) {
            delay(100)
            project.getLogger().info("run tasks [:$type] in project:${project.getPath()}")

            project.getLogger().info("\n")

            val startTime = System.currentTimeMillis()

            runCatching {
                withContext(Dispatchers.IO) {
                    for (task in tasks) {
                        when (task.prepare()) {
                            Task.State.INCREMENT, Task.State.DEFAULT -> task.run()
                            else -> {}
                        }
                    }
                }
            }.onFailure {
                Log.e("build error",it.stackTraceToString())
                project.getLogger().error(it.stackTraceToString())

                project.getLogger().error("FAILURE: Build failed with an exception.")
                endBuild(System.currentTimeMillis() - startTime, false)
            }.onSuccess {
                endBuild(System.currentTimeMillis() - startTime)
            }

            builder.clear()
        }

        return job

    }

    private fun formatTime(millis: Long): String {


        val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        val milliseconds = millis % 1000

        return when {
            hours > 0 -> {
                "$hours h, $minutes min, $seconds sec,$milliseconds ms"
            }
            hours == 0L && minutes > 0 -> {
                "$minutes min, $seconds sec,$milliseconds ms"
            }
            hours == 0L && minutes == 0L && seconds > 0 -> {
                "$seconds sec,$milliseconds ms"
            }
            hours == 0L && minutes == 0L && seconds == 0L && milliseconds > 0 -> {
                "$milliseconds ms"
            }
            else -> "$minutes min, $seconds sec,$milliseconds ms"
        }


    }

    private fun endBuild(time: Long, buildStatus: Boolean = true) {
        project.getLogger().info("\n")

        if (buildStatus) {
            project.getLogger().info(
                "BUILD SUCCESSFUL IN ${formatTime(time)}"
            )
        } else {
            project.getLogger().error(
                "BUILD FAILED IN ${formatTime(time)}"
            )
        }


        project.getMainBuilder().stop()
    }
}