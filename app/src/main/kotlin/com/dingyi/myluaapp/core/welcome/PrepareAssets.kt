package com.dingyi.myluaapp.core.welcome

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.zip.ZipHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/8/4 13:02
 * @description:
 **/
class PrepareAssets(private val activity: AppCompatActivity) {

    private suspend fun getAllUnList() = withContext(Dispatchers.IO) {
        ZipHelper
            .getZipAllList(activity.packageResourcePath)
            .map { it.name }
            .filter { path -> path.startsWith("assets/") || path.startsWith("lua/") }

    }


    fun start(block: () -> Unit) {
        activity.lifecycleScope.launch {
            val result = getAllUnList()

            val defaultPath = "${activity.filesDir.parentFile?.absolutePath}"


            ZipHelper.unZipFile(
                zipPath = activity.packageResourcePath,
                inZipPathList = result.filter { it.startsWith("lua/") },
                toPath = "$defaultPath/app_lua",
                unPathFilterPrefix = "/lua"
            )

            ZipHelper.unZipFile(
                zipPath = activity.packageResourcePath,
                inZipPathList = result.filter { it.startsWith("assets/") },
                toPath = "$defaultPath/files",
                unPathFilterPrefix = "/assets"
            )


            arrayOf(Paths.projectDir,Paths.fontsDir).forEach {
                it.toFile().mkdirs()
            }

            block()


        }
    }
}