package com.dingyi.myluaapp.common.zip

import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toZipFile
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.zip.ZipEntry

/**
 * @author: dingyi
 * @date: 2021/8/5 20:13
 * @description:
 **/
object ZipHelper {

    suspend fun getZipFileList(zipPath: String) = withContext(Dispatchers.IO) {
        mutableListOf<ZipEntry>().apply {
            runCatching {
                zipPath.toZipFile()
            }.onSuccess { zipFile ->
                val entries = zipFile.entries()
                while (entries.hasMoreElements()) {
                    add(entries.nextElement())
                }
            }
        }

    }


    suspend fun unZipFile(
        zipPath: String,
        inZipPathList: List<String>,
        toPath: String,
        unPathFilterPrefix: String = "/",
        block: (String?) -> Any = {},
    ) = withContext(Dispatchers.Main) {
            val coroutineDispatcher = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()).asCoroutineDispatcher()
            launch(Dispatchers.IO) {
                inZipPathList.forEach {
                    async(coroutineDispatcher) {
                        unSingleZipFile(
                            zipPath,
                            it,
                            "$toPath/${it.substring(unPathFilterPrefix.lastIndex)}"
                        )
                        block(it)
                    }
                }
            }.join()
            coroutineDispatcher.close()
            block(null)
        }

    private fun unSingleZipFile(zipPath: String, inZipPath: String, toPath: String): Boolean {
        zipPath.toZipFile().use {
            val entry = it.getEntry(inZipPath)
            if (entry.isDirectory) {
                toPath.toFile().mkdirs()
                return true//toPath is dir and mkdirs is ok
            } else if (toPath.toFile().parentFile?.exists() == false) {
                toPath.toFile().parentFile?.mkdirs()
            }

            it.getInputStream(entry).use { inputStream ->
                toPath.toFile().apply {
                    createNewFile()
                }.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            return true
        }
    }


}