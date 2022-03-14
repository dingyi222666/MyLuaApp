package com.dingyi.myluaapp.build.api.file

import java.io.File

interface SnapshotManager {


    /**
     * Snapshot a file
     * @return is success snapshot
     */
    fun snapshot(snapshotFile: File): Boolean

    /**
     * Compare Snapshot file are equals now file
     * @return is equals
     */
    fun equalsSnapshot(snapshotFile: File): Boolean


    /**
     * Compare Snapshot file are equals now file and snapshot
     * @return is equals
     */
    fun equalsAndSnapshot(snapshotFile: File): Boolean


}