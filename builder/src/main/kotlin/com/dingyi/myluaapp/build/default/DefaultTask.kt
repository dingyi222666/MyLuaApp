package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.file.TaskInput
import kotlin.properties.Delegates

abstract class DefaultTask(
     val applyModule: Module
) : Task {
    override val name: String
        get() = "DEFAULT_TASK"


    protected var isIncremental by Delegates.notNull<Boolean>()


    override suspend fun prepare():Task.State  {
        if (getTaskInput().getAllInputFile().isEmpty()) {
            return Task.State.SKIPPED
        }

         isIncremental = getTaskInput().isIncremental()

        return if (isIncremental) {
            if (getTaskInput().getIncrementalInputFile().isEmpty()) {
                Task.State.`UP-TO-DATE`
            } else {
                Task.State.INCREMENT
            }
        } else {
            Task.State.DEFAULT
        }

    }


    private lateinit var taskInput: TaskInput

    override fun getTaskInput(): TaskInput {
        if (!::taskInput.isInitialized) {
            taskInput = DefaultTaskInput(this)
        }
        return taskInput
    }

}
