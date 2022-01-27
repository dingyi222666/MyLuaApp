package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task

open class DefaultBuilder(
    private val module: Module
) : Builder {

    protected val syncTasks = mutableListOf<Task>()

    protected val cleanTasks = mutableListOf<Task>()

    protected val buildTasks = mutableListOf<Task>()

    private val DEFAULT_TASK = DefaultTask(module)

    override fun after(task: Task, afterTask: Task) {
        arrayOf(buildTasks,cleanTasks,syncTasks).forEach {
            if (it.contains(afterTask)) {
                it.add((it.indexOf(afterTask)+1).coerceAtLeast(0),task)
            }
        }
    }

    override fun before(task: Task, beforeTask: Task) {
        arrayOf(buildTasks,cleanTasks,syncTasks).forEach {
            if (it.contains(beforeTask)) {
                it.add((it.indexOf(beforeTask)).coerceAtLeast(0),task)
            }
        }
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

    override fun getTask(name: String): Task {
        return buildTasks.filter { it.name == name }
            .getOrNull(0) ?: syncTasks.filter { it.name == name }
            .getOrNull(0) ?: cleanTasks.filter { it.name == name }
            .getOrNull(0) ?: DEFAULT_TASK
    }
}