package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import kotlinx.coroutines.*

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

        if (allTask.isEmpty()) {
            Task.State.`NO-SOURCE`
        } else {
            Task.State.DEFAULT
        }
    }

    override fun toString(): String {
        return name
    }

    override suspend fun run() = withContext(Dispatchers.IO) {
        launch {
            for (tasks in allTask) {
                launch(coroutineContext) {
                    for (task in tasks) {
                        withContext(Dispatchers.IO) {
                            when (task.prepare()) {
                                Task.State.INCREMENT, Task.State.DEFAULT -> task.run()
                                else -> {}
                            }
                        }
                    }
                }
            }
        }.join()

    }
}