package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.file.SnapshotManager
import java.io.File

interface BuildTool {


    fun getLocalMavenDirectory(): File

    fun getCacheDirectory(): File

    fun getCurrentVersion():String

    fun getSnapshotManager(): SnapshotManager

}