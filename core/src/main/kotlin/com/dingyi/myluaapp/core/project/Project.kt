package com.dingyi.myluaapp.core.project

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:10
 * @description: project bean class
 **/
class Project(
    val projectPath: String = "",
    private val projectManager: ProjectManager = ProjectManager(
        projectPath.toFile().parentFile?.absolutePath ?: ""
    )
) : IProject, Parcelable {

    private val gson = Gson()

    data class AppProject(
        val appPackageName: String,
        val appName: String,
        var iconPath: String,
        var path: String = ""
    )


    constructor(parcel: Parcel) : this(
        parcel.readString() ?: ""
    )

    override fun delete(): Boolean {
        return true;
    }

    override fun deleteFile(path: String) {
        ProjectFile(path, this).deleteFile()
    }

    fun generateAppProject(): AppProject? {
        return runCatching {
            gson.fromJson(
                "$projectPath/.MyLuaApp/.config.json".toFile().reader(),
                getJavaClass<AppProject>()
            ).apply {
                iconPath = "$projectPath/$iconPath"
                path = projectPath
            }
        }.onFailure {
            it.printStackTrace()
        }
            .getOrNull()
    }

    override fun openFile(path: String): ProjectFile {

        val openFilePath = getAbsoluteFile(path)

        val bean = getOpenedFileBean()

        return bean?.run {
            nowOpenFile = openFilePath
            if (!openedFiles.contains(openFilePath)) openedFiles.add(openFilePath)
            saveOpenedFiles(this)
            Log.e("openFile", "$path $nowOpenFile")
            ProjectFile(openFilePath, this@Project)
        } ?: throw Exception("Open File fail")


    }

    private fun getAbsoluteFile(path: String): String {
        var path = path
        if (!path.toFile().exists() || path == "") {
            path = "$projectPath/$path"
            if (!path.toFile().exists()) throw FileNotFoundException("Not Found File.")
            return path
        }
        return path
    }

    private fun saveOpenedFiles(bean: OpenedFilesBean) {
        Log.d("test", "save bean $bean")
        gson.toJson(bean).apply {
            getOpenedFile().writeText(this)
        }
    }

    fun postNowOpenedDir(nowOpenedDir: String) {
        getOpenedFileBean()?.apply {
            this.nowOpenedDir = nowOpenedDir
        }?.let { saveOpenedFiles(it) }
    }

    fun getNowOpenedDir(): String {
        return getOpenedFileBean()?.nowOpenedDir ?: getAbsoluteFile("")
    }

    fun selectOpenedFile(nowOpenFile: String) {
        getOpenedFileBean()?.apply {
            this.nowOpenFile = nowOpenFile
        }?.let { saveOpenedFiles(it) }
    }

    private fun getOpenedFile(): File {
        val path = "$projectPath/.MyLuaApp/opened_files.json"
        val file = path.toFile()
        if (!file.exists()) {
            file.writeText(
                """
                    {
                      openedFiles = [],
                      nowOpenFile = "",
                      nowOpenedDir = ""
                    }
                """.trimIndent()
            )
        }
        return file
    }


    private fun getOpenedFileBean(): OpenedFilesBean? {
        return runCatching {
            gson.fromJson(
                getOpenedFile().reader(),
                getJavaClass<OpenedFilesBean>()
            )?.apply {
                nowOpenedDir = getAbsoluteFile(nowOpenedDir)
                println(nowOpenedDir)
            }
        }.onFailure {
            it.printStackTrace(System.out)
        }
            .getOrNull()
    }

    override fun getOpenedFiles(): Pair<List<ProjectFile>, String> {

        val bean = getOpenedFileBean()

        val result = mutableListOf<ProjectFile>()
        bean?.openedFiles?.forEach {
            result.add(ProjectFile(it, this))
        }
        return result to (bean?.nowOpenFile ?: "")
    }


    override fun saveAllOpenedFile(): Boolean {
        return getOpenedFiles().first.map {
            it.saveChange()
        }.filter { !it }.size > 1
    }

    override fun saveOpenedFile(path: String): Boolean {
        return ProjectFile(path, this).saveChange()
    }

    override fun closeOpenedFile(path: String, nowOpenedFile: String) {
        getOpenedFileBean()?.let { bean ->
            bean.openedFiles.remove(getAbsoluteFile(path))
            val nowOpenFilePath = getAbsoluteFile(nowOpenedFile)
            bean.nowOpenFile = nowOpenFilePath
            saveOpenedFiles(bean)
        }

    }

    override fun closeOtherOpenedFile(path: String) {
        getOpenedFileBean()?.let { bean ->
            bean.openedFiles.clear()
            bean.openedFiles.add(path)
            bean.nowOpenFile = path
            saveOpenedFiles(bean)
        }
    }

    override fun backup(exportOutputStream: OutputStream): Boolean {
        return true;
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(projectPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Project(projectPath='$projectPath', projectManager=$projectManager)"
    }

    companion object CREATOR : Parcelable.Creator<Project> {
        override fun createFromParcel(parcel: Parcel): Project {
            return Project(parcel)
        }

        override fun newArray(size: Int): Array<Project?> {
            return arrayOfNulls(size)
        }
    }


    data class OpenedFilesBean(
        var nowOpenFile: String,
        val openedFiles: MutableList<String>,
        var nowOpenedDir: String
    )


}