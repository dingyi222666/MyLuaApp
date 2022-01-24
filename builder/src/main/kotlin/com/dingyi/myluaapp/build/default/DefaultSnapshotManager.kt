package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.file.SnapshotManager
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.toMD5
import java.io.File

class DefaultSnapshotManager(private val tmpPath: String) : SnapshotManager {


    override fun snapshot(snapshotFile: File): Boolean {
        val md5Path = snapshotFile.path.toMD5()

        return runCatching {
            File(tmpPath, "${md5Path}_snapshot_file").apply {
                createNewFile()
            }.writeText(snapshotFile.getSHA256())
        }.isSuccess

    }

    override fun snapshotFull(snapshotFile: File): Boolean {
        val md5Path = snapshotFile.path.toMD5()

        snapshot(snapshotFile)

        return runCatching {
            File(tmpPath, "${md5Path}_snapshot_file_full").apply {
                createNewFile()
            }.writeText(snapshotFile.readText())
        }.isSuccess
    }

    override fun equalsSnapshot(snapshotFile: File): Boolean {
        val md5Path = snapshotFile.path.toMD5()

        val targetSHA256 = snapshotFile.getSHA256()

        return File(tmpPath, "${md5Path}_snapshot_file").readText() == targetSHA256
    }

    override fun getSnapshotFullFile(snapshotFile: File): File {
        val md5Path = snapshotFile.path.toMD5()

        return File(tmpPath, "${md5Path}_snapshot_file_full")
    }
}