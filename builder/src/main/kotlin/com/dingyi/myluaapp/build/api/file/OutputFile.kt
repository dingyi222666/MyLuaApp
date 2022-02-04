package com.dingyi.myluaapp.build.api.file

import java.io.File

interface OutputFile {

    fun getFile():File

    /**
     * get snapshot cache file,after snapshot
     */
    fun getSnapshotCacheFile():File

    fun snapshot()

    fun getCacheFile():File

    fun isIncremental():Boolean

    fun endSnapshot()
}