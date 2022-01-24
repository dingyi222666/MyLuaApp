package com.dingyi.myluaapp.build

import android.app.Application
import android.app.Service
import com.dingyi.myluaapp.build.builder.MainBuilder
import com.dingyi.myluaapp.build.log.Logger
import com.dingyi.myluaapp.build.service.ServiceRepository
import kotlin.RuntimeException

class BuildMain(
    private val application: Application
) {

    private var logger: Logger? = Logger(application)

    private val repository = ServiceRepository()

    private var nowBuilder: MainBuilder? = null

    init {
        repository.init()
    }

    private fun createLogger(): Logger {
        logger = logger ?: Logger(application)
        return logger ?: error("")
    }

    fun build(path: String, command: String) {
        return MainBuilder(path, createLogger(), repository).apply {
            init()
            nowBuilder = this
        }.build("build $command")
    }

    fun clean(path: String) {
        return MainBuilder(path, createLogger(), repository).apply {
            init()
            nowBuilder = this
        }.build("clean")
    }

    fun sync(path: String) {
        return MainBuilder(path, createLogger(), repository).apply {
            init()
            nowBuilder = this
        }.build("sync")
    }

    fun close() {
        nowBuilder?.stop()
        repository.shutdown()
        logger = null
    }


}


class CompileError(override val message: String) : RuntimeException(message)



