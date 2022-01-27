package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.task.Task

class DefaultProjectBuilder(
    private val project: Project
) : Builder {

    override fun after(task: Task, afterTask: Task) {
        project.getModules().forEach {
            it.getBuilder().after(task, afterTask)
        }
    }

    override fun before(task: Task, beforeTask: Task) {
        project.getModules().forEach {
            it.getBuilder().before(task, beforeTask)
        }
    }

    override fun getTasks(): List<Task> {
        val weight = project.createModulesWeight()
        println(weight)
        val keys = weight.keys.toList().sortedDescending()
        val list = mutableListOf<Task>()
        keys.forEach { it ->
            val parallelTask = ParallelTask("build")
            weight[it]?.forEach {
                parallelTask.addModule(it)
            }
            list.add(parallelTask)
        }
        return list
    }

    override fun clean(): List<Task> {
        val weight = project.createModulesWeight()
        val keys = weight.keys.toList().sortedDescending()
        val list = mutableListOf<Task>()
        keys.forEach { it ->
            val parallelTask = ParallelTask("clean")

            weight[it]?.forEach {
                parallelTask.addModule(it)
            }
            list.add(parallelTask)
        }
        return list
    }

    override fun sync(): List<Task> {
        val weight = project.createModulesWeight()
        val keys = weight.keys.toList().sortedDescending()
        val list = mutableListOf<Task>()
        keys.forEach { it ->
            val parallelTask = ParallelTask("sync")
            weight[it]?.forEach {
                parallelTask.addModule(it)
            }
            list.add(parallelTask)
        }
        return list
    }

    override fun getTask(name: String): Task {
        return project.getMainModule().getBuilder().getTask(name)
    }
}