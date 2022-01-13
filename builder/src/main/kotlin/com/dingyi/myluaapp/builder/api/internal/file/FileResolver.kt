package com.dingyi.myluaapp.builder.api.internal.file

import com.dingyi.myluaapp.builder.api.file.FileTree

import java.io.File


interface FileResolver {
    fun resolve(path: Any): File


    fun resolveFiles(vararg paths: Any): Sequence<File>
    fun resolveFilesAsTree(vararg paths: Any?): FileTree

    fun resolveAsRelativePath(path: Any?): String

}