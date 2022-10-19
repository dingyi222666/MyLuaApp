package com.dingyi.myluaapp.openapi.vfs

import com.dingyi.myluaapp.openapi.fileTypes.FileType
import org.apache.commons.vfs2.FileObject

interface VirtualFile:FileObject {

    fun getFileType():FileType
}