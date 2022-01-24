package com.dingyi.myluaapp.build.script

import com.dingyi.myluaapp.build.api.script.Script
import com.dingyi.myluaapp.common.kts.LuaJVM
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile

//The DefaultScript based lua
class DefaultScript(private val path: String) : Script {

    private val luaJVM = LuaJVM()

    // default is false
    private var runStatus = false

    private val defaultName = path.toFile().name

    override fun getName(): String {
        return defaultName
    }

    init {
        luaJVM.init()
        val nowModuleDirectory = path.toFile().parentFile?.path ?: path
        luaJVM.doString(
            """
            function getNowProjectDir() return "$nowModuleDirectory" end
        """.trimIndent()
        )
        luaJVM.doFile("${Paths.buildPath}/lua/buildscriptfunc.lua")

    }

    override fun get(key: String): Any? {
        return luaJVM.runFunc("getScriptValue", key)
    }

    override fun run() {
        if (!runStatus) {
            runStatus = true
            luaJVM.runFunc("runScript", path)
        }
    }


    override fun close() {
        luaJVM.close()
    }

    override fun put(key: String, value: Any?) {
        luaJVM.runFunc("putScriptValue", value)
    }

    override fun getPath(): String {
        return path
    }
}