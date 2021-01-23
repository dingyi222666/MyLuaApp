package com.dingyi.MyLuaApp.utils

import android.app.Activity
import android.os.Environment
import com.dingyi.MyLuaApp.activitys.BaseActivity
import java.io.File
import java.io.InputStream


val sdPath=Environment.getExternalStorageDirectory().path

val usePaths= mapOf("buildPath" to "$sdPath/MyLuaApp/build",
"projectPath" to "$sdPath/MyLuaApp/project",
        "crashPath" to "$sdPath/MyLuaApp/crash"
)



fun String.toFile() :File {
    return File(this)
}

fun Activity.getAssetString(path: String):String {
    val stream=this.assets.open(path)
    stream.use {
        return it.readBytes().decodeToString()
    }
}

fun init() {
    usePaths.forEach {
        val file=it.value.toFile()
        e(it.value)
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


fun readBytes(inputStream: InputStream):ByteArray{
  inputStream.use {
      return it.readBytes()
  }
}

fun  writeBytes(path: String,byteArray: ByteArray) {
    path.toFile().let {
        it.createNewFile()
        it.outputStream().use {
            it.write(byteArray)
        }
    }
}

fun getSuffix(path: String): String {
    val lastPoint=path.lastIndexOf('.')+1
    return (path.substring(lastPoint,path.length))
}

