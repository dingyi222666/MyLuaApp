package com.dingyi.myluaapp.build.api.file

import com.dingyi.myluaapp.build.api.Module
import java.io.File

interface FileManager:SnapshotManager {


    fun getSnapshotManager(): SnapshotManager

    fun resolveFile(name: String, module: Module): File

    fun forEachDirectory(directory: File): List<File>

    fun init()

}