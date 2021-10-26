package com.dingyi.myluaapp.core.project

import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:10
 * @description: project bean class
 **/
class Project(
    val projectPath: String = "",
    val projectManager: ProjectManager
) : IProject {


    data class AppProject(
        val appPackageName: String,
        val appName: String,
        val iconPath: String,
        val path: String
    )

    fun openFile(filePath: String) {

    }

    override fun delete(): Boolean {
        return true;
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

    override fun openFile(): ProjectFile {
        TODO("Not yet implemented")
    }

    override fun getOpenFiles(): List<ProjectFile> {
        TODO("Not yet implemented")
    }

    override fun saveAllOpenFile(): Boolean {
        TODO("Not yet implemented")
    }

    override fun backup(exportOutputStream: OutputStream): Boolean {
        return true;
    }

    fun getChangeProjectFiles() {

    }


}