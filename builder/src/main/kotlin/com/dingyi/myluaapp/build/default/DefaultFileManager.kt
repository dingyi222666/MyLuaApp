package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.file.SnapshotManager
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project
import java.io.File

class DefaultFileManager(
    private val project: Project
) : FileManager {


    private lateinit var defaultSnapshotManager :SnapshotManager


    override fun getSnapshotManager(): SnapshotManager {
        return defaultSnapshotManager
    }

    override fun resolveFile(name: String, module: Module): File {
        val localDir = "${project.getPath()}/${module.name}"
        return File(localDir, name)
    }

    override fun forEachDirectory(directory: File): List<File> {
        return directory.walkBottomUp().toMutableList().apply {
            remove(directory)
        }
    }

    override fun init() {
        defaultSnapshotManager = DefaultSnapshotManager(
            project.getCache()
        )
    }
}