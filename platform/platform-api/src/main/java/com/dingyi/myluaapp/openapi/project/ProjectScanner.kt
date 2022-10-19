package com.dingyi.myluaapp.openapi.project

import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import org.apache.commons.vfs2.FileObject

fun interface ProjectScanner {

    /**
     * Scan the folder for projects that may be included
     */
    fun scanByFolder(file: VirtualFile): List<Project>
}