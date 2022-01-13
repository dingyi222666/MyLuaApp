package com.dingyi.myluaapp.builder.api.internal

import com.dingyi.myluaapp.builder.api.task.Task

interface TaskInternal:Task {

    fun execute()
}