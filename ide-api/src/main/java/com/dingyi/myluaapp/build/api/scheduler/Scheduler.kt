package com.dingyi.myluaapp.build.api.scheduler

import kotlinx.coroutines.Job

interface Scheduler {

    fun run(type: String): Job
}