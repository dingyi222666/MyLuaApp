package com.dingyi.myluaapp.builder.api.dependency

interface Dependency {


    fun getGroup(): String

    fun getName(): String

    fun getVersion(): String


    fun contentEquals(dependency: Dependency): Boolean

    fun copy(): Dependency
}