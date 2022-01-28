package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.file.SnapshotManager
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.common.kts.Paths
import java.io.File

class DefaultFileManager(
    private val path: String,
) : FileManager {


    private val defaultSnapshotManager = DefaultSnapshotManager("$path/build/snapshot")

    override fun getSnapshotManager(): SnapshotManager {
        return defaultSnapshotManager
    }

    override fun resolveFile(name: String, module: Module): File {
        val localDir = "$path/${module.name}"
        return File(localDir, name)
    }

    override fun forEachDirectory(directory: File): List<File> {
        return directory.walkBottomUp().toMutableList().apply {
            remove(directory)
        }
    }
}