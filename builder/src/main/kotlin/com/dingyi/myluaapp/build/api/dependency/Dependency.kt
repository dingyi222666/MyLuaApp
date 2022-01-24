package com.dingyi.myluaapp.build.api.dependency

import java.io.File

interface Dependency {
    val name: String

    val type: String

    fun getDependenciesFile(): Set<File>
}