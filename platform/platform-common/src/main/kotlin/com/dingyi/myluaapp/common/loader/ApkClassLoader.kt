package com.dingyi.myluaapp.common.loader

import com.dingyi.myluaapp.common.ktx.Paths
import dalvik.system.DexClassLoader
import java.io.File

class ApkClassLoader(
    private val apkPath: String,
    private val libraryPath: String,
    private val parentClassLoader: ClassLoader = getSystemClassLoader()
) : DexClassLoader(apkPath, Paths.dexLoaderDir, libraryPath, parentClassLoader) {
    constructor(apkPath: File, libraryPath: File, classLoader: ClassLoader) : this(
        apkPath.path,
        libraryPath.path,
        classLoader
    )
}