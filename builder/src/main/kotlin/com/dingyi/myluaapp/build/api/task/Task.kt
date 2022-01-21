package com.dingyi.myluaapp.build.api.task

interface Task {
    val name:String

    fun prepare()

    fun run()


}