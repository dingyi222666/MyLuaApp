package com.dingyi.myluaapp.build.api

import com.dingyi.myluaapp.build.api.file.collection.FileCollection
import com.dingyi.myluaapp.build.api.file.collection.FileTree
import com.dingyi.myluaapp.build.api.properties.Properties
import java.io.File

interface Project:Properties {

    fun getAllProject():Set<Project>

    fun getPath():String

    fun getName():String


    fun file(path:Any): File

    fun fileTree(arg:Map<String,String>): FileTree

    fun files(vararg path: Any):FileCollection

}