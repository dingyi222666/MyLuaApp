package com.dingyi.myluaapp.build.dependency.repository

import android.util.Log
import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.dependency.LocalMavenDependency
import com.dingyi.myluaapp.build.parser.PomParser
import com.dingyi.myluaapp.common.kts.toFile
import java.io.File
import java.lang.RuntimeException

class LocalMavenRepository(
    private val repositoryPath: String,
    private val logger:ILogger
) : MavenRepository {

    init {
        val file = File(repositoryPath)

        if (!file.isDirectory) {
            file.mkdirs()
        }
    }

    private val parser = PomParser()

    override fun getDependency(string: String): List<MavenDependency> {
        return getDependency(string, mutableSetOf())
    }

    private fun getDefaultPomPath(groupId:String,artifactId:String,version:String):String {
        return "$repositoryPath${File.separator}${
            groupId.replace(
                ".",
                File.separator
            )
        }${File.separator}${artifactId}${File.separator}${
            version
        }${File.separator}${artifactId}-${version}.pom"

    }

    private fun getDependency(string: String, containsList: MutableSet<String>): List<MavenDependency> {
        val array = string.split(":")
        containsList.add(string)

        val targetPomPathList = mutableListOf<String>()
        Log.e("parse",array.joinToString())
        if (array[1]=="*") {
            val pomDir =  "$repositoryPath${File.separator}${
                array[0].replace(
                    ".",
                    File.separator
                )
            }${File.separator}"

            pomDir.toFile().listFiles()?.forEach {
              targetPomPathList.add(
                  getDefaultPomPath(array[0],it.name,array[2])
              )
            }

        } else {
            targetPomPathList.add(getDefaultPomPath(array[0],array[1],array[2]))
        }

        targetPomPathList.forEach {
            if (File(it).exists().not()) {
                logger.waring("Unable to retrieve POM of $string,try sync project to re download POM")
                logger.info("\n")
            }
        }

        return targetPomPathList.mapNotNull { pomPath ->

            if (!pomPath.toFile().exists()) {
                return@mapNotNull null
            }

            val pom = parser.parse(pomPath)

            val allDependencies = mutableListOf<MavenDependency>().apply {
                pom.dependencies.forEach {
                    if (!containsList.contains(it)) {
                        addAll(getDependency(it, containsList))
                    }
                }
            }

            LocalMavenDependency(pom, allDependencies, repositoryPath)
        }

    }


    override fun getLastVersion(mavenDependency: MavenDependency): MavenDependency {
        TODO("Not yet implemented")
    }
}