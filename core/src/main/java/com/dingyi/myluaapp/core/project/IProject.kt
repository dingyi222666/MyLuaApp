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
    fun openFile():ProjectFile
    fun getOpenFiles():List<ProjectFile>
    fun saveAllOpenFile():Boolean

}