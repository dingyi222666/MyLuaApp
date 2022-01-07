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
    val path: String,
    private val project: Project
) {

    private val file = path.toFile()

    private val virtualProjectFilePath =
        "${project.projectPath}/.MyLuaApp/cache/virtual_file_${path.toMD5()}".toFile().apply {
            if (!exists()) {
                parentFile?.mkdirs()
                createNewFile()
                writeText("{\"caches\":[]}")
            }
        }

    private var change = true

    private var virtualProjectFile by Delegates.notNull<VirtualProjectFile>()



    companion object {
        fun checkVirtualProjectPathExists(projectPath:String,path: String):Boolean {
            return "$projectPath/.MyLuaApp/cache/virtual_file_${path.toMD5()}".toFile().exists()
        }
    }

    /**
     * Commit change to cache file
     */
    fun commitChange(
        text: CharSequence,
        data: Map<String, Float>,
        timestamp: Long = System.currentTimeMillis()
    ) {

        val history = getVirtualProjectFile()

        if (history.historyList.size + 1 > 10) {
            history.historyList.removeAt(history.historyList.lastIndex)
                .delete()
        }
        history.apply {

            val defaultValue = { 0f }
            scrollX = data.getOrElse("scrollX", defaultValue).toInt()
            scrollY = data.getOrElse("scrollY", defaultValue).toInt()
            column = data.getOrElse("column", defaultValue).toInt()
            line = data.getOrElse("line", defaultValue).toInt()
            textSize = data.getOrElse("textSize",defaultValue)
        }

        val cache = VirtualProjectFile.ProjectFileCache(
            "${project.projectPath}/.MyLuaApp/cache/history_file_${path.toMD5()}_${
                timestamp.toString().toMD5()
            }",
            timestamp
        )
        cache.saveText(text)
        history.historyList.add(0, cache)
        history.save(virtualProjectFilePath.outputStream())
        change = true
    }

    @JvmName("getVirtualProjectFile1")
    private fun getVirtualProjectFile(): VirtualProjectFile {
        readLocalVirtualFile()
        return virtualProjectFile
    }


    private fun readLocalVirtualFile() {
        if (change) {
            virtualProjectFile =
                Gson().fromJson(virtualProjectFilePath.reader(), getJavaClass<VirtualProjectFile>())
        }
    }

    fun saveChange(): Boolean {
        return runCatching {
            virtualProjectFile.historyList[0].copyTo(path)
            change = false
        }.isSuccess
    }


    fun readText(): String {
        return getVirtualProjectFile().historyList.getOrNull(0)?.run {
            readText()
        } ?: path.toFile().readText()
    }

    fun readEditorData(): Map<String, Float> {
        return mapOf(
            "line" to getVirtualProjectFile().line.toFloat(),
            "scrollX" to getVirtualProjectFile().scrollX.toFloat(),
            "scrollY" to getVirtualProjectFile().scrollY.toFloat(),
            "column" to getVirtualProjectFile().column.toFloat(),
            "textSize" to getVirtualProjectFile().textSize
        )
    }


    override fun equals(other: Any?): Boolean {
        if (other is ProjectFile) {
            return other.path == this.path
        }
        return false
    }


    data class VirtualProjectFile(
        @SerializedName("caches")
        var historyList: MutableList<ProjectFileCache>,
        var scrollX: Int = 0,
        var scrollY: Int = 0,
        var line: Int = 0,
        var column: Int = 0,
        //if size==0
        var textSize: Float = 0f

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

            fun delete():Boolean {
                return path.toFile().delete()
            }

            fun copyTo(targetPath: String) {
                path.toFile().copyTo(targetPath.toFile(), true)
            }


        }


        fun save(outputStream: OutputStream) {
            //TODO use binary history key-map file and gzip support
            outputStream.use {
                it.write(Gson().toJson(this).encodeToByteArray())
            }
        }

        fun delete():Boolean {
            return !historyList.map {
                it.delete()
            }.contains(false)
        }

    }

    fun deleteFile():Boolean {
        return getVirtualProjectFile().delete() && virtualProjectFilePath.delete() && path.toFile()
            .delete()
    }

    override fun toString(): String {
        return "ProjectFile(path='$path', project=$project, file=$file, virtualFile=$virtualProjectFilePath, change=$change)"
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }

    fun rename(toPath: String): Boolean  = runCatching {
        saveChange()

        toPath.toFile().apply {
            parentFile?.mkdirs()
            createNewFile()
        }.writeText(readText())

        val newProjectFile = ProjectFile(toPath, project)
        val nowVirtualFile = getVirtualProjectFile()
        newProjectFile.getVirtualProjectFile().apply {
            scrollY = nowVirtualFile.scrollY
            scrollX = nowVirtualFile.scrollX
            line = nowVirtualFile.line
            column = nowVirtualFile.column
            textSize = nowVirtualFile.textSize
            nowVirtualFile.historyList.reverse()
            nowVirtualFile.historyList.forEach {
                val cache = VirtualProjectFile.ProjectFileCache(
                    "${project.projectPath}/.MyLuaApp/cache/history_file_${toPath.toMD5()}_${
                        it.timestamp.toString().toMD5()
                    }",
                    it.timestamp
                )
                cache.saveText(it.readText())
                this.historyList.add(0,cache)
            }
            newProjectFile.virtualProjectFilePath.outputStream().use {
                save(it)
            }
        }
        deleteFile()
    }.isSuccess
}