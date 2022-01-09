package com.dingyi.myluaapp.builder.api.file

import java.io.File
import java.io.InputStream

interface TaskInputs {

    fun file(file: File)

    fun files(vararg file: File)

    fun skip(): Boolean

    fun getInputFiles(): List<TaskFile>
}