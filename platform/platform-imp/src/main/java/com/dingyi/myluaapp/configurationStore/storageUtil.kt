package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.ide.application.runInThreadPool
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.dingyi.myluaapp.openapi.vfs.VirtualFileSystemManager
import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.HtmlChunk
import org.apache.commons.vfs2.VFS
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.TestOnly
import java.io.IOException
import java.nio.file.Path
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import kotlin.io.path.createDirectories


@NonNls
const val NOTIFICATION_GROUP_ID = "Load Error"


fun getOrCreateVirtualFile(file: Path, requestor: StorageManagerFileWriteRequestor): VirtualFile {
    val vfs = (VFS.getManager() as VirtualFileSystemManager)
    var virtualFile = vfs.resolveFile(file.systemIndependentPath)
    if (virtualFile == null) {
        val parentFile = file.parent
        parentFile.createDirectories()

        // need refresh if the directory has just been created
        val parentVirtualFile = vfs.resolveFile(parentFile.systemIndependentPath)
            ?: throw IOException("???") //ProjectBundle.message("project.configuration.save.file.not.found", parentFile))

        virtualFile =
            parentVirtualFile.createChildData(requestor, file.fileName.toString())

    }

    return checkNotNull( virtualFile)
}

