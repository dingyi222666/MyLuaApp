package com.dingyi.myluaapp.build.api.builder

import com.dingyi.myluaapp.build.api.task.Task

interface Builder {

    fun after(task: Task, afterTask: Task)

    fun before(task: Task, beforeTask: Task)

    fun getTasks(): List<Task>
    fun clean(): List<Task>
    fun sync(): List<Task>
    fun getTask(name: String): Task
}