package com.dingyi.MyLuaApp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.androlua.LuaActivity
import com.androlua.LuaUtil
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.base.BaseViewManager
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.builder.LuaBuilder
import com.dingyi.MyLuaApp.builder.ProgressBarBuilderOut
import org.json.JSONArray
import java.io.File
import java.io.FileNotFoundException
import java.util.*



const val LUA_PROJECT = 0xff

const val GRADLE_PROJECT = 0x1f

val PROJECT_TEMPLATE_LIST = mutableListOf<String>()


fun getProjectType(file: File): Int {
    if ("${file.absolutePath}/init.lua".toFile().isFile) {
        return LUA_PROJECT
    } else if ("${file.absolutePath}/app/build.gradle".toFile().isFile) {
        return com.dingyi.MyLuaApp.utils.GRADLE_PROJECT
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

fun getFileTemplateSuffix(name: String):String {
    val map=mapOf(".aly" to "Lua(.*)Layout",".lua" to "Lua(.*)$")
    map.entries.forEach {
        if (name.matches(it.value.toRegex())) {
            return it.key
        }
    }
    return ""
}

fun getFileTemplate(context: Activity):Map<CharSequence,String> {
    val result= mutableMapOf<CharSequence,String>()

    val jsonString = context.getAssetString("res/template/fileTemplate.json")
    val jsonArray = JSONArray(jsonString)

    for (i in 0 until jsonArray.length()) {

        result[jsonArray.getJSONObject(i).getString("templateName")] = jsonArray.getJSONObject(i).getString("templateString")
    }

    return result
}

fun getProjectInfoPath(projectInfo: ProjectInfo):String {
    return when(projectInfo.type){
        LUA_PROJECT -> projectInfo.path + "/init.lua"
        else -> ""
    }
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

fun buildLuaProject(info: ProjectInfo,activity: BaseActivity<*>) {
    with(activity) {
        val out = ProgressBarBuilderOut()
                .init(this)

        out.title = getString(R.string.build_title)


        val builder = LuaBuilder()
                .initBuilderOut(out)
                .initActivity(this)
                .initProjectInfo(info)

        builder.start()
    }
}

fun build(info: ProjectInfo,activity: BaseActivity<*>) {
    when (info.type) {
        LUA_PROJECT -> buildLuaProject(info,activity)
    }
}

fun getDefaultPath(path: String): String {
    return when (getProjectType(path.toFile())) {
        LUA_PROJECT -> "$path/main.lua"
        GRADLE_PROJECT -> "$path/app/src/main/assets/main.lua"
        else -> "$path/main.lua"
    }
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
        e(it.name.substring(0, len), (it.name.substring(0, len) == "MyApplication").toString())
        return@filter it.name.substring(0, len) == "MyApplication"
    }.size

    if (len == 0)
        return "MyApplication"

    return "MyApplication${len + 1}"
}

fun runProject(context: Activity, info: ProjectInfo) {
    when (info.type) {
        LUA_PROJECT -> {
            val intent = Intent(context, LuaActivity::class.java)
            intent.putExtra("name", info.path + "/main.lua")
            var path = info.path + "/main.lua"

            if (path[0] != '/') {
                path = "/data/data/${context.packageName}/files" + "/" + path
            }

            val f = File(path)
            if (f.isDirectory && File("$path/main.lua").exists()) path += "/main.lua" else if ((f.isDirectory || !f.exists()) && !path.endsWith(".lua")) path += ".lua"
            if (!File(path).exists()) throw FileNotFoundException(path)


            intent.data = Uri.parse("file://$path")
            context.startActivity(intent)

        }
    }
}

fun hasProject(path: String): Boolean {
  return (usePaths["projectPath"]+File.separator+path).toFile().isDirectory
}
