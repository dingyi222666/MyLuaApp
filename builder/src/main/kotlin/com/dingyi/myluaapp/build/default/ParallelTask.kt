package com.dingyi.myluaapp.build.default

import android.util.Log
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class ParallelTask(
    private val mode: String = "build"
) : Task {
    override val name: String
        get() = allModule.joinToString(
            separator = "",
            prefix = "ParallelTask[",
            postfix = "]"
        ) { "'${it.name}'" }

    private val allModule = mutableListOf<Module>()


    private val allTask = mutableListOf<List<Task>>()

    fun addModule(module: Module) {
        allModule.add(module)
    }


    override suspend fun prepare() = withContext(Dispatchers.IO) {
        allModule.forEach {
            when (mode) {
                "build" -> it.getBuilder().getTasks()
                "clean" -> it.getBuilder().clean()
                "sync" -> it.getBuilder().sync()
                else -> it.getBuilder().getTasks()
            }.apply {
                allTask.add(this)
            }
        }
    }

    override fun toString(): String {
        return name
    }

    override suspend fun run() = withContext(Dispatchers.IO) {
        launch(Dispatchers.IO) {
            for (tasks in allTask) {
                launch(Dispatchers.IO) {
                    for (task in tasks) {
                        task.prepare()
                        task.run()
                    }
                }
            }
        }.join()

    }
}