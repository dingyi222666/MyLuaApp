package com.dingyi.myluaapp.openapi.vfs

import org.apache.commons.vfs2.FileName
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager
import org.apache.commons.vfs2.FileSystemOptions
import org.apache.commons.vfs2.NameScope
import java.io.File
import java.net.URI
import java.net.URL

interface VirtualFileSystemManager : FileSystemManager {

    override fun resolveFile(name: String): VirtualFile?

    override fun resolveFile(url: URL?): VirtualFile?

    override fun resolveFile(uri: URI?): VirtualFile?

    override fun resolveFile(baseFile: File, name: String): VirtualFile?

    override fun resolveFile(baseFile: FileObject?, name: String?): VirtualFile?
    override fun resolveFile(name: String?, fileSystemOptions: FileSystemOptions?): VirtualFile?
}