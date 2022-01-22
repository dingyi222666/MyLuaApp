package com.dingyi.myluaapp.build.dependency

import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import java.io.File

class LocalMavenDependency(
    private val pomPath:String
):MavenDependency {
    override val groupId: String
        get() = TODO("Not yet implemented")
    override val artfactId: String
        get() = TODO("Not yet implemented")
    override val version: String
        get() = TODO("Not yet implemented")
    override val type: String
        get() = TODO("Not yet implemented")

    override fun getDependencies(): List<MavenDependency>? {
        TODO("Not yet implemented")
    }

    override val name: String
        get() = TODO("Not yet implemented")

    override fun getDependenciesFile(): List<File> {
        TODO("Not yet implemented")
    }
}