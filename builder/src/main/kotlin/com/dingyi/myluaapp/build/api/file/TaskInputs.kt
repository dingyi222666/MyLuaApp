package com.dingyi.myluaapp.build.api.file

import java.io.File

interface TaskInputs {

    fun addFile(file:File)

    fun addFiles(vararg file: File)

    fun addDirectory(file: File)

    fun addDirectory(file: File,filter:(File)->Boolean)

    fun addDirectorys(file: File)

    fun addDirectorys(file: File,filter: (File) -> Boolean)

    fun getInputs():List<InputFile>

    fun isIncremental():Boolean


}