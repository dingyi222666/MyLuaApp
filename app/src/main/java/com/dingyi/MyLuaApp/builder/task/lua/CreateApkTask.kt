package com.dingyi.MyLuaApp.builder.task.lua

import apksigner.Signer
import com.android.sdklib.build.ApkBuilder
import com.androlua.LuaUtil
import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.builder.BuilderException
import com.dingyi.MyLuaApp.builder.LuaBuilderCache
import com.dingyi.MyLuaApp.builder.task.LuaTask
import com.dingyi.MyLuaApp.utils.e
import com.dingyi.MyLuaApp.utils.toFile
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CreateApkTask:LuaTask() {

    override fun doAction(vararg arg: Any) {
        val luaBuilderCache=arg[0] as LuaBuilderCache;

        createTmpApk(luaBuilderCache) //创建缓存apk

        val errorSteam=PrintStream(ByteArrayOutputStream())

        val builder=ApkBuilder(luaBuilderCache.buildUnSignedPath.toFile(),luaBuilderCache.buildUnSignedWithResApkPath.toFile(),null,null,errorSteam)





        if (luaBuilderCache.cacheResDir.toFile().exists())  {
            addDir(luaBuilderCache.cacheResDir,builder,luaBuilderCache.cacheDir) //add res
            addDir(luaBuilderCache.cacheLibDir,builder,luaBuilderCache.cacheDir) //add lib
            addDir(luaBuilderCache.cacheLuaDir,builder,luaBuilderCache.cacheDir) //add lua
            addDir(luaBuilderCache.buildAssetsDir,builder,luaBuilderCache.buildDir)// add assets lua
            addDirWithNoLua(luaBuilderCache.projectInfo.path!!,builder,luaBuilderCache.projectInfo.path!!)// add assets lua
        }


        val array=luaBuilderCache.cacheDexPath.toFile().listFiles()
        for (i in array.indices) {
            builder.addFile(array[i],getDexName(i))
        }

        builder.addFile(luaBuilderCache.buildAxmlOutPath.toFile(),"AndroidManifest.xml")

        kotlin.runCatching {
            builder.sealApk()
        }.onFailure {
            sendError(it.toString())
            throw BuilderException(it)
        }.onSuccess {
            signApk(luaBuilderCache,activity!!)
        }

    }

    private fun signApk(luaBuilderCache: LuaBuilderCache,activity: BaseActivity) {
        sendMessage("sign apk")
        Signer.sign(luaBuilderCache.buildUnSignedPath,luaBuilderCache.buildSignedPath)
        sendMessage("install apk")
        activity.installApk(luaBuilderCache.buildSignedPath)
        luaBuilderCache.buildUnSignedPath.toFile().delete()
    }

    private fun getDexName(int: Int):String {
        return if (int==0) {
            "classes.dex"
        } else {
            "classes{$int}.dex"
        }
    }

    private fun addDirWithNoLua(addPath:String,apkBuilder: ApkBuilder,parentPath:String) {
        addPath.toFile().listFiles().forEach {
            it?.let { file ->
                if (file.name.substring(0,1)==".")
                    return@let
                if (file.isDirectory) {
                    addDirWithNoLua(file.path,apkBuilder, parentPath)
                } else {
                    if (!(file.name.endsWith("lua") || file.name.endsWith("aly"))) {
                        sendMessage("add file ${file.path.substring(parentPath.length+1)}")
                        apkBuilder.addFile(file,"assets/"+file.path.substring(parentPath.length+1))
                    }

                }
            }
        }
    }

    private fun addDir(addPath:String,apkBuilder: ApkBuilder,parentPath:String) {
        addPath.toFile().listFiles().forEach {
            it?.let { file ->
                if (file.isDirectory) {
                    addDir(file.path,apkBuilder, parentPath)
                } else {
                    sendMessage("add file ${file.path.substring(parentPath.length+1)}")
                    apkBuilder.addFile(file,file.path.substring(parentPath.length+1))
                }
            }
        }
    }



    fun createTmpApk(luaBuilderCache: LuaBuilderCache) {
        sendMessage("create tmp apk")
        LuaUtil.zip(luaBuilderCache.cacheArscPath,luaBuilderCache.buildDir,luaBuilderCache.buildUnSignedWithResApkPath.toFile().name)
    }
}