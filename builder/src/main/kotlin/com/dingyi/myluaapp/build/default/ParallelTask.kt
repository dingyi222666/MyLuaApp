package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import kotlinx.coroutines.*

class ParallelTask : Task {
    override val name: String
        get() = "Parallel_Task"

    private val allModule = mutableListOf<Module>()


    private val allTask = mutableListOf<List<Task>>()

    fun addModule(module: Module) {
        allModule.add(module)
    }

    var mode = "build"

    override fun prepare() {
        allModule.forEach {
            allTask.add(
                when (mode) {
                    "build" -> it.getBuilder().getTasks()
                    "clean" -> it.getBuilder().clean()
                    "sync" -> it.getBuilder().sync()
                    else -> it.getBuilder().getTasks()
                }
            )
        }
    }

    override suspend fun run() {
        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.IO + job)


        for (tasks in allTask) {

            coroutineScope.launch {
                tasks.forEach {
                    it.prepare()
                    it.run()
                }
            }.start()
        }

        job.join()

    }
}