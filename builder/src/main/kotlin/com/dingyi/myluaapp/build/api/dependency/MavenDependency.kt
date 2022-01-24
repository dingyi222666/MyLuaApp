package com.dingyi.myluaapp.build.api.dependency

interface MavenDependency : Dependency {

    var groupId: String

    var artfactId: String

    var version: String
    var type: String
    fun getDependencies(): List<MavenDependency>?


}