package com.dingyi.myluaapp.openapi.fileEditor

import android.view.View
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolder
import org.apache.commons.vfs2.FileObject
import java.util.Collections


/**
 * @see TextEditor
 */
interface FileEditor : UserDataHolder, Disposable {
    /**
     * Returns a component which represents the editor in UI.
     */
    fun getView(): View


    /**
     * Returns `true` when editor's content differs from its source (e.g. a file).
     */
    fun isModified(): Boolean


    companion object {

        val FILE_KEY: Key<VirtualFile> = Key.create("FILE_KEY")
    }

    /**
     * Returns the file for which [FileEditorProvider.createEditor] was called.
     * The default implementation is temporary, and shall be dropped in the future.
     */
    fun getFile(): VirtualFile? {
        /*PluginException.reportDeprecatedDefault(
            javaClass,
            "getFile",
            "A proper @NotNull implementation required"
        )*/
        return FILE_KEY.get(this)
    }

    /**
     * Returns the files for which [com.intellij.ide.SaveAndSyncHandler] should be called on frame activation.
     */
    fun getFilesToRefresh(): List<VirtualFile> {
        val file = getFile()
        return if (file == null) Collections.emptyList() else Collections.singletonList(file)
    }

    /**
     * Returns an action group that will be displayed on the right side of the Editor tabs
     */
    fun getTabActions(): ActionGroup? {
        return null
    }
}