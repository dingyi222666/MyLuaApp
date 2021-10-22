package com.dingyi.myluaapp.core.startup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.common.kts.edit
import com.dingyi.myluaapp.common.kts.versionCode
import com.dingyi.myluaapp.common.zip.ZipHelper

/**
 * @author: dingyi
 * @date: 2021/10/21 10:59
 * @description:
 **/
object ZipContainer {

    fun checkVersion(context: AppCompatActivity): Boolean {
        val versionCode = context.versionCode

        val lastVersionCode = context.getSharedPreferences("default", Context.MODE_PRIVATE)
            .getInt("versionCode", 0)


        return versionCode != lastVersionCode

    }

    suspend fun unFileToAssets(context: AppCompatActivity, callback: suspend (String?) -> Unit) {
        val unFileList = ZipHelper.getZipFileList(context.applicationContext.packageResourcePath)
            .map { it.name }
            .filter { path -> path.startsWith("assets/") || path.startsWith("lua/") }


        val defaultPath = "${context.filesDir.parentFile?.absolutePath}"


        ZipHelper.UnZipBuilder()
            .apply {
                zipPath = context.applicationContext.packageResourcePath
                inZipPathList = unFileList
            }
            .toPath {
                if (it.startsWith("assets/")) {
                    "$defaultPath/files"
                } else {
                    "$defaultPath/app_lua"
                }
            }.unPathFilterPrefix {
                if (it.startsWith("assets/")) {
                    "assets"
                } else {
                    "lua"
                }
            }
            .build {
                if (it == null) {
                    context.getSharedPreferences("default", Context.MODE_PRIVATE)
                        .edit {
                            //putInt("versionCode", context.versionCode)
                        }
                }
                callback(it)
            }


    }

}