package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.common.ktx.checkNotNull

open class DefaultBuilder(
    private val module: Module
) : Builder {

    protected val syncTasks = mutableListOf<Task>()

    protected val cleanTasks = mutableListOf<Task>(CleanTask(module))

    protected val buildTasks = mutableListOf<Task>()

    private val onInitList = mutableListOf<Runnable>()

    override fun dependsOn(task: Task, dependsTask: Task) {
        arrayOf(buildTasks, cleanTasks, syncTasks).forEach {
            if (it.contains(dependsTask)) {
                it.add((it.indexOf(dependsTask) + 1).coerceAtLeast(0), task)
            }
        }
    }

    //Only call in sub class
    protected fun addTask(task: Task, mutableList: MutableList<Task>) {

        mutableList.add(task)
    }

    override fun getTasks(): List<Task> {
        return buildTasks
    }

    override fun clean(): List<Task> {
        return cleanTasks
    }

    override fun sync(): List<Task> {
        return syncTasks
    }


    override fun init() {
        onInitList.removeAll {
            it.run()
            true
        }
    }

    override fun getTaskByName(name: String): Task {
        return buildTasks.filter { it.name == name }
            .getOrNull(0) ?: syncTasks.filter { it.name == name }
            .getOrNull(0) ?: cleanTasks.filter { it.name == name }
            .getOrNull(0).checkNotNull()
    }

    override fun clear() {
        arrayOf(syncTasks, buildTasks, cleanTasks).forEach {
            it.clear()
        }
        System.gc()
    }

    override fun onInit(function: Runnable) {
        onInitList.add(function)
    }
}