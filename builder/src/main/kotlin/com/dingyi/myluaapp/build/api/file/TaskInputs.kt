package com.dingyi.myluaapp.build.api.file

import java.io.File

interface TaskInputs {

    fun addInputFile(file:File)

    fun addInputDirectory(file: File)

    fun getAllInputFile():List<InputFile>

    fun getInputFile(file: File):InputFile

    fun getInputFileFormDirectory(file: File):List<InputFile>

    fun transformDirectoryToFile(block:(File)->Boolean)

    fun readLastConfig()
}