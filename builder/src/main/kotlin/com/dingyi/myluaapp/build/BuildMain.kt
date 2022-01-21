package com.dingyi.myluaapp.build

import android.app.Application
import android.app.Service
import com.dingyi.myluaapp.build.builder.MainBuilder
import com.dingyi.myluaapp.build.log.Logger
import com.dingyi.myluaapp.build.service.ServiceRepository

class BuildMain(
    private val application: Application
) {

    private val logger = Logger(application)

    private val repository = ServiceRepository()

    init {
        repository.init()
    }

    fun build(path:String,command:String) {
        return MainBuilder(path,logger,repository).apply {
            init()
        }.build("build $command")
    }

    fun clean(path: String) {
        return MainBuilder(path,logger,repository).apply {
            init()
        }.build("clean")
    }

    fun sync(path: String) {
        return MainBuilder(path,logger,repository).apply {
            init()
        }.build("sync")
    }

    fun close() {
        repository.shutdown()

    }

}



