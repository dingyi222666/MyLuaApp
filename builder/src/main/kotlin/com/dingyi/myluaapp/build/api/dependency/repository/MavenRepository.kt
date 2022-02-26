package com.dingyi.myluaapp.build.api.dependency.repository

import com.dingyi.myluaapp.build.api.dependency.MavenDependency

interface MavenRepository {

    fun getDependency(string: String): MavenDependency



    fun getLastVersion(mavenDependency: MavenDependency): MavenDependency


    fun clear()

    fun getDependency(string: String, exclusionList: MutableSet<String>): MavenDependency
}