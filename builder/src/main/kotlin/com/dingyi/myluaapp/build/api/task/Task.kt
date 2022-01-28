package com.dingyi.myluaapp.build.api.task

import com.dingyi.myluaapp.build.api.project.Module

interface Task {
    val name: String

    suspend fun prepare()

    suspend fun run()


    fun getOutputString(module: Module,outString: String):String {
        return "> Task :${module.name}:$name $outString"
    }


}