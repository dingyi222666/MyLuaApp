package com.dingyi.myluaapp.build.api

interface Task {
    val name: String

    suspend fun prepare(): State

    suspend fun run()


    fun getOutputString(module: Module, state: State?): String {
        return "> Task :${module.name}:$name ${state?.name ?: ""}"
    }

    enum class State {
        `UP-TO-DATE`, SKIPPED, `NO-SOURCE`,INCREMENT,DEFAULT
    }


}