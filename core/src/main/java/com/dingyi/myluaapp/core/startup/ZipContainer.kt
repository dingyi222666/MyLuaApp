package com.dingyi.myluaapp.core.startup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.common.kts.versionCode
import com.dingyi.myluaapp.common.zip.ZipHelper
import java.util.zip.ZipFile

/**
 * @author: dingyi
 * @date: 2021/10/21 10:59
 * @description:
 **/
class ZipContainer {

    fun checkVersion(context: AppCompatActivity): Boolean {
        val versionCode = context.versionCode

        val lastVersionCode = context.getSharedPreferences("default", Context.MODE_PRIVATE)
            .getInt("versionCode", 0)


        return versionCode > lastVersionCode

    }

    suspend fun unFileToAssets(context: AppCompatActivity) {
        ZipHelper.getZipFileList(context.applicationContext.packageResourcePath)
    }

}