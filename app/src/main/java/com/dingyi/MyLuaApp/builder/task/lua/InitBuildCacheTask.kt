package com.dingyi.MyLuaApp.builder.task.lua

import com.androlua.LuaApplication
import com.androlua.LuaUtil
import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.builder.LuaBuilderCache
import com.dingyi.MyLuaApp.builder.task.LuaTask
import com.dingyi.MyLuaApp.utils.toFile

class InitBuildCacheTask: LuaTask() {

    override fun doAction(vararg arg: Any) {
        val luaBuilderCache=arg[0] as LuaBuilderCache;
        val activity=arg[1] as BaseActivity;

        luaBuilderCache.cacheDir.toFile().mkdirs();
        luaBuilderCache.buildDir.toFile().mkdirs();

        if (!luaBuilderCache.cacheLibDir.toFile().isDirectory) { //加载lua lib
            val soPath="/data/data/${activity.packageName}/lib/"
            val toPath=luaBuilderCache.cacheLibDir+"/armebai-v7a/"
            toPath.toFile().mkdirs()
            LuaUtil.copyDir(soPath,toPath)
        }

        if (!luaBuilderCache.cacheLuaDir.toFile().isDirectory) { //加载lua
            val soPath="/data/data/${activity.packageName}/app_lua/"
            val toPath=luaBuilderCache.cacheLuaDir
            toPath.toFile().mkdirs()
            LuaUtil.copyDir(soPath,toPath)
        }

        //加载res
        if (!luaBuilderCache.cacheResDir.toFile().isDirectory || !luaBuilderCache.cacheArscPath.toFile().isFile || !luaBuilderCache.cacheAxmlPath.toFile().isFile) { //��ѹlua
            val defaultZipPath="/data/data/${activity.packageName}/assets/res/build/defaultRes.zip"
            LuaUtil.unZip(defaultZipPath,luaBuilderCache.cacheDir)
        }



    }
}