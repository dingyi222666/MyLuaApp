package com.dingyi.myluaapp.build.api.file

import java.io.File

interface InputFile {

    fun getSnapShotHash():String

    fun toFile():File

    fun getFileHash():String

    fun getBindOutputFile(): List<File>?


    fun getSourceDirectory():String

}