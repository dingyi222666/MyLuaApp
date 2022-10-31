package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import org.json.JSONObject

/**
 * Should be registered via [.EP_FILE_EDITOR_PROVIDER].
 * @see DumbAware
 */
interface FileEditorProvider {
    /**
     * The method is expected to run fast.
     *
     * @param file file to be tested for acceptance.
     * @return `true` if provider can create valid editor for the specified `file`.
     */
    fun accept(project: Project, file: VirtualFile): Boolean

    /**
     * Creates editor for the specified file.
     *
     *
     * This method is called only if the provider has accepted this file (i.e. method [.accept] returned
     * `true`).
     * The provider should return only valid editor.
     *
     * @return created editor for specified file.
     */

    fun createEditor(project: Project, file: VirtualFile): FileEditor

    /**
     * Disposes the specified `editor`. It is guaranteed that this method is invoked only for editors
     * created with this provider.
     *
     * @param editor editor to be disposed.
     */
    fun disposeEditor(editor: FileEditor) {
        Disposer.dispose(editor)
    }

    /**
     * Deserializes state from the specified `sourceElement`.
     */

    fun readState(
        sourceElement: JSONObject,
        project: Project,
        file: VirtualFile
    ): FileEditorState {
        return FileEditorState.INSTANCE
    }

    /**
     * Serializes state into the specified `targetElement`.
     */
    fun writeState(
        state: FileEditorState,
        project: Project,
        targetElement: JSONObject
    ) {
    }


    val editorTypeId: String?


    companion object {
        val EP_FILE_EDITOR_PROVIDER: ExtensionPointName<FileEditorProvider> =
            ExtensionPointName("com.intellij.fileEditorProvider")
        val KEY: Key<FileEditorProvider> = Key.create("com.intellij.fileEditorProvider")
        val EMPTY_ARRAY = arrayOf<FileEditorProvider>()
    }
}