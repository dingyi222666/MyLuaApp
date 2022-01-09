package com.dingyi.myluaapp.builder.api.file

import java.io.File
import java.io.InputStream


interface TaskOutputs {

    fun file(file: File)

    fun files(vararg file: File)

    fun getInputFiles(): List<TaskFile>

}