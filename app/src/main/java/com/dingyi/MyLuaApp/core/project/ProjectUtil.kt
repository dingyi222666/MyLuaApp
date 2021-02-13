@file:JvmName("ProjectUtil")
package com.dingyi.MyLuaApp.core.project


import android.app.Activity
import android.content.Context
import com.androlua.LuaUtil
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.utils.*
import org.json.JSONArray
import java.io.File
import java.util.*


const val LUA_PROJECT = 0xff

const val GRADLE_PROJECT = 0x1f

val PROJECT_TEMPLATE_LIST = mutableListOf<String>()


fun getProjectType(file: File): Int {
    if ("${file.absolutePath}/init.lua".toFile().isFile) {
        return LUA_PROJECT
    } else if ("${file.absolutePath}/app/build.gradle".toFile().isFile) {
        return GRADLE_PROJECT
    }
    return 0
}

fun getProjectTypeText(context: Context, int: Int): String {
    return when (int) {
        LUA_PROJECT -> context.getString(R.string.projectTypeLua)
        else -> ""
    }
}

fun getProjectTemplate(context: Activity): Array<CharSequence> {
    val result = mutableListOf<String>()
    val jsonString = context.getAssetString("res/template/template.json")
    val jsonArray = JSONArray(jsonString)

    for (i in 0 until jsonArray.length()) {
        result.add(jsonArray.getJSONObject(i).getString("templateName"))
        PROJECT_TEMPLATE_LIST.add(jsonArray.getJSONObject(i).getString("templatePath"))
    }

    return result.toTypedArray();

}

fun formatProjectText(dir: String, file: String, name: String, packageName: String) {
    var str = readString(dir + File.separator + file)
    str = str.replace("{appName}", name).replace("{appPackageName}", packageName)
    writeString((dir + File.separator + file), str)
}

fun createProject(context: Activity, i: Int, toPath: String, name: String, packageName: String) {
    val path = "data/data/${context.packageName}/assets/res/template/" + PROJECT_TEMPLATE_LIST[i]
    val formatText = LuaUtil.readZip(path, "format.json").decodeToString()
    val templatePath = "$toPath/template"
    val jsonArray = JSONArray(formatText)

    templatePath.toFile().mkdirs()
    LuaUtil.unZip(path, toPath, "template")

    for (i in 0 until jsonArray.length()) {
        formatProjectText(templatePath, jsonArray.getString(i), name, packageName)
    }

    LuaUtil.copyDir(templatePath, toPath)
    LuaUtil.rmDir(templatePath.toFile())

}

fun smartGetProjectName(): String {
    val list = mutableListOf<File>()
    var len = "MyApplication".length
    usePaths["projectPath"]?.let {
        with(it) {
            list.addAll(toFile().listFiles())
        }
    }

    len = list.filter {
        if (it.name.length < len) {
            return@filter false
        }
        return@filter it.name.substring(0, len) == "MyApplication"
    }.size

    if (len == 0)
        return "MyApplication"

    return "MyApplication${len+1}"
}
