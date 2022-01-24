package com.dingyi.myluaapp.build.api.runner

import kotlinx.coroutines.Job

interface Runner {

    fun run(type: String): Job
}