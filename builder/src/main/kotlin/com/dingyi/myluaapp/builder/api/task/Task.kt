package com.dingyi.myluaapp.builder.api.task

interface Task {
    val name:String

    fun prepare()

    fun run()


}