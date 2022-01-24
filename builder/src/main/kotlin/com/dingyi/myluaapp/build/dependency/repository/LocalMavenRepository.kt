package com.dingyi.myluaapp.build.dependency.repository

import com.dingyi.myluaapp.build.api.dependency.MavenDependency
import com.dingyi.myluaapp.build.api.dependency.repository.MavenRepository

class LocalMavenRepository(
    private val repositoryPath: String
) : MavenRepository {
    override fun getDependency(string: String): MavenDependency {
        TODO("Not yet implemented")
    }


    override fun getLastVersion(mavenDependency: MavenDependency): MavenDependency {
        TODO("Not yet implemented")
    }
}