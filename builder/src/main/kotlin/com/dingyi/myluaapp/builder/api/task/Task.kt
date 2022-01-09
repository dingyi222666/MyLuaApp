package com.dingyi.myluaapp.builder.api.task

import com.dingyi.myluaapp.builder.api.Action
import com.dingyi.myluaapp.builder.api.Builder
import com.dingyi.myluaapp.builder.api.file.TaskInputs
import com.dingyi.myluaapp.builder.api.file.TaskOutputs
import java.io.File

interface Task {


    fun getActions(): List<Action<out Task>>

    fun addAction(action: Action<in Task>)

    fun getInputs(): TaskInputs

    fun getOutputs(): TaskOutputs

    fun getCacheFile(file: File): File

    fun getBuilder(): Builder

}