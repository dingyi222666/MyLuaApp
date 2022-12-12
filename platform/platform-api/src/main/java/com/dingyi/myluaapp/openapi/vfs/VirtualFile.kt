package com.dingyi.myluaapp.openapi.vfs

import com.dingyi.myluaapp.openapi.fileTypes.FileType
import org.apache.commons.vfs2.FileObject
import java.net.CacheRequest

interface VirtualFile : FileObject {

    val length:Int

    fun getFileType(): FileType
    fun findChild(name: String): VirtualFile?

    fun isDirectory() = isFolder

    fun getFileName() = name


    fun delete(requestor: Any, useDefaultDelete: Boolean = true)

    override fun getParent(): VirtualFile
    fun isValid(): Boolean

    fun contentsToByteArray(): ByteArray
}