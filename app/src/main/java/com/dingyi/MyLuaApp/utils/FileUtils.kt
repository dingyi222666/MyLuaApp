@file:JvmName("FileUtils")
package com.dingyi.MyLuaApp.utils

import android.app.Activity

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

fun forEachDir(string: String,callback:(File)->Unit) {
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

fun writeString(s: String,string: String) {
    s.toFile().writeText(string)
}

