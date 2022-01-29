package com.dingyi.myluaapp.build.api.project

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.file.FileManager
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.api.script.ModuleScript


interface Module : ModuleScript {
    /**
     * Get Module Type
     */
    val type: String

    /**
     * Get Module Name
     */
    val name: String


    fun getBuilder(): Builder

    fun init()

    fun getDependencies(): List<Dependency>


    fun getPath():String

    fun getFileManager(): FileManager

    fun getProject(): Project


    fun getLogger(): ILogger

    fun getMavenRepository(): MavenRepository


}