package com.dingyi.myluaapp.builder.api.internal.file

import com.dingyi.myluaapp.builder.api.file.TaskFile
import com.dingyi.myluaapp.builder.api.internal.BuilderInternal
import com.dingyi.myluaapp.common.kts.toFile
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class DefaultTaskFile
    (
    private val file: File,
    private val builder: BuilderInternal
) : TaskFileInternal {


    override fun getSHA256RecordFile() {
        TODO("Not yet implemented")
    }


    override fun getInput(): InputStream {
        TODO("Not yet implemented")
    }

    override fun getOutputPath(): String {
        TODO("Not yet implemented")
    }

    override fun skip(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getOutput(): OutputStream {
        return getOutputPath().toFile().outputStream()
    }

    override fun copyCache(outputStream: OutputStream) {
        TODO("Not yet implemented")
    }
}