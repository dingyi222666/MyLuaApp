package com.dingyi.myluaapp.build.api.dependency

interface MavenDependency : Dependency {

    val groupId: String

    val artifactId: String

    val versionName: String

    val packaging:String
    fun getDependencies(): List<MavenDependency>?


}