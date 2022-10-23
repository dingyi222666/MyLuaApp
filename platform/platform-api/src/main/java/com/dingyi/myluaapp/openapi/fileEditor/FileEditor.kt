package com.dingyi.myluaapp.openapi.fileEditor

import android.view.View
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolder
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


    /**
     * Returns editor's name - a string that identifies the editor among others
     * (e.g.: "GUI Designer" for graphical editing and "Text" for textual representation of a GUI form editor).
     */
    fun getName(): String

    /**
     * Returns editor's internal state.
     */

    fun getState(): FileEditorState {
        return FileEditorState.INSTANCE
    }

    /**
     * Applies a given state to the editor.
     */
    fun setState(state: FileEditorState)

    /**
     * In some cases, it's desirable to set state exactly as requested (e.g. on tab splitting), while in other cases different behaviour is
     * preferred, e.g. bringing caret into view on text editor opening.
     * This method passes an additional flag to [FileEditor] to indicate the desired way to set state.
     */
    fun setState(state: FileEditorState, exactState: Boolean) {
        setState(state)
    }


    /**
     * An editor is valid if its contents still exist.
     * For example, an editor displaying the contents of some file stops being valid if the file is deleted.
     * An editor can also become invalid after being disposed of.
     */
    fun isValid(): Boolean

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