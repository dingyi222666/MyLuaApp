package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.file.SnapshotManager
import java.io.File

class DefaultSnapshotManager(val tmpPath:String):SnapshotManager {
    override fun snapshot(snapshotFile: File): Boolean {
        TODO("Not yet implemented")
    }

    override fun snapshotFull(snapshotFile: File): Boolean {
        TODO("Not yet implemented")
    }

    override fun equalsSnapshot(snapshotFile: File): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSnapshotFullFile(snapshotFile: File): File {
        TODO("Not yet implemented")
    }
}