package com.dingyi.myluaapp.build.modules.android.tasks

import android.util.Log
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import kotlinx.coroutines.delay

class Hello(private val module: Module): Task {
    override val name: String
        get() = "Hello"

    override fun prepare() {
        Log.e("fuck","prepare Hello")
    }

    override suspend fun run() {
        Log.e("fuck","Run Hello")
        delay(1000)
        Log.e("fuck","Run Success")
    }
}