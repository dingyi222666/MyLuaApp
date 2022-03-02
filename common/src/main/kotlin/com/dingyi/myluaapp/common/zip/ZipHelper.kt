package com.dingyi.myluaapp.common.zip

import com.dingyi.myluaapp.common.ktx.println
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.ktx.toZipFile
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.zip.ZipEntry
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/8/5 20:13
 * @description:
 **/
object ZipHelper {

    suspend fun getZipFileList(zipPath: String) = withContext(Dispatchers.IO) {
        println(zipPath)
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


    class UnZipBuilder {
        var zipPath by Delegates.notNull<String>()
        var inZipPathList by Delegates.notNull<List<String>>()
        var toPath by Delegates.notNull<(String) -> String>()
        var unPathFilterPrefix by Delegates.notNull<(String) -> String>()

        fun toPath(toPath: (String) -> String): UnZipBuilder {
            this.toPath = toPath
            return this
        }

        fun unPathFilterPrefix(unPathFilterPrefix: (String) -> String): UnZipBuilder {
            this.unPathFilterPrefix = unPathFilterPrefix
            return this

        }

        suspend fun build(block: suspend (String?) -> Unit) {
            unZipFile(
                zipPath, inZipPathList, toPath, unPathFilterPrefix, block
            )
        }

    }


    suspend fun unZipFile(
        zipPath: String,
        inZipPathList: List<String>,
        toPath: (String) -> String,
        unPathFilterPrefix: (String) -> String = { "/" },
        block: suspend (String?) -> Unit = {},
    ) = withContext(Dispatchers.Main) {
        val coroutineDispatcher =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
                .asCoroutineDispatcher()
        launch(Dispatchers.IO) {
            inZipPathList.forEach { inZipPath ->
                async(coroutineDispatcher) {
                    val filterPrefix = unPathFilterPrefix(inZipPath)
                    unSingleZipFile(
                        zipPath,
                        inZipPath,
                        "${toPath(inZipPath)}/${inZipPath.substring(filterPrefix.lastIndex)}"
                    )
                    block(inZipPath)
                }
            }
        }.join()
        coroutineDispatcher.close()
        block(null)
    }

    private fun unSingleZipFile(zipPath: String, inZipPath: String, toPath: String): Boolean {
        println(zipPath, inZipPath, toPath)
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