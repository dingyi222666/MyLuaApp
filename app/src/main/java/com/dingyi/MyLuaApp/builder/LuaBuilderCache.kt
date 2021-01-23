package com.dingyi.MyLuaApp.builder

import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.luaj.LuaJ
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue

class LuaBuilderCache(projectInfo: ProjectInfo) : IBuilderCache(projectInfo) {

    val cacheDir = projectInfo.path + "/.cache"
    val buildDir = projectInfo.path + "/.build"
    val buildAxmlOutPath = "$buildDir/AndroidManifest_out.xml"
    val buildAssetsDir = "$buildDir/assets"
    val cacheLuaDir = "$cacheDir/lua"
    val cacheLibDir = "$cacheDir/lib"
    val cacheResDir = "$cacheDir/res"
    val cacheArscPath = "$cacheDir/resources.arsc"
    val cacheAxmlPath = "$cacheDir/AndroidManifest.xml"

    fun getLuaConfigs(): Map<String, LuaValue> {
        val luaJ = LuaJ()
        val result= mutableMapOf<String,LuaValue>()
        val table = luaJ.loadFile(projectInfo.path + "/init.lua")
        table.keys().forEach {
            it?.let {
                result.put(it.tojstring(),table[it])
            }
        }

        return result;
    }
}