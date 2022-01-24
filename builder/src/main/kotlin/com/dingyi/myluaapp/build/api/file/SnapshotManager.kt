package com.dingyi.myluaapp.build.api.file

import java.io.File

interface SnapshotManager {


    /**
     * Snapshot a file
     * @return is success snapshot
     */
    fun snapshot(snapshotFile: File): Boolean

    /**
     * Snapshot a file and save full file
     * @return is success snapshot
     */
    fun snapshotFull(snapshotFile: File): Boolean

    /**
     * Compare Snapshot file are equals now file
     * @return is equals
     */
    fun equalsSnapshot(snapshotFile: File): Boolean

    /**
     * Get Full Snapshot File
     */
    fun getSnapshotFullFile(snapshotFile: File): File

}