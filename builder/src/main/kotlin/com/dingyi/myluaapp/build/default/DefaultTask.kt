package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task

open class DefaultTask(
    private val module: Module
) : Task {
    override val name: String
        get() = "DEFAULT_TASK"

    override suspend fun prepare()  = Task.State.DEFAULT

    override suspend fun run() {

    }

}
