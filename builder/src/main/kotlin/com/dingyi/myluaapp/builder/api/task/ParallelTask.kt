package com.dingyi.myluaapp.builder.api.task

interface ParallelTask : Task {
    fun getParallelTask():Set<Task>
}
