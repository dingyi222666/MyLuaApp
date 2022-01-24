package com.dingyi.myluaapp.build.dependency

import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import java.io.File

class LocalMavenDependency(
    private val mavenPom: MavenPom,
    private val pomPath: String
) : MavenDependency {


    override var groupId: String = ""
    override var artfactId: String = ""

    override var version: String = ""

    override var type: String = ""

    override fun getDependencies(): List<MavenDependency>? {
        TODO("Not yet implemented")
    }

    override var name: String = ""

    override fun getDependenciesFile(): List<File> {
        TODO("Not yet implemented")
    }
}