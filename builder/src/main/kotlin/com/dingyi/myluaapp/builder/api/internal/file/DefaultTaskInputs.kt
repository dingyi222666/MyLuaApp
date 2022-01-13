package com.dingyi.myluaapp.builder.api.internal.file

import com.dingyi.myluaapp.builder.api.file.TaskFile
import com.dingyi.myluaapp.builder.api.file.TaskInputs
import com.dingyi.myluaapp.builder.api.internal.BuilderInternal
import java.io.File

class DefaultTaskInputs
    (builder: BuilderInternal):TaskInputs {
    override fun file(file: File) {

    }

    override fun files(vararg file: File) {
        TODO("Not yet implemented")
    }

    override fun skip(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getInputFiles(): List<TaskFile> {
        TODO("Not yet implemented")
    }

    override fun getIncrementFiles(): List<TaskFile> {
        TODO("Not yet implemented")
    }
}