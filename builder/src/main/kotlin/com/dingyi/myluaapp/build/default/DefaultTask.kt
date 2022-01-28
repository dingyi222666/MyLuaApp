package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task

open class DefaultTask(
    private val module: Module
) : Task {
    override val name: String
        get() = "DEFAULT_TASK"

    override suspend fun prepare() {

    }

    override suspend fun run() {

    }

}
