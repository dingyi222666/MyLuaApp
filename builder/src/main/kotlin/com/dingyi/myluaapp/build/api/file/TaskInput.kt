package com.dingyi.myluaapp.build.api.file

import android.provider.ContactsContract
import java.io.File

interface TaskInput {

    fun addInputFile(file:File)

    fun addInputDirectory(file: File)

    fun getAllInputFile():List<InputFile>

    fun getInputFile(file: File):InputFile

    fun getInputFileFormDirectory(file: File):List<InputFile>

    fun transformDirectoryToFile(block:(File)->Boolean)

    fun bindOutputFile(inputFile: InputFile,outputFile: File)

    fun bindOutputDirectory(inputDirectory: File,outputDirectory: File,match:(File,File)->Boolean)

    suspend fun isIncremental():Boolean

    fun getIncrementalInputFile():List<InputFile>



    fun addOutputDirectory(file: File)


    fun getOutputDirectory():List<File>

    fun snapshot()

}