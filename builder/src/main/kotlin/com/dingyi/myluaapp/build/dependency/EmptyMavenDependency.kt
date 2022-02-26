package com.dingyi.myluaapp.build.dependency

import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import java.io.File
import java.util.*


class EmptyMavenDependency(

    private val repositoryPath: String,
    override val groupId: String,
    override val artifactId: String,
    override val versionName: String
) : MavenDependency {

    override val type: String
        get() = "default"
    override val packaging: String
        get() = "default"


    override fun getDependencies(): List<MavenDependency> {
        return mutableListOf()
    }

    override fun getDeclarationString(): String {
        return "$groupId:$artifactId:$versionName"
    }

    override fun getDependencyFile(): File {
        return File("")
    }

    override var name: String = "empty"

    override fun getDependenciesFile(): Set<File> {
        return setOf(getDependencyFile())
    }

    override fun hashCode(): Int {
        return Objects.hash(artifactId, groupId)
    }

    private fun getFileName(): String {
        return "$artifactId-$versionName"
    }

    fun getPath(): String {
        val path = groupId.replace('.', '/')
        val artifact = artifactId.replace('.', '/')
        return "$path/$artifact/$versionName"
    }

    private fun getDependencyFileDirectory(): String {
        return "$repositoryPath/${getPath()}"
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MavenDependency

        if (groupId != other.groupId) return false
        if (artifactId != other.artifactId) return false
        if (versionName != other.versionName) return false
        return true
    }

    override fun toString(): String {
        return "EmptyMavenDependency(groupId='$groupId', artifactId='$artifactId', versionName='$versionName', packaging='$packaging')"
        //return "LocalMavenDependency(groupId='$groupId', artifactId='$artifactId', versionName='$versionName', packaging='$packaging', dependencies=$allDependencies)"
    }
}