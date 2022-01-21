package com.dingyi.myluaapp.build.api.dependency.repository

import com.dingyi.myluaapp.build.api.dependency.MavenDependency

interface MavenRepository {

    fun getDependency(string: String):MavenDependency

    fun getPomPath(mavenDependency: MavenDependency):String


    fun getLastVersion(mavenDependency: MavenDependency):MavenDependency

}