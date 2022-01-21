package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.file.SnapshotManager
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import java.io.File

class DefaultFileManager(
    private val path:String
):FileManager {



    override fun getSnapshotManager(): SnapshotManager {
        TODO("Not yet implemented")
    }

    override fun resloveFile(name: String, module: Module): File {
        TODO()
    }

    override fun forEachDirectory(directory: File): List<File> {
        TODO("Not yet implemented")
    }
}