package com.dingyi.myluaapp.build.modules.android.tasks

import android.util.Log
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import kotlinx.coroutines.delay

class World(private val module: Module):Task {
    override val name: String
        get() = "World"

    override fun prepare() {
        Log.e("fuck","prepare World")
    }

    override suspend fun run() {
        Log.e("fuck","Run World")
        delay(1000)
        Log.e("fuck","Run World Success")
        delay(1000)
    }
}