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


    override suspend fun prepare(): Task.State {
        throw RuntimeException("Stub!");
    }


    override fun getTaskInput(): TaskInput {
        throw RuntimeException("Stub!");
    }

}
