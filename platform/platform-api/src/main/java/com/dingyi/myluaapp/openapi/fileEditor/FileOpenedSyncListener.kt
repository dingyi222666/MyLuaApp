package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.fileEditor.ex.FileEditorWithProvider
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.dingyi.myluaapp.util.messages.Topic


interface FileOpenedSyncListener {
    companion object {
        @Topic.ProjectLevel
        @JvmField
        val TOPIC = Topic(FileOpenedSyncListener::class.java, Topic.BroadcastDirection.TO_PARENT, true)
    }

    /**
     * This method is called synchronously (in the same EDT event), as the creation of {@link FileEditor}s.
     */
    fun fileOpenedSync(source: FileEditorManager, file: VirtualFile, editorsWithProviders: List<FileEditorWithProvider>) {}
}