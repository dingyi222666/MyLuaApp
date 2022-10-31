package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.fileEditor.ex.FileEditorWithProvider
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.dingyi.myluaapp.util.messages.Topic
import com.intellij.openapi.util.Pair
import com.intellij.util.containers.ContainerUtil

import java.util.EventListener


/**
 * Listener for [FileEditorManager] events. All methods are invoked in EDT.
 */
interface FileEditorManagerListener : EventListener {



    /**
     * This method is called after the focus settles down (if requested) in a newly created [FileEditor].
     * Be aware that this isn't always true in the case of asynchronously loaded editors, which, in general,
     * may happen with any text editor. In that case, the focus request is postponed until after the editor is fully loaded,
     * which means that it may gain the focus way after this method is called.
     * When necessary, use [FileEditorManager.runWhenLoaded]) to ensure the desired ordering.
     *
     *
     * [.fileOpenedSync] is always invoked before this method,
     * either in the same or the previous EDT event.
     *
     * @see .fileOpenedSync
     */
    fun fileOpened(source: FileEditorManager, file: VirtualFile) {}
    fun fileClosed(source: FileEditorManager, file: VirtualFile) {}
    fun selectionChanged(event: FileEditorManagerEvent) {}
    interface Before : EventListener {
        fun beforeFileOpened(source: FileEditorManager, file: VirtualFile) {}
        fun beforeFileClosed(source: FileEditorManager, file: VirtualFile) {}

        companion object {
            /**
             * file editor before events
             */
            val FILE_EDITOR_MANAGER: Topic<Before> = Topic(
                Before::class.java, Topic.BroadcastDirection.TO_PARENT
            )
        }
    }

    companion object {
        @Topic.ProjectLevel
        val FILE_EDITOR_MANAGER: Topic<FileEditorManagerListener> = Topic(
            FileEditorManagerListener::class.java, Topic.BroadcastDirection.TO_PARENT
        )
    }
}
