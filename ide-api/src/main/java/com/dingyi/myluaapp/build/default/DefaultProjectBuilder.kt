package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.Task

class DefaultProjectBuilder(
    private val project: Project
) : Builder {


    override fun dependsOn(task: Task, dependsTask: Task) {
        for (module in project.getAllModule()) {
            module.getBuilder().dependsOn(task, dependsTask)
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

    override fun getTaskByName(name: String): Task {
        return project.getMainModule().getBuilder().getTaskByName(name)
    }

    override fun init() {
        project.getAllModule().forEach {
            it.getBuilder().init()
        }
    }

    override fun clear() {
        project.getAllModule().forEach {
            it.getBuilder().clear()
        }
        System.gc()
    }

    override fun onInit(function: Runnable) {
        throw CompileError("not support onInit")
    }
}