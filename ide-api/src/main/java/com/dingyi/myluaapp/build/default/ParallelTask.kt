package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.TaskInput
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
        val module = allModule.getOrNull(0)
        val logger = module?.getLogger()
        launch {
            for (tasks in allTask) {
                launch(coroutineContext) {
                    for (task in tasks) {
                        withContext(Dispatchers.IO) {
                            val status = task.prepare()
                            logger?.info(task.getOutputString(module, status))
                            when (status) {
                                Task.State.INCREMENT, Task.State.DEFAULT -> task.run()
                                else -> {}
                            }
                        }
                    }
                }
            }
        }.join()

    }

    override fun getTaskInput(): TaskInput? {
        return null
    }
}