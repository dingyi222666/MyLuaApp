package com.dingyi.myluaapp.build.api.internal

import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.internal.tasks.TaskStateInternal
import com.dingyi.myluaapp.build.api.sepcs.Spec
import com.dingyi.myluaapp.build.util.Path

interface TaskInternal: Task {


    fun getOnlyIf(): Spec<in TaskInternal>


    override fun getState(): TaskStateInternal


    fun getIdentityPath(): Path
}