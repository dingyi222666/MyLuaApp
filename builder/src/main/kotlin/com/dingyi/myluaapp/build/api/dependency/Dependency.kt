package com.dingyi.myluaapp.build.api.dependency

import java.io.File

interface Dependency {
    val name:String


    fun getDependenciesFile():List<File>
}