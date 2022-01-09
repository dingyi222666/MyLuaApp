package com.dingyi.myluaapp.builder.api.dependency

import java.io.File

interface MavenDependency {

    fun getPomPath(mavenUrl: String): String

    fun checkVersion(mavenUrl: String): String

    fun getDependencyFiles(): List<File>


}