package com.dingyi.myluaapp.plugin.api.project

import java.io.File

interface FileTemplate {
    val name:String


    suspend fun create(directory:File,name:String):Boolean
}