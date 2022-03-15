package com.dingyi.myluaapp.build.api.dependency

import java.io.File

interface Dependency {
    /**
     * Dependency name
     */
    val name: String

    /**
     * Dependency file type
     */
    val type: String

    /**
     * dependencies file
     */
    fun getDependenciesFile(): Set<File>
}