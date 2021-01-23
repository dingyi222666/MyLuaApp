package com.dingyi.MyLuaApp.builder.task.lua

import android.util.Log
import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.builder.BuilderException
import com.dingyi.MyLuaApp.builder.LuaBuilderCache
import com.dingyi.MyLuaApp.builder.task.LuaTask
import com.dingyi.MyLuaApp.utils.e
import com.dingyi.MyLuaApp.utils.toFile
import com.luajava.SimpleLuaState
import org.luaj.vm2.LuaTable
import java.io.File
class CompileLuaTask: LuaTask() {

    private var state:SimpleLuaState? = null;

    private var cache:LuaBuilderCache?=null

    override fun doAction(vararg arg: Any) {
        initLua();
        cache=arg[0] as LuaBuilderCache;

        cache?.let { cache ->
            cache.buildAssetsDir.toFile().mkdirs()
            val path = cache.projectInfo.path
            if (path != null) {
                doCompile(path)
            }
        }

    }


    private fun getOutPath(string: String): String {
        if (cache!=null){

            return cache!!.buildAssetsDir+"/"+(string.substring((cache!!.projectInfo.path?.length)!! +1))
        }
        return ""
    }

    fun doCompile(path: String) {
        path.toFile().listFiles().forEach {
            if (it.name.substring(0,1)==".")
                return

            if (it.isDirectory) {
                doCompile(it.path)
            }else if (it.name.endsWith("lua") || it.name.endsWith("aly")) {
                compileLua(it.path,getOutPath(it.path))
            }
        }
    }

    fun initLua() {
        activity?.let {
            state=SimpleLuaState(it)
            state?.runFunc("sendClassToLua", File::class.java,"File")
            state?.doFile("/data/data/${it.packageName}/assets/lua/func.lua");//lua
        }
    }

    fun compileLua(path: String, outPath: String):Boolean?  {
        val any=state?.runFunc("build", path, outPath)
        if (any is String) {
            sendError("Lua Build Error$any")
            throw BuilderException(Throwable(any));
        }
        return true
    }

    fun close() {
        state?.close()
    }
}