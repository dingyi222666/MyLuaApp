package com.dingyi.myluaapp.build.api.file

import java.io.File

interface InputFile {


    fun getFile(): File

    fun snapshot()

    fun isIncremental():Boolean

    fun concatToOutputFile(outputFile: OutputFile)

}