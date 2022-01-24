package com.dingyi.myluaapp.build.dependency

import com.dingyi.myluaapp.build.api.dependency.Dependency
import com.dingyi.myluaapp.common.kts.toFile
import java.io.File

class FileDependency(
    private val path: String
) : Dependency {

    private val file = path.toFile()

    override val type: String
        get() = file.name

    override val name: String
        get() = file.name

    override fun getDependenciesFile(): Set<File> {
        return setOf(file)
    }
}