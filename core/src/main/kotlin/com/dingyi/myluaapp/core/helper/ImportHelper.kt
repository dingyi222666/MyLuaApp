package com.dingyi.myluaapp.core.helper

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.io.inputstream.ZipInputStream
import net.lingala.zip4j.model.FileHeader
import net.lingala.zip4j.model.LocalFileHeader
import java.io.InputStream
import java.lang.StringBuilder


object ImportHelper {


    fun importPlugin(importStream: InputStream) {

    }


    fun getPluginInfo(
        importStream: InputStream,
        pluginInfoList: List<Pair<String, String>>
    ): Pair<String, Map<String, String>> {


        val inputStream = ZipInputStream(importStream)


        var next: LocalFileHeader? = inputStream.getNextEntry(null, false)

        while (next?.fileName != "plugin.json" || next == null) {
            next = inputStream.getNextEntry(null, false)
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