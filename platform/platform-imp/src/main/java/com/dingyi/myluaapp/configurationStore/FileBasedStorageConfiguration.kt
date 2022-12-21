package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.openapi.components.StateStorageOperation
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.dingyi.myluaapp.openapi.vfs.VirtualFileSystemManager
import org.apache.commons.vfs2.FileSystem
import org.apache.commons.vfs2.VFS
import org.apache.commons.vfs2.impl.VirtualFileSystem
import org.apache.commons.vfs2.provider.local.LocalFileSystem


interface FileBasedStorageConfiguration {
    val isUseVfsForRead: Boolean

    val isUseVfsForWrite: Boolean

    fun resolveVirtualFile(path: String, reasonOperation: StateStorageOperation) =
        doResolveVirtualFile(path, reasonOperation)
}

internal val defaultFileBasedStorageConfiguration = object : FileBasedStorageConfiguration {
    override val isUseVfsForRead: Boolean
        get() = false

    override val isUseVfsForWrite: Boolean
        get() = true
}

internal fun doResolveVirtualFile(
    path: String,
    reasonOperation: StateStorageOperation
): VirtualFile? {
    //The root file manager is the child of VirtualFileSystemManager
    val fs = VFS.getManager() as VirtualFileSystemManager
    val result = fs.resolveFile(path)
    if (result == null || !result.isValid()) {
        return null
    }

    // otherwise virtualFile.contentsToByteArray() will query expensive FileTypeManager.getInstance()).getByFile()
    //result.setCharset(Charsets.UTF_8, null, false)
    return result
}