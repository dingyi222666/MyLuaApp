package com.dingyi.myluaapp.builder.api.dependency

import java.io.File

interface Dependency {
    val name:String


    fun getDependenciesFile():List<File>
}