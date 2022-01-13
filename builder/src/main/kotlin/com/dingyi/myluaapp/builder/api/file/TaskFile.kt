package com.dingyi.myluaapp.builder.api.file

import com.dingyi.myluaapp.builder.api.task.Task
import java.io.File
import java.io.InputStream
import java.io.OutputStream

interface TaskFile {

    fun getInput(): InputStream

    fun getOutputPath(): String

    fun skip(): Boolean

    fun getOutput():OutputStream

    fun copyCache(outputStream: OutputStream)

}