package com.dingyi.myluaapp.common.loader

import com.dingyi.myluaapp.common.ktx.Paths
import dalvik.system.DexClassLoader
import java.io.File

class ApkClassLoader(
    private val apkPath: String,
    private val libraryPath:String
): DexClassLoader(apkPath, Paths.dexLoaderDir,libraryPath, getSystemClassLoader()) {
    constructor(apkPath: File,libraryPath: File):this(apkPath.path,libraryPath.path)
}