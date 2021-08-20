package com.dingyi.editor.language.java.api

import org.json.JSONObject
import java.io.InputStream

/**
 * @author: dingyi
 * @date: 2021/8/18 22:43
 * @description:
 **/
object AndroidApi {


    private val androidApiList = mutableListOf<String>()

    fun findClasses(name: String): List<String> {

        val result = mutableListOf<String>()
        androidApiList.forEach {
            if (it.lowercase().startsWith(name.lowercase())) {
                result.add(it)
            }
        }
        return result
    }


    fun findClassesByEnd(name: String): List<String> {
        val result = mutableListOf<String>()
        androidApiList.forEach {
            if (it.split(".")
                    .run { get(lastIndex) }
                    .lowercase()
                    .startsWith(name.lowercase())) {
                result.add(it)
            }
        }
        return result
    }


    private fun walk(jsonObject: JSONObject, parentString: String) {
        jsonObject.keys().forEach {
            jsonObject.optJSONObject(it)?.let { child ->
                if (child.keys().asSequence().toList().isEmpty()) {
                    androidApiList.add("$parentString.$it".substring(1))
                } else {
                    walk(child, "$parentString.$it")
                }
            }
        }
    }

    fun prepareJson(inputStream: InputStream) {
        if (androidApiList.isEmpty()) {
            JSONObject(inputStream.readBytes().decodeToString()).run {
                walk(this, "")
            }
        }

    }


}