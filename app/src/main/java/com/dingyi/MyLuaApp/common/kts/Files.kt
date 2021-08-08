package com.dingyi.MyLuaApp.common.kts

import android.os.Environment
import com.dingyi.MyLuaApp.MainApplication
import java.io.File
import java.util.zip.ZipFile

/**
 * @author: dingyi
 * @date: 2021/8/4 14:21
 * @description:
 **/


fun String.toFile() = File(this)

fun String.toZipFile() = ZipFile(this)

object Paths {
    val mainDir = Environment.getExternalStorageDirectory().absolutePath + "/MyLuaApp"
    val projectDir = "$mainDir/project"
    val assetsDir = MainApplication.instance.filesDir.absolutePath
    val projectTemplateDir = assetsDir + "/res/template/project/"
}