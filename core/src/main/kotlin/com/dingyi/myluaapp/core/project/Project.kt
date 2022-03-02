package com.dingyi.myluaapp.core.project

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.println
import com.dingyi.myluaapp.common.ktx.suffix
import com.dingyi.myluaapp.common.ktx.toFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:10
 * @description: project bean class
 **/
@Deprecated("Now Use Plugin Service to manager project")
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
        var path: String = "",
        var fileTemplates: List<String>
    )


    constructor(parcel: Parcel) : this(
        parcel.readString() ?: ""
    )

    override fun delete(): Boolean {
        return projectPath.toFile().deleteRecursively()
    }

    override fun deleteFile(path: String) {
        val projectBean = getOpenedFileBean()
        val file = path.toFile()
        if (file.isFile) {
            closeOpenedFile(getAbsoluteFile(path), projectBean, false)
            ProjectFile(getAbsoluteFile(path), this).deleteFile()
        } else {

            file.walkBottomUp()
                .forEach {
                    if (it.isFile) {
                        closeOpenedFile(it.path, projectBean, false)
                        Log.d("delete", "status:${ProjectFile(it.path, this).deleteFile()}")
                    }
                }

            file.deleteRecursively()

        }

        projectBean?.let {
            var openedDir = it.nowOpenedDir.toFile()
            while (openedDir.name != "") {
                if (openedDir.exists()) {
                    break
                } else {
                    openedDir =
                        openedDir.parentFile?.absoluteFile?.absolutePath?.toFile()
                            ?: "".toFile()
                }
            }
            val nowOpenedDir = openedDir.path
            it.nowOpenedDir = nowOpenedDir
            println(it)
            saveOpenedFiles(it)

        }
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
        println("abs", path)
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

    override fun closeOpenedFile(path: String) {
        closeOpenedFile(path, autoSave = true)
    }

    private fun closeOpenedFile(
        path: String,
        bean: OpenedFilesBean? = getOpenedFileBean(),
        autoSave: Boolean = true
    ) {
        val absoluteClosePath = getAbsoluteFile(path)
        Log.e("test", "abs1 $absoluteClosePath $path")
        bean?.let { bean ->

            if (!bean.openedFiles.contains(absoluteClosePath)) {
                return
            }

            val selectIndex = bean.openedFiles.indexOf(getAbsoluteFile(bean.nowOpenFile))


            val closeIndex = bean.openedFiles.indexOf(getAbsoluteFile(absoluteClosePath))

            var targetSelectPath =
                bean.openedFiles
                    .getOrElse(0.coerceAtLeast(selectIndex - 1)) {
                        ""
                    }


            if (closeIndex != selectIndex) {
                targetSelectPath = bean.nowOpenFile
            }


            bean.openedFiles.remove(absoluteClosePath)


            Log.e("test", "select $targetSelectPath")

            bean.nowOpenFile = targetSelectPath

            if (autoSave) {
                saveOpenedFiles(bean)
            }
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

    override fun getFileTemplates(templatePath: String): List<Pair<String, String>> {
        val templateJsonList = generateAppProject()?.fileTemplates
        val resultList = mutableListOf<Pair<String, String>>()
        runCatching {
            templateJsonList?.forEach { path ->
                gson.fromJson<List<FileTemplateBeanItem>>(
                    "${templatePath}/${path}".toFile().readText(),
                    object : TypeToken<List<FileTemplateBeanItem>>() {}.type
                ).forEach {
                    resultList.add(it.templateName to it.templatePath)
                }
            }
        }.onFailure {
            it.printStackTrace(System.err)
        }
        return resultList
    }

    override fun createTemplateFile(
        fileName: String,
        createDir: String,
        templateDir: String,
        fileTemplate: String
    ): String {

        val suffix = fileTemplate.toFile().suffix
        val createPath = "$createDir/$fileName${if (suffix.isNotEmpty()) "." else ""}$suffix"

        val templatePath = "$templateDir/$fileTemplate"

        createPath.toFile().apply {
            parentFile?.mkdirs()
            createNewFile()
        }.writeText(templatePath.toFile().readText())

        return createPath
    }

    override fun rename(path: String, toPath: String): Boolean = runCatching {
        val bean = getOpenedFileBean()
        bean?.apply {
            nowOpenedDir = nowOpenedDir.replace(path, toPath)
        }
        return if (path.toFile().isFile) {
            openedFilesRename(path, toPath, bean)
            ProjectFile(path, this).rename(toPath)

        } else {
            path.toFile().apply {
                copyRecursively(toPath.toFile())
            }.walkBottomUp()
                .filter { it.isFile }
                .map {
                    val targetPath = it.path.replace(path, toPath)
                    openedFilesRename(
                        it.path,
                        targetPath,
                        bean
                    )
                    ProjectFile(it.path, this).rename(targetPath)
                }
                .contains(false)
                .not()
                .apply {
                    deleteFile(path)
                }

        }.apply {
            bean?.let(::saveOpenedFiles)
        }
    }.getOrNull() ?: false

    private fun openedFilesRename(
        path: String,
        toPath: String,
        bean: OpenedFilesBean? = getOpenedFileBean()
    ) {
        bean?.apply {
            nowOpenFile = nowOpenFile.replace(path, toPath)
            openedFiles.forEachIndexed { index, s ->
                openedFiles[index] = s.replace(path, toPath)
            }
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


    data class FileTemplateBeanItem(
        val templateName: String, // Lua Empty Layout
        val templatePath: String // file/lua_empty_layout.aly
    )

}