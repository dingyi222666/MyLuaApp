package com.dingyi.myluaapp.builder.api.internal.task

import com.dingyi.myluaapp.builder.api.Action
import com.dingyi.myluaapp.builder.api.Builder
import com.dingyi.myluaapp.builder.api.Project
import com.dingyi.myluaapp.builder.api.file.TaskInputs
import com.dingyi.myluaapp.builder.api.file.TaskOutputs
import com.dingyi.myluaapp.builder.api.internal.TaskInternal
import com.dingyi.myluaapp.builder.api.task.Task

abstract class DefaultTask: TaskInternal {

    override val name: String
        get() = "default"

    private val allAction = mutableListOf<Action<Task>>()

    override fun execute() {
        allAction.forEach {
            it.execute(this)
        }
    }

    override fun getActions(): List<Action<Task>> {
        return allAction
    }

    override fun addAction(action: Action<Task>) {
       allAction.add(action)
    }

    override fun getInputs(): TaskInputs {
        TODO("Not yet implemented")
    }

    override fun getOutputs(): TaskOutputs {
        TODO("Not yet implemented")
    }

    override fun getBuilder(): Builder {
        TODO("Not yet implemented")
    }

    override fun setGroup(name: String) {
        TODO("Not yet implemented")
    }

    override fun getGroup(name: String) {
        TODO("Not yet implemented")
    }

    override fun getProject(): Project {
        TODO("Not yet implemented")
    }
}