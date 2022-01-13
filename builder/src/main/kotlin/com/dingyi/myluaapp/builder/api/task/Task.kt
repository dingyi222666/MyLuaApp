package com.dingyi.myluaapp.builder.api.task

import com.dingyi.myluaapp.builder.api.Action
import com.dingyi.myluaapp.builder.api.Builder
import com.dingyi.myluaapp.builder.api.Project
import com.dingyi.myluaapp.builder.api.file.TaskInputs
import com.dingyi.myluaapp.builder.api.file.TaskOutputs
import java.io.File

interface Task {

    val name:String

    fun getActions(): List<Action<Task>>

    fun addAction(action: Action<Task>)

    fun getInputs(): TaskInputs

    fun getOutputs(): TaskOutputs


    fun getBuilder(): Builder

    //default group
    fun setGroup(name:String)

    fun getGroup(name: String)

    fun getProject():Project

}