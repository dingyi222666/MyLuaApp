package com.dingyi.myluaapp.build.api.file

import java.io.File

interface TaskOutputs {

    fun addOutputFile(file: File,inputFile: InputFile)

    fun addOutputDirectory(file: File,inputFileList:List<InputFile>,match:(File,InputFile)->Boolean)

    fun addOutputFile(file: File,inputFileList:List<InputFile>)


    fun getOutputFile(file: File)

    fun getOutputFileFormDirectory(file: File):List<OutputFile>


}