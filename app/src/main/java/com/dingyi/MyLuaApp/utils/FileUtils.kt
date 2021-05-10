@file:JvmName("FileUtils")

package com.dingyi.MyLuaApp.utils

import android.app.Activity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


val usePaths = mapOf(
    "buildPath" to "/sdcard/MyLuaApp/build",
    "projectPath" to "/sdcard/MyLuaApp/project"
)


fun String.toFile(): File {
    return File(this)
}

fun Activity.readAssetString(path: String): String {
    val stream = this.assets.open(path)
    stream.use {
        return it.readBytes().decodeToString()
    }
}

fun init() {
    usePaths.forEach {
        val file = it.value.toFile()
        if (!file.isDirectory)
            file.mkdirs()
    }
}

fun forEachDir(string: String, callback: (File) -> Unit) {
    string.toFile().listFiles()?.forEach {
        it?.let { callback(it) }
    }
}

fun readString(file: File): String {
    return file.readBytes().decodeToString()
}

fun readString(path: String): String {
    return path.toFile().readBytes().decodeToString()
}

fun writeString(path: String, content: String) {
    path.toFile().writeText(content)
}

fun copyFile(oldFile: File, toFile: File) {
    FileInputStream(oldFile).use { inputStream ->
        FileOutputStream(toFile).use { outputStream ->
            val bytes = ByteArray(1024)
            var len = inputStream.read(bytes)
            while (len != -1) {
                outputStream.write(bytes, 0, len)
                len = inputStream.read(bytes)
            }
        }
    }

}

fun renameTo(oldPath: String, toPath: String) {
    val oldFile = oldPath.toFile()
    if (oldFile.isFile) {
        copyFile(oldFile, toPath.toFile())
        oldFile.delete()
    } else if (oldFile.isDirectory) {

        oldFile.listFiles().forEach {
            if (it.isFile) {
                copyFile(it, "toPath/${it.name}".toFile())
                it.delete()
            } else if (it.isDirectory)
                renameTo(it.path, "toPath/${it.name}")
        }
    }
}


fun File.getSuffix(): String {
    return name.substring(name.lastIndexOf(".") + 1)
}

fun String.getSuffix(): String {
    return substring(lastIndexOf(".") + 1)
}

fun File.hasChildFile(): Boolean {
    if (this.isFile) {
        return false
    }
    return this.list().isNotEmpty()
}

fun File.listSortFiles(): List<File> {
    return this.listFiles().sortedWith { a, b ->
        if (a.isDirectory) {
            return@sortedWith -1
        } else if (b.isDirectory) {
            return@sortedWith 1
        } else {
            return@sortedWith a.name.compareTo(b.name)
        }
    }
}