package com.dingyi.myluaapp.core.helper

import com.dingyi.myluaapp.MainApplication
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import net.lingala.zip4j.ZipFile

import net.lingala.zip4j.model.FileHeader
import net.lingala.zip4j.model.LocalFileHeader
import java.io.File
import java.io.InputStream
import java.lang.StringBuilder
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


object ImportHelper {


    fun importPlugin(importStream: InputStream,block:(File) -> Unit) {
        val tmpPluginPath =
            File(MainApplication.instance.getExternalFilesDir("")?.parentFile,"cache/import.mpk")


        tmpPluginPath.parentFile?.mkdirs()
        tmpPluginPath.createNewFile()
        importStream.use { input ->
            tmpPluginPath.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        block(tmpPluginPath)

    }


    fun getPluginInfo(
        importStream: InputStream,
        pluginInfoList: List<Pair<String, String>>
    ): Pair<String, Map<String, String>> {


        val inputStream = ZipInputStream(importStream)


        var next:ZipEntry? = inputStream.nextEntry

        while ( next == null || next.name != "plugin.json" ) {
            next = inputStream.nextEntry
        }

        if (next == null) {
            error("no found")
        }

        val json = inputStream
            .readBytes().decodeToString()

        inputStream.close()
        importStream.close()

        val putResult = Gson().fromJson<Map<String, String>>(
            json,
            object : TypeToken<Map<String, String>>() {}.type
        )

        val buffer = StringBuilder()

        pluginInfoList.forEach { (t, _) ->
            if (putResult[t] == null) {
                error("missing property $t for plugin mainfest file")
            }
        }

        pluginInfoList.forEach { (key, value) ->
            buffer
                .append("\n")
                .append(value)
                .append(": ")
                .append(putResult[key])
                .append("\n")
        }

        return buffer.toString() to putResult


    }


}