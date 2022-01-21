package com.dingyi.myluaapp.build.api.project

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.script.ModuleScript

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

    fun init()

    fun getDependencies():List<Dependency>


    fun getFileManager():FileManager



}