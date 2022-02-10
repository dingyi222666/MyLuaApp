package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.TaskInput

abstract class DefaultTask(
     val applyModule: Module
) : Task {
    override val name: String
        get() = "DEFAULT_TASK"

    override suspend fun prepare() = Task.State.DEFAULT

    override suspend fun run() {

    }


    private lateinit var taskInput: TaskInput

    override fun getTaskInput(): TaskInput {
        if (!::taskInput.isInitialized) {
            taskInput = DefaultTaskInput(this)
        }
        return taskInput
    }

}
