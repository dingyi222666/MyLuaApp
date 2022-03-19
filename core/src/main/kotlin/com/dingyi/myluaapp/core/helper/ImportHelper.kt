package com.dingyi.myluaapp.core.helper

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.io.inputstream.ZipInputStream
import net.lingala.zip4j.model.FileHeader
import net.lingala.zip4j.model.LocalFileHeader
import java.io.InputStream



object ImportHelper {



    fun importPlugin(importStream:InputStream) {

    }


    fun getPluginMessage(importStream: InputStream):Map<String,String> {
        val result = mutableMapOf<String,String>()


        val inputStream = ZipInputStream(importStream)


        var next :LocalFileHeader?= inputStream.getNextEntry(null,false)

        while (next?.fileName != "plugin.json" || next == null) {
            next = inputStream.getNextEntry(null,false)
        }


        return result


    }


}