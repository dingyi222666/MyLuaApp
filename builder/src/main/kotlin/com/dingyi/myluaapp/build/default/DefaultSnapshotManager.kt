package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Cache
import com.dingyi.myluaapp.build.api.file.SnapshotManager
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.toMD5
import java.io.File

class DefaultSnapshotManager(
    private val cache: Cache
) : SnapshotManager {


    override fun snapshot(snapshotFile: File): Boolean {
        if (!snapshotFile.exists()) {
            return false
        }
        val md5Path = snapshotFile.path.toMD5()

        return runCatching {
            cache.saveCacheToDisk("${md5Path}_snapshot_file", snapshotFile.getSHA256())
        }.isSuccess

    }



    override fun equalsSnapshot(snapshotFile: File): Boolean {

        if (!snapshotFile.exists()) {
            return false
        }

        val md5Path = snapshotFile.path.toMD5()

        val targetSHA256 = snapshotFile.getSHA256()

        return cache.readCacheFromDisk("${md5Path}_snapshot_file") == targetSHA256
    }

    override fun equalsAndSnapshot(snapshotFile: File): Boolean {
        if (!snapshotFile.exists()) {
            return false
        }
        return equalsSnapshot(snapshotFile).apply {
            snapshot(snapshotFile)
        }
    }


}