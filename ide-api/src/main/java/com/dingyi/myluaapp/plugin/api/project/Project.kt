package com.dingyi.myluaapp.plugin.api.project

import com.dingyi.myluaapp.editor.lsp.LSPProject
import java.io.File

interface Project {

    fun backup(exportPath:File)


    suspend fun deleteFile(targetFile: File)


    fun walkProjectFile(): FileTreeWalk

    fun runProject()

    fun getFileTemplates(): List<FileTemplate>

    suspend fun renameFile(file: File, targetFile: File)

    suspend fun createDirectory(targetPath: File)


    fun getBuildProject():com.dingyi.myluaapp.build.api.Project

    val name: String

    val packageName: String?

    val path: File

    val iconPath: String?

    val type: String


}