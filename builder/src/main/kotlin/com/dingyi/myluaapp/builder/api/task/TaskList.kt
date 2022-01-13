package com.dingyi.myluaapp.builder.api.task

interface TaskList {


    fun add(name: String, task: Task)

    fun before(task: Task, beforeTask: Task)

    fun after(task: Task, afterTask: Task)


    fun replace(name: String, replaceTask: Task)

    fun remove(name: String)

    fun findByName(name: String): Task

    fun getAllTask():List<Task>

    fun getTasksByGroup(name:String):List<Task>


}
