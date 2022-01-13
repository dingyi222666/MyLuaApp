package com.dingyi.myluaapp.builder.api.state

interface TaskState {


    enum class State {
        `UP-TO-DATE`,BUSYED,FINISHED,SKIPPED,IDLE,STATED,
    }


    fun output(string: String)


    fun getState():State

    fun getLastOutputText():String

}