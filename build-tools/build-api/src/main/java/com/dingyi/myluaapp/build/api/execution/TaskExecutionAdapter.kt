package com.dingyi.myluaapp.build.api.execution

import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.api.tasks.TaskState


/**
 * A [TaskExecutionListener] adapter class for receiving task execution events.
 *
 * The methods in this class are empty. This class exists as convenience for creating listener objects.
 */
open class TaskExecutionAdapter : TaskExecutionListener {
    override fun beforeExecute(task: Task) {}
    override fun afterExecute(task: Task, state: TaskState) {}
}
