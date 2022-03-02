package com.dingyi.myluaapp.plugin.runtime.project

import com.dingyi.myluaapp.common.ktx.suffix
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.project.FileTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DefaultFileTemplate(
    private val path: String,
    override val name: String
) : FileTemplate {

    override suspend fun create(directory: File, name: String): Boolean = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            val suffix = path.toFile().suffix
            val createPath = "${directory.path}/$name${if (suffix.isNotEmpty()) "." else ""}$suffix"
            val file = createPath.toFile()
            file.createNewFile()
            file.writeText(path.toFile().readText())
        }.isSuccess
    }
}