package com.dingyi.myluaapp.common.ktx

import com.dingyi.myluaapp.MainApplication
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
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

fun OutputStream.writeUseGZIP(text: String) {
    this.use { output ->
        val gzipOutputStream = GZIPOutputStream(output)
        gzipOutputStream.write(text.toByteArray())
        gzipOutputStream.close()
    }
}

fun InputStream.readFormGZIP(): String {
    return use { input ->
        val gzipInputStream = GZIPInputStream(input)
        val str = gzipInputStream.readBytes().decodeToString()
        gzipInputStream.close()
        str
    }
}


fun Array<File>.sortFile(): List<File> {
    return this.sortedWith { a, b ->
        when {
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
    val cacheDir = "${mainDir?.toFile()?.parentFile?.path}/cache"
    val builderDir = "$cacheDir/builder"
    val nativeLibraryDir = MainApplication.instance.applicationInfo.nativeLibraryDir
    val assetsDir = MainApplication.instance.filesDir.absolutePath
    val localMavenDir = "$builderDir/maven"
    val extractAarDir = "$builderDir/extract-aar"
    val projectDir = "$mainDir/project"
    val fontsDir = "$mainDir/fonts"
    val pluginDir = "$assetsDir/plugin"

    val resDir = "$assetsDir/res"
    val buildPath = "$resDir/build"
    val tempateDir = "$resDir/template"
    val projectFileTemplateDir = "$tempateDir/file"
    val projectTemplateDir = "$tempateDir/project/"
}