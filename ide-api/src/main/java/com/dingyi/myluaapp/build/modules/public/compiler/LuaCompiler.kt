package com.dingyi.myluaapp.build.modules.public.compiler

import io.github.dingyi.androlua.vm.SingleLuaVM
import java.io.File

object LuaCompiler {

    private val globalLuaVM = SingleLuaVM()

    var isInit = false

    /**
     * Compile a lua script to bytecode.
     */
    fun compile(scriptPath: String, outputPath: String) = kotlin.runCatching {

        val tmpOutputPath = "$outputPath.tmp"

        if (!isInit) {
            globalLuaVM.init()
            //compile lua script to bytecode using lua src string
            globalLuaVM.doString("function compile(from,to) local f = io.open(from,\"r\") local t = io.open(to,\"w\") local s = f:read(\"*a\") f:close() t:write(string.dump(load(s,\"\"))) t:close() end")
            isInit = true
        }

        //run compile func im lua vm to compile lua
        globalLuaVM.runFunc("compile", scriptPath, tmpOutputPath)

        //rename tmp file to output file
        val tmpFile = File(tmpOutputPath)
        val outputFile = File(outputPath)
        tmpFile.copyTo(outputFile, true)
        tmpFile.delete()
    }.isSuccess
}