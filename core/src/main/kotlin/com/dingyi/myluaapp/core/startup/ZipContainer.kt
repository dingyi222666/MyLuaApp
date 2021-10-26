package com.dingyi.myluaapp.core.startup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.kts.edit
import com.dingyi.myluaapp.common.kts.versionCode
import com.dingyi.myluaapp.common.zip.ZipHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

/**
 * @author: dingyi
 * @date: 2021/10/21 10:59
 * @description:
 **/
object ZipContainer {

    fun checkVersion(): Boolean {
        val context = MainApplication.instance
        val versionCode = context.versionCode

        val lastVersionCode = context.getSharedPreferences("default", Context.MODE_PRIVATE)
            .getInt("versionCode", 0)


        return false //versionCode != lastVersionCode

    }

    @ExperimentalCoroutinesApi
    fun unFileToAssets() = channelFlow {
        val context = MainApplication.instance
        val unFileList = ZipHelper.getZipFileList(context.packageResourcePath)
            .map { it.name }
            .filter { path -> path.startsWith("assets/") || path.startsWith("lua/") }

        println(unFileList)
        val defaultPath = "${context.filesDir.parentFile?.absolutePath}"


        ZipHelper.UnZipBuilder()
            .apply {
                zipPath = context.packageResourcePath
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
                send(it)
            }


    }

}