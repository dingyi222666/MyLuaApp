package com.dingyi.MyLuaApp.builder.task.lua

import android.util.Log
import com.dingyi.MyLuaApp.builder.task.LuaTask
import com.luajava.SimpleLuaState
import org.luaj.vm2.LuaTable
import java.io.File
class CompileLuaTask: LuaTask() {

    private var state:SimpleLuaState? = null;


    override fun doAction(vararg arg: Any) {
        initLua();
        super.doAction(*arg)
    }


    fun initLua() {
        activity?.let {
            state=SimpleLuaState(it)
            state?.runFunc("sendClassToLua", File::class.java,"File")
            state?.doFile("/data/data/${it.packageName}/assets/lua/func.lua");//lua
        }
    }

    fun compileLua(path: String, outPath: String):Boolean? {
       return state?.runFunc("build", path, outPath) as Boolean?
    }
}