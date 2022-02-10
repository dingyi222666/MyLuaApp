package com.dingyi.myluaapp.build.default


import com.dingyi.myluaapp.build.api.file.InputFile

import java.io.File

class DefaultInputFile(
    private val input: DefaultTaskInput<*>,
    private val path: File,
    private val sourceDirectory: String
) : InputFile {

    override fun getSnapShotHash(): String {
        return input.getSnapShotHash(path)
    }

    override fun toFile(): File {
        return path
    }

    override fun getFileHash(): String {
        return path.lastModified().toString()
    }

    override fun getBindOutputFile(): List<File>? {
        return input.getBindOutputFile(this)
    }

    override fun getSourceDirectory(): String {
        return sourceDirectory
    }


}