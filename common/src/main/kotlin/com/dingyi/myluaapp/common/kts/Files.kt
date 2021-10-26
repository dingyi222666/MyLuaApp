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
    get() = name.substring(name.lastIndexOf(".") + 1)

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
    val mainDir = MainApplication.instance.getExternalFilesDir("test")?.parentFile?.absolutePath
    val projectDir = "$mainDir/project"
    val fontsDir = "$mainDir/fonts"
    val assetsDir = MainApplication.instance.filesDir.absolutePath
    val projectTemplateDir = "$assetsDir/res/template/project/"
}