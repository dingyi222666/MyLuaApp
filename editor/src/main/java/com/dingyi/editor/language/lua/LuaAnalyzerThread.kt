package com.dingyi.editor.language.lua

import com.dingyi.editor.AnalyzerThread
import com.dingyi.lua.analysis.LuaAnalyzer

/**
 * @author: dingyi
 * @date: 2021/10/1 15:23
 * @description:
 **/
class LuaAnalyzerThread : AnalyzerThread() {

    private val luaAnalyzer = LuaAnalyzer()
    override fun analyze(nowObject: Any): Any {
        return luaAnalyzer.analyserCode(
            nowObject.toString()
        )
    }
}