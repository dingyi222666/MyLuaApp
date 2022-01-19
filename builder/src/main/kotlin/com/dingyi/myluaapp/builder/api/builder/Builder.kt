package com.dingyi.myluaapp.builder.api.builder

import com.dingyi.myluaapp.builder.api.task.Task

interface Builder {
    fun getTasks():List<Task>
    fun clean():List<Task>
    fun sync():List<Task>
}