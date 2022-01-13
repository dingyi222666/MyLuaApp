package com.dingyi.myluaapp.builder.api.internal.file

import com.dingyi.myluaapp.builder.api.file.FileTree
import com.dingyi.myluaapp.builder.api.internal.BuilderInternal
import java.io.File

class DefaultFileResolver(builder: BuilderInternal):FileResolver {

    override fun resolve(path: Any): File {
        TODO()
    }

    override fun resolveFiles(vararg paths: Any): Sequence<File> {
        TODO("Not yet implemented")
    }

    override fun resolveFilesAsTree(vararg paths: Any?): FileTree {
        TODO("Not yet implemented")
    }

    override fun resolveAsRelativePath(path: Any?): String {
        TODO("Not yet implemented")
    }

}