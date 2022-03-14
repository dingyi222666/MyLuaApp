package com.dingyi.myluaapp.build.dependency

import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import java.io.File
import java.util.*

class LocalMavenDependency(
    private val mavenPom: MavenPom,
    private val allDependencies: List<MavenDependency>,
    private val repositoryPath: String
) : MavenDependency {

    override val groupId: String
        get() = mavenPom.groupId
    override val artifactId: String
        get() = mavenPom.artifactId
    override val versionName: String
        get() = mavenPom.versionName
    override val type: String
        get() = mavenPom.packaging
    override val packaging: String
        get() = mavenPom.packaging

    override var isDynamicVersion: Boolean = false


    override fun getDependencies(): List<MavenDependency> {
        return allDependencies
    }

    override fun getDeclarationString(): String {
        return mavenPom.toString()
    }

    override var name: String = mavenPom.name

    override fun getDependenciesFile(): Set<File> {
        return setOf(getDependencyFile())
        //不要去一直引用下层依赖
        /*
        return mutableSetOf<File>().apply {
            add(getDependencyFile())
            allDependencies.forEach {
                addAll(it.getDependenciesFile())
            }
        } */
    }

    override fun getDependencyFile(): File {
        return File("${getDependencyFileDirectory()}/${getFileName()}.$type")
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
        return "LocalMavenDependency(groupId='$groupId', artifactId='$artifactId', versionName='$versionName', packaging='$packaging')"
        //return "LocalMavenDependency(groupId='$groupId', artifactId='$artifactId', versionName='$versionName', packaging='$packaging', dependencies=$allDependencies)"
    }
}