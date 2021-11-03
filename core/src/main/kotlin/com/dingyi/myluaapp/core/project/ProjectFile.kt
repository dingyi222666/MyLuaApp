package com.dingyi.myluaapp.core.project

import androidx.annotation.Keep
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.OutputStream
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/20 14:40
 * @description:
 **/
class ProjectFile(
    private val path: String,
    private val project: Project
) {

    private val file = path.toFile()

    private val virtualFile =
        "${project.projectPath}/.MyLuaApp/cache/virtual_file_${path.toMD5()}".toFile().apply {
            if (!exists()) {
                parentFile?.mkdirs()
                createNewFile()
            }
        }

    private var change = true

    private var cache by Delegates.notNull<ProjectFileHistory>()


    /**
     * Commit change to cache file
     */
    fun commitChange(text: CharSequence) {
        change = true
        val history = getLocalHistory()

        if (history.caches.size + 1 > 10) {
            history.caches.removeAt(history.caches.lastIndex)
                .delete()
        }
        val timestamp = System.currentTimeMillis()
        val cache = ProjectFileHistory.ProjectFileCache(
            "${project.projectPath}/.MyLuaApp/cache/history_file_${path.toMD5()}_${
                timestamp.toString().toMD5()
            }",
            timestamp
        )
        cache.saveText(text)
        history.caches.add(0, cache)
        history.save(virtualFile.outputStream())
    }

    private fun getLocalHistory(): ProjectFileHistory {
        readLocalHistory()
        return cache
    }

    private fun readLocalHistory() {
        if (change) {
            cache = Gson().fromJson(virtualFile.reader(), getJavaClass<ProjectFileHistory>())
        }
    }

    fun saveChange() {
        change = false
        cache.caches[0].copyTo(path)
    }

    data class ProjectFileHistory(
        @SerializedName("caches")
        var caches: MutableList<ProjectFileCache>
    ) {
        @Keep
        data class ProjectFileCache(
            @SerializedName("path")
            var path: String, // 666
            @SerializedName("timestamp")
            var timestamp: Long // 9999
        ) {
            fun readText(): String {
                return path.toFile().readText()
            }

            fun saveText(string: CharSequence) {
                path.toFile().writeText(string.toString())
            }

            fun delete() {
                path.toFile().delete()
            }

            fun copyTo(targetPath: String) {
                path.toFile().copyTo(targetPath.toFile(), true)
            }
        }


        fun save(outputStream: OutputStream) {

        }

    }

    fun deleteFile() {

    }
}