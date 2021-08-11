package com.dingyi.myluaapp.database.bean

import org.litepal.crud.LitePalSupport

/**
 * @author: dingyi
 * @date: 2021/8/8 18:30
 * @description:
 **/
class ProjectConfig : LitePalSupport() {
    var id = 0
    var openFiles: MutableList<CodeFile> = mutableListOf()
    lateinit var projectPath: String
    lateinit var lastOpenDir: String
    override fun toString(): String {
        return "ProjectConfig(lastOpenDir='$lastOpenDir', openFiles=$openFiles, path='$projectPath')"
    }

    fun copy(): ProjectConfig {
        return ProjectConfig().apply {
            openFiles = this@ProjectConfig.openFiles
            projectPath = this@ProjectConfig.projectPath
            lastOpenDir = this@ProjectConfig.lastOpenDir
        }
    }

    fun findCodeFileByPath(path: String): Int? {
        openFiles.forEachIndexed { index, codeFile ->
            if (codeFile.filePath == path) {
                return index
            }
        }
        return null
    }


}
