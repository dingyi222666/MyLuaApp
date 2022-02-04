package com.dingyi.myluaapp.build.api.builder

import com.dingyi.myluaapp.build.api.Task


interface Builder {

    fun dependsOn(task:Task,dependsTask: Task)

    fun getTasks(): List<Task>
    fun clean(): List<Task>
    fun sync(): List<Task>
    fun getTaskByName(name: String): Task
}