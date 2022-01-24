package com.dingyi.myluaapp.core.project

import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/23 15:26
 * @description:
 **/
interface IProject {
    fun backup(exportOutputStream: OutputStream): Boolean
    fun delete(): Boolean
    fun deleteFile(path: String)
    fun openFile(path: String): ProjectFile
    fun getOpenedFiles(): Pair<List<ProjectFile>, String>
    fun saveAllOpenedFile(): Boolean
    fun saveOpenedFile(path: String): Boolean

    fun closeOtherOpenedFile(path: String)
    fun getFileTemplates(templatePath: String): List<Pair<String, String>>
    fun rename(path: String, toPath: String): Boolean
    fun createTemplateFile(
        fileName: String,
        createDir: String,
        templateDir: String,
        fileTemplate: String
    ): String

    fun closeOpenedFile(path: String)
}