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


    override fun prepare() {
        allModule.forEach {
            Log.e("tasks", "${it.getBuilder().getTasks()}")

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

    override suspend fun run() {
        val job = Job()

        val coroutineDispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()

        val coroutineScope = CoroutineScope(coroutineDispatcher + job)

        Log.e("tasks for run", "$allTask")

        Log.e("test", "start for paralleltask")

        for (tasks in allTask) {
            Log.e("for", "$tasks")
            coroutineScope.launch {
                Log.e("start", "$tasks")

                tasks.forEach {
                    Log.e("task", "$it")

                    it.prepare()
                    it.run()
                }
            }
        }


        job.join()
        coroutineDispatcher.close()
    }
}