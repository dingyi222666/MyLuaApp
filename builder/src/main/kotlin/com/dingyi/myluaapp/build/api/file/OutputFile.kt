package com.dingyi.myluaapp.build.api.file

import java.io.File

interface OutputFile {

    fun getBindInputFiles():List<InputFile>

    fun getSnapShotHash():String

    fun getPath(): File

    fun getFileHash():String


}