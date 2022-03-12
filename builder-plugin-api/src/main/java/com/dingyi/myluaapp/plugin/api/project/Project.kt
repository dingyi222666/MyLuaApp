package com.dingyi.myluaapp.plugin.api.project

import java.io.File

interface Project {

    fun backup(exportPath:File)


    suspend fun deleteFile(targetFile: File)


    fun walkProjectFile(): FileTreeWalk

    fun runProject()

    fun getFileTemplates(): List<FileTemplate>

    suspend fun renameFile(file: File, targetFile: File)

    suspend fun createDirectory(targetPath: File)

    val name: String

    val packageName: String?

    val path: File

    val iconPath: String?

    val type: String


}