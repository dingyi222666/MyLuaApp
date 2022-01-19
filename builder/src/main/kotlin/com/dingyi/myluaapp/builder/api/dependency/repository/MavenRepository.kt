package com.dingyi.myluaapp.builder.api.dependency.repository

import com.dingyi.myluaapp.builder.api.dependency.MavenDependency

interface MavenRepository {

    fun getDependency(string: String):MavenDependency

    fun getPomPath(mavenDependency: MavenDependency):String


    fun getLastVersion(mavenDependency: MavenDependency):MavenDependency

}