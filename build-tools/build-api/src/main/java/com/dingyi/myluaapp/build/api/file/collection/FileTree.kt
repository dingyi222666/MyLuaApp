package com.dingyi.myluaapp.build.api.file.collection

import java.io.File

interface FileTree:FileCollection {

    fun include(pattern:String): FileTree

    fun exclude(pattern:String): FileTree

    fun dir(name:String): FileTree

    fun toCollection(): FileCollection

    fun visit(block:(File)->Unit)


}