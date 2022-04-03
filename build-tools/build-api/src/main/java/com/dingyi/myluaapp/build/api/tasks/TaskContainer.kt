package com.dingyi.myluaapp.build.api.tasks

import com.dingyi.myluaapp.build.api.Task

interface TaskContainer {

    fun findByPath(path: String): Task?

    fun getByPath(path: String): Task


    fun register(name:String,clazz: Class<out Task>)

    fun create(name: String): Task

    fun create(name: String, vararg args: Any): Task

}