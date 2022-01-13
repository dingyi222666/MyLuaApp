package com.dingyi.myluaapp.builder.api.internal.state

import com.dingyi.myluaapp.builder.api.state.TaskState

interface TaskStateInternal: TaskState {

    var onStateChange:(TaskState.State) -> Unit

    fun start()

    fun skip()

    fun upToDate()

    fun busy(busy:Boolean)

    fun finish()

}