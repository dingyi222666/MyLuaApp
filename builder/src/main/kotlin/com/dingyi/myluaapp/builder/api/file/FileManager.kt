package com.dingyi.myluaapp.builder.api.file

import com.dingyi.myluaapp.builder.api.project.Module
import com.dingyi.myluaapp.builder.api.project.Project
import java.io.File

interface FileManager {


    fun getSnapshotManager():SnapshotManager

    fun resloveFile(name:String,module: Module):File

    fun forEachDirectory(directory:File):List<File>


}