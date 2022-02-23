package com.dingyi.myluaapp.plugin.api.project

import java.io.File

interface Project {

    fun backup(exportPath:File)


    fun deleteFile(targetFile:File)



    fun walkProjectFile():FileTreeWalk


    val name:String

    val packageName:String?

    val path:File

    val iconPath:String?

    val type:String


}