package com.dingyi.myluaapp.builder.api.internal.state

import com.dingyi.myluaapp.builder.api.internal.BuilderInternal
import com.dingyi.myluaapp.builder.api.internal.TaskInternal
import com.dingyi.myluaapp.builder.api.state.TaskState
import kotlin.properties.Delegates

class DefaultTaskState
    (private val task:TaskInternal):TaskStateInternal {

    override var onStateChange: (TaskState.State) -> Unit = {}

    private var lastOutputText = ""

    private var _state by Delegates.observable(TaskState.State.IDLE, onChange = { _, _, newValue ->
        output(buildStateMessage())
        onStateChange(newValue)
    })

    override fun start() {
        _state = TaskState.State.STATED
    }

    private fun buildStateMessage():String {
        return "> Task :${task.getProject().name}:${task.name} $_state"
    }

    override fun skip() {
        _state = TaskState.State.SKIPPED
    }

    override fun upToDate() {
        _state = TaskState.State.`UP-TO-DATE`
    }

    override fun output(string: String) {
        task.getBuilder().logVerbose("Task Output",string)
        lastOutputText = string
    }



    override fun busy(busy: Boolean) {
        _state = TaskState.State.BUSYED
    }

    override fun finish() {
        _state = TaskState.State.FINISHED
    }

    override fun getState(): TaskState.State {
        return _state
    }

    override fun getLastOutputText(): String {
       return lastOutputText
    }
}