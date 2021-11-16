package com.dingyi.myluaapp.core.project

import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/23 15:26
 * @description:
 **/
interface IProject {
    fun backup(exportOutputStream: OutputStream):Boolean
    fun delete():Boolean
    fun deleteFile(path: String)
    fun openFile(path:String):ProjectFile
    fun getOpenedFiles():List<ProjectFile>
    fun saveAllOpenedFile():Boolean
    fun saveOpenedFile(path:String): Boolean
    fun closeOpenedFile(path:String)
}