@file:JvmName("FileUtils")
package com.dingyi.MyLuaApp.utils

import android.app.Activity
import android.text.TextUtils.substring

import java.io.File


val usePaths= mapOf("buildPath" to "/sdcard/MyLuaApp/build",
        "projectPath" to "/sdcard/MyLuaApp/project"
)



fun String.toFile() :File {
    return File(this)
}

fun Activity.readAssetString(path: String):String {
    val stream=this.assets.open(path)
    stream.use {
        return it.readBytes().decodeToString()
    }
}

fun init() {
    usePaths.forEach {
        val file=it.value.toFile()
        if (!file.isDirectory)
            file.mkdirs()
    }
}

fun forEachDir(string: String, callback: (File) -> Unit) {
    string.toFile().listFiles()?.forEach {
        it?.let{callback(it)}
    }
}

fun readString(file: File) :String {
    return file.readBytes().decodeToString()
}

fun readString(s: String): String {
    return s.toFile().readBytes().decodeToString()
}

fun writeString(s: String, string: String) {
    s.toFile().writeText(string)
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

fun File.listSortFiles():List<File>{
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