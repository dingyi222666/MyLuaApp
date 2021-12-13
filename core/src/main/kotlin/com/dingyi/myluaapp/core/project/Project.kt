package com.dingyi.myluaapp.core.project

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
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


    data class AppProject(
        val appPackageName: String,
        val appName: String,
        val iconPath: String,
        val path: String
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
            val table = projectManager.globalLuaJVM.loadFile("$projectPath/.MyLuaApp/.config.lua")
            AppProject(
                appName = table["appName"].tojstring(),
                appPackageName = table["appPackageName"].tojstring(),
                iconPath = projectPath + "/" + table["iconPath"].tojstring(),
                path = projectPath
            )
        }.onFailure {
            it.printStackTrace()
        }
            .getOrNull()
    }

    override fun openFile(path: String): ProjectFile {
        var path = path
        if (!path.toFile().exists()) {
            path = "$projectPath/$path"
            if (!path.toFile().exists())
                throw FileNotFoundException("Not Found File.")
        }
        val file = getOpenedFile()

        val table = projectManager.globalLuaJVM.loadFile(file.absolutePath).get("openFiles")
        .checktable()

        table.keys().forEach {
            if (table[it].tojstring()==path) {
                //不重复写入
                return ProjectFile(path,this)
            }
        }

        table.insert(table.keyCount() + 1, LuaValue.valueOf(path))
        file.writeText(formatOpenFile(table))
        return ProjectFile(path, this)
    }

    private fun formatOpenFile(table: LuaTable): String {
        val buffer = StringBuilder()
        buffer.append("openFiles={").append("\n")
        for (i in 1..table.keyCount()) {
            val value = table[i].tojstring()
            buffer.append('[').append(i)
                .append(']').append(" = ")
                .append('"').append(value)
                .append('"').append(",")
                .append("\n")
        }
        buffer.removeRange(buffer.lastIndex - 2..buffer.lastIndex)
        buffer.append("\n").append("}")
        return buffer.toString()
    }

    private fun getOpenedFile(): File {
        val path = "$projectPath/.MyLuaApp/open_files.lua"
        val file = path.toFile()
        if (!file.exists()) {
            file.writeText(
                """
                openFiles = {}
            """.trimIndent()
            )
        }
        return file
    }

    override fun getOpenedFiles(): List<ProjectFile> {
        val table =
            projectManager.globalLuaJVM.loadFile(getOpenedFile().absolutePath).get("openFiles")
                .checktable()

        val result = mutableListOf<ProjectFile>()
        table.keys().forEach {
            runCatching { table.get(it).checkstring() }.getOrNull()?.let { path ->
                result.add(ProjectFile(path.tojstring(), this))
            }
        }
        return result
    }

    override fun saveAllOpenedFile(): Boolean {
        return getOpenedFiles().map {
            it.saveChange()
        }.filter { !it }.size > 1
    }

    override fun saveOpenedFile(path: String): Boolean {
        return ProjectFile(path, this).saveChange()
    }

    override fun closeOpenedFile(path: String) {
        val table = projectManager.globalLuaJVM.loadFile(getOpenedFile().absolutePath)
        (1..table.keyCount()).forEach {
            if (table[it].tojstring() == path) {
                table.remove(it)
                return@forEach
            }
        }
        getOpenedFile().writeText(formatOpenFile(table))
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


}