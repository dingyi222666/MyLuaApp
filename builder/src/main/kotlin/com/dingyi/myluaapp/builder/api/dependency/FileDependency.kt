package com.dingyi.myluaapp.builder.api.dependency

import java.io.File

interface FileDependency : Dependency {

    fun getPath(): File
    fun getType(): String
}