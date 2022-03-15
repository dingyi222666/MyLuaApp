package com.dingyi.myluaapp.build.api.dependency

import java.io.File

interface MavenDependency : Dependency {

    /**
     * Dependency group Id
     */
    val groupId: String

    val artifactId: String

    val versionName: String

    val packaging: String
    fun getDependencies(): List<MavenDependency>?

    fun getDeclarationString(): String

    fun getDependencyFile(): File


    var isDynamicVersion:Boolean


}