package com.dingyi.myluaapp.common.kts

import com.dingyi.myluaapp.MainApplication
import java.io.File
import java.util.zip.ZipFile

/**
 * @author: dingyi
 * @date: 2021/8/4 14:21
 * @description:
 **/


fun String.toFile() = File(this)

fun String.toZipFile() = ZipFile(this)

inline val File.suffix: String
    get() = if (name.lastIndexOf(".") == -1) "" else name.substring(
        name.lastIndex.coerceAtMost(
            name.lastIndexOf(
                "."
            ) + 1
        )
    )

inline fun File.isDirectory(block: File.() -> Unit) {
    if (this.isDirectory) {
        block()
    }
}

fun List<File>.sortBySelf(): List<File> {
    return this.sortedWith { a, b ->
        when {
            a.isDirectory && b.isDirectory -> {
                a.name.compareTo(b.name)
            }
            a.isDirectory && b.isFile -> {
                -1
            }
            a.isFile && b.isDirectory -> {
                1
            }
            else -> a.name.compareTo(b.name)
        }

    }
}

object Paths {
    val mainDir = MainApplication.instance.getExternalFilesDir("")?.absolutePath
    val builderPath = "$mainDir/builder"
    val buildCachePath = "$builderPath/cache"
    val snapshotCachePath = "$buildCachePath/snapshot"
    val localMavenPath = "$buildCachePath/maven"
    val projectDir = "$mainDir/project"
    val fontsDir = "$mainDir/fonts"
    val assetsDir = MainApplication.instance.filesDir.absolutePath
    val resDir = "$assetsDir/res"
    val buildPath = "$resDir/build"
    val tempateDir  = "$resDir/template"
    val projectFileTemplateDir = "$tempateDir/file"
    val projectTemplateDir = "$tempateDir/project/"
}