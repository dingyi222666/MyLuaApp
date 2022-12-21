package com.dingyi.myluaapp.openapi.vfs

import com.dingyi.myluaapp.openapi.fileTypes.FileType
import org.apache.commons.vfs2.FileObject
import java.io.OutputStream
import java.net.CacheRequest
import java.nio.file.Path

interface VirtualFile : FileObject {

    val isWritable: Boolean
    val length: Int

    fun getFileType(): FileType
    fun findChild(name: String): VirtualFile?

    fun isDirectory() = isFolder

    fun getFileName() = name


    fun delete(requestor: Any, useDefaultDelete: Boolean = true)
    override fun getParent(): VirtualFile
    fun isValid(): Boolean

    fun contentsToByteArray(): ByteArray
    fun getOutputStream(requestor: Any?): OutputStream
    fun createChildData(requestor: Any, childPath: String): VirtualFile?
}