package com.dingyi.myluaapp.builder.api.task

interface TaskList {


    fun add(name: String, task: Task)

    fun before(task: Task, beforeTask: Task)

    fun after(task: Task, afterTask: Task)

    fun parallel(task: Task, vararg parallelTask: Task): ParallelTask

    fun parallel(task: ParallelTask, vararg parallelTask: Task): ParallelTask

    fun replace(name: String, replaceTask: Task)

    fun remove(name: String)

    fun findByName(name: String): Task


}
