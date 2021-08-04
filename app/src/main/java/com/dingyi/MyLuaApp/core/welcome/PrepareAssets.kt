package com.dingyi.MyLuaApp.core.welcome

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.MyLuaApp.common.kts.toFile
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.zip.ZipFile

/**
 * @author: dingyi
 * @date: 2021/8/4 13:02
 * @description:
 **/
class PrepareAssets(private val activity: AppCompatActivity) {

    private suspend fun getAllUnList() = withContext(Dispatchers.IO) {
        mutableListOf<String>().apply {
            runCatching {
                ZipFile(activity.packageResourcePath)
            }.onSuccess {
                it.use { zipFile ->
                    val entries = zipFile.entries()
                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()
                        val path = entry.name
                        if (path.startsWith("assets/") || path.startsWith("lua/")) {
                            add(path)
                        }
                    }
                }
            }.onFailure {

            }
        }


    }

    private fun unFile(zipPath: String, toPath: String): Boolean {
        if (toPath.toFile().isDirectory && toPath.toFile().exists()) {
            toPath.toFile().mkdirs()
            return true//toPath is dir and mkdirs is ok
        } else if (toPath.toFile().parentFile?.exists() == false) {
            toPath.toFile().parentFile?.mkdirs()
        }

        ZipFile(activity.packageResourcePath).use { zipFile ->
            zipFile.getInputStream(zipFile.getEntry(zipPath)).use { inputStream ->
                toPath.toFile().apply {
                    createNewFile()
                }.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }

        return true

    }

    fun start(block: () -> Unit) {
        activity.lifecycleScope.launchWhenResumed {
            val result = getAllUnList()

            val defaultPath = "/data/data/${activity.packageName}"

            val coroutineDispatcher = Executors.newFixedThreadPool(6).asCoroutineDispatcher()


            launch(Dispatchers.Default) {
                result.forEach {
                    async(coroutineDispatcher) {
                        unFile(
                            it,
                            if (it.startsWith("assets/"))
                                "$defaultPath/files/${it.substring(7)}"
                            else
                                "$defaultPath/app_lua/${it.substring(4)}"
                        )
                    }
                }
            }.join()

            coroutineDispatcher.close()
            block.invoke()
        }
    }
}