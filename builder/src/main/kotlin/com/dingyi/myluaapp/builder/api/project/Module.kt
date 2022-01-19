package com.dingyi.myluaapp.builder.api.project

import com.dingyi.myluaapp.builder.api.builder.Builder
import com.dingyi.myluaapp.builder.api.dependency.Dependency
import com.dingyi.myluaapp.builder.api.file.FileManager
import com.dingyi.myluaapp.builder.api.script.ModuleScript
import java.io.File

interface Module:ModuleScript {
    /**
     * Get Module Type
     */
    val type:String

    /**
     * Get Module Name
     */
    val name:String


    fun getBuilder():Builder


    fun getDependencies():List<Dependency>


    fun getFileManager():FileManager



}