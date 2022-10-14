package com.dingyi.myluaapp.openapi.project

import org.apache.commons.vfs2.FileObject

fun interface ProjectScanner {

    /**
     * Scan the folder for projects that may be included
     */
    fun scanByFolder(file: FileObject): List<Project>
}