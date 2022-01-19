package com.dingyi.myluaapp.builder.api.dependency

interface MavenDependency:Dependency {

    val groupId:String

    val artfactId:String

    val version:String
    val type:String
    fun getDependencies():List<MavenDependency>?



}