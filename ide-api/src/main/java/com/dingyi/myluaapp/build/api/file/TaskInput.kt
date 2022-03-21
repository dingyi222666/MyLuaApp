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

    fun bindOutputFiles(inputFile: InputFile,outputFile: List<File>)


    /**
     * Check the task input isIncremental
     */
    suspend fun isIncremental():Boolean

    /**
     * Return incremental input file
     */
    fun getIncrementalInputFile():List<InputFile>


    fun addOutputDirectory(file: File)


    fun getOutputDirectory():List<File>

    /**
     * Snapshot input and output file
     */
    fun snapshot()

    fun transformDirectoryToFile()

}