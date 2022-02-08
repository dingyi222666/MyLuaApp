package com.dingyi.myluaapp.build.dependency

import java.util.*

data class MavenPom(
    val groupId: String,
    val artifactId: String,

    val versionName: String,
    val name: String,
    val packaging: String,
    val dependencies: List<String>
) {


    override fun hashCode(): Int {
        return Objects.hash(artifactId, groupId)
    }

    override fun toString(): String {
        return "$groupId:$artifactId:$versionName"
    }

    fun getFileName(): String {
        return "$artifactId-$versionName"
    }

    fun getPath(): String {
        val path = groupId.replace('.', '/')
        val artifact = artifactId.replace('.', '/')
        return "$path/$artifact/$versionName"
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MavenPom

        if (groupId != other.groupId) return false
        if (artifactId != other.artifactId) return false

        return true
    }

    fun getDeclarationString(): String {
        return "MavenPom(groupId='$groupId', artifactId='$artifactId', versionName='$versionName', packaging='$packaging', dependencies=$dependencies)"
    }


}