package com.dingyi.myluaapp.build.api.file

import java.io.File

interface TaskOutputs {

    fun addFile(file: File)

    fun addFiles(vararg file: File)


    fun addFormInputFile(inputFile: InputFile,file: File)

    fun getOutputs():List<OutputFile>


    fun isIncremental():Boolean


}