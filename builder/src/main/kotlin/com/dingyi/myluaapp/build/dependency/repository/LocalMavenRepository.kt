package com.dingyi.myluaapp.build.dependency.repository

import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.dependency.LocalMavenDependency
import com.dingyi.myluaapp.build.parser.PomParser
import java.io.File
import java.lang.RuntimeException

class LocalMavenRepository(
    private val repositoryPath: String
) : MavenRepository {


    private val parser = PomParser()

    override fun getDependency(string: String): MavenDependency {
        return getDependency(string, mutableSetOf())
    }

    private fun getDependency(string: String, containsList: MutableSet<String>): MavenDependency {
        val array = string.split(":")
        containsList.add(string)
        val targetPomPath =
            "$repositoryPath${File.separator}${
                array[0].replace(
                    ".",
                    File.separator
                )
            }${File.separator}${array[1]}${File.separator}${
                array[2]
            }${File.separator}${array[1]}-${array[2]}.pom"

        if (File(targetPomPath).exists().not()) {
            System.err.println(RuntimeException("Unable to retrieve POM of $string"))

        }

        val pom = parser.parse(targetPomPath)

        val allDependencies = mutableListOf<MavenDependency>().apply {
            pom.dependencies.forEach {
                if (!containsList.contains(it)) {
                    add(getDependency(it,containsList))
                }
            }
        }


        return LocalMavenDependency(pom, allDependencies, repositoryPath)

    }


    override fun getLastVersion(mavenDependency: MavenDependency): MavenDependency {
        TODO("Not yet implemented")
    }
}