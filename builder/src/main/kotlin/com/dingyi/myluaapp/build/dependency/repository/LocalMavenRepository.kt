package com.dingyi.myluaapp.build.dependency.repository

import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.dependency.EmptyMavenDependency
import com.dingyi.myluaapp.build.dependency.LocalMavenDependency
import com.dingyi.myluaapp.build.dependency.MavenPom
import com.dingyi.myluaapp.build.parser.MavenMetaDataParser
import com.dingyi.myluaapp.build.parser.PomParser
import com.dingyi.myluaapp.common.ktx.toFile
import java.io.File

class LocalMavenRepository(
    private val repositoryPath: String,
    private val logger: ILogger
) : MavenRepository {

    init {
        val file = File(repositoryPath)

        if (!file.isDirectory) {
            file.mkdirs()
        }
    }

    private val parsedPom = mutableMapOf<MavenPom, MavenDependency>()

    private val parser = PomParser()

    private val metaDataParser = MavenMetaDataParser()

    override fun getDependency(string: String): MavenDependency {
        return getDependency(string, mutableSetOf())
    }

    override fun getDependency(string: String, exclusionList: MutableSet<String>): MavenDependency {

        val (groupId, artifactId, versionName) = string.split(":")

        val mavenMetaData = kotlin.runCatching {
            metaDataParser.parse(
                repositoryPath + File.separator + groupId.replaceNameToPath()
                        + File.separator + artifactId.replaceNameToPath() + File.separator + "/maven-metadata.xml"
            )
        }.getOrNull()


        var dynamicVersion = false


        val pom = if (versionName == "latest") {
            dynamicVersion = true

            if (mavenMetaData==null) {
               return EmptyMavenDependency(
                    repositoryPath = this.repositoryPath,
                    groupId = groupId,
                    artifactId = artifactId,
                    versionName = versionName
                ).apply {
                    this.isDynamicVersion = dynamicVersion
                }
            }

            getMavenPom(
                groupId,
                artifactId,
                mavenMetaData.versioning.latest.toString()
            )
        } //TODO: support resolve xx:xx:xx.+
        else {
            getMavenPom(groupId, artifactId, versionName)
        }

        if (pom == null) {
            return EmptyMavenDependency(
                repositoryPath = this.repositoryPath,
                groupId = groupId,
                artifactId = artifactId,
                versionName = versionName
            ).apply {
                this.isDynamicVersion = dynamicVersion
            }
        }

        return getMavenDependency(pom, exclusionList).apply {
            this.isDynamicVersion = dynamicVersion
        }
    }


    private fun getMavenPom(
        groupId: String,
        artifactId: String,
        versionName: String,
    ): MavenPom? {
        val pomPath = getDefaultPomPath(groupId, artifactId, versionName)

        if (!pomPath.toFile().exists()) {
            logger.warning("\n")
            logger.warning("w:Unable to retrieve dependency of $groupId:$artifactId:$versionName,try sync project to re download dependency")

            return null
        }


        return parser.parse(pomPath)


    }

    private fun String.replaceNameToPath(): String {
        return replace(".", File.separator)
    }

    private fun getDefaultPomPath(
        groupId: String,
        artifactId: String,
        version: String
    ): String {
        return "$repositoryPath${File.separator}${
            groupId.replace(
                ".",
                File.separator
            )
        }${File.separator}${artifactId}${File.separator}${
            version
        }${File.separator}${artifactId}-${version}.pom"

    }

    private fun getMavenDependency(
        dependency: MavenPom,
        exclusionList: MutableSet<String>
    ): MavenDependency {

        if (parsedPom.keys.contains(dependency)) {
            return parsedPom.getValue(dependency)
        }

        val dependencies = mutableListOf<MavenDependency>()

        dependency
            .dependencies
            .filterNot {
                exclusionList.contains(it.dependencyId)
            }
            .map {
                val targetExclusionList = exclusionList.toMutableSet()
                    .apply { addAll(it.exclusions) }
                val (groupId, artifactId, versionName) = it.dependencyId.split(":")
                val pom = getMavenPom(groupId, artifactId, versionName)

                if (pom != null) {
                    getMavenDependency(pom, targetExclusionList)
                } else EmptyMavenDependency(
                    repositoryPath = this.repositoryPath,
                    groupId = groupId,
                    artifactId = artifactId,
                    versionName = versionName
                )
            }.forEach {
                dependencies.add(it)
            }

        return LocalMavenDependency(dependency, dependencies, repositoryPath)

    }


    override fun getLastVersion(mavenDependency: MavenDependency): MavenDependency {
        TODO("Not yet implemented")
    }

    override fun clear() {
        parsedPom.clear()
    }
}