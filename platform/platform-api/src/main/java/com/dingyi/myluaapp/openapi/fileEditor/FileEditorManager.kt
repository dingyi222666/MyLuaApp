package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.editor.Caret
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Key


/**
 * @see FileEditorManagerListener
 */
abstract class FileEditorManager {

    abstract fun getComposite( file: VirtualFile): FileEditorComposite?

    /**
     * @param file file to open. File should be valid.
     * Must be called from [EDT](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html).
     * @return array of opened editors
     */
    abstract fun openFile(
         file: VirtualFile,
        focusEditor: Boolean
    ):  Array<FileEditor>

    /**
     * Opens a file.
     * Must be called from [EDT](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html).
     *
     * @param file        file to open
     * @param focusEditor `true` if need to focus
     * @return array of opened editors
     */
    fun openFile(
         file: VirtualFile,
        focusEditor: Boolean,
        searchForOpen: Boolean
    ):  Array<FileEditor> {
        throw UnsupportedOperationException("Not implemented")
    }

    /**
     * Closes all the editors opened for a given file.
     * Must be called from [EDT](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html).
     *
     * @param file file to be closed.
     */
    abstract fun closeFile( file: VirtualFile)

    /**
     * Works as [.openFile] but forces opening of text editor (see [TextEditor]).
     * If several text editors are opened, including the default one, default text editor is focused (if requested) and returned.
     * Must be called from [EDT](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html).
     *
     * @return opened text editor. The method returns `null` in case if text editor wasn't opened.
     */

    abstract fun openTextEditor(
        descriptor: OpenFileDescriptor,
        focusEditor: Boolean
    ): Editor

    @Deprecated("use {@link #openTextEditor(OpenFileDescriptor, boolean)}",
        ReplaceWith("openTextEditor(descriptor, focusEditor)")
    )
    fun navigateToTextEditor(descriptor: OpenFileDescriptor, focusEditor: Boolean) {
        openTextEditor(descriptor, focusEditor)
    }

    /**
     * @return currently selected text editor. The method returns `null` in case
     * there is no selected editor at all or selected editor is not a text one.
     * Must be called from [EDT](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html).
     */

    abstract val selectedTextEditor: Editor



    /**
     * @return `true` if `file` is opened, `false` otherwise
     */
    abstract fun isFileOpen( file: VirtualFile): Boolean


    /**
     * @return all opened files. Order of files in the array corresponds to the order of editor tabs.
     */
    abstract val openFiles: Array<Any>



    fun hasOpenFiles(): Boolean {
        return openFiles.isNotEmpty()
    }

    /**
     * @return files currently selected. The method returns an empty array if there are no selected files.
     * If more than one file is selected (split), the file with the most recent focused editor is returned first.
     */
    abstract val selectedFiles:  Array<VirtualFile>

    /**
     * @return editors currently selected. The method returns an empty array if no editors are open.
     */
    abstract val selectedEditors:  Array<FileEditor>


    /**
     * @return currently selected file editor or `null` if there is no selected editor at all.
     */

    val selectedEditor: FileEditor?
        get() {
            val files = selectedFiles
            return if (files.isEmpty()) null else getSelectedEditor(files[0])
        }

    /**
     * @return editor which is currently selected for a given file.
     * The method returns `null` if `file` is not opened.
     */

    abstract fun getSelectedEditor(file: VirtualFile): FileEditor?

    /**
     * @return current editors for the specified `file`
     */
    abstract fun getEditors( file: VirtualFile):  Array<FileEditor>

    /**
     * @return all editors for the specified `file`
     */
    abstract fun getAllEditors( file: VirtualFile):  Array<FileEditor>

    /**
     * @return all open editors
     */
    abstract val allEditors: Array<FileEditor>

    /**
     * Adds the specified component above the editor and paints a separator line below it.
     * If a separator line is not needed, set the client property to `true`:
     * <pre>    component.putClientProperty(SEPARATOR_DISABLED, true);    </pre>
     * Otherwise, a separator line will be painted by a
     * [TEARLINE_COLOR][com.intellij.openapi.editor.colors.EditorColors.TEARLINE_COLOR] if it is set.
     *
     *
     * This method allows adding several components above the editor.
     * To change the order of components, the specified component may implement the
     * [Weighted][com.intellij.openapi.util.Weighted] interface.
     */
     //abstract fun addTopComponent(@NotNull editor: FileEditor?, @NotNull component: JComponent?)
    //abstract fun removeTopComponent(@NotNull editor: FileEditor?, @NotNull component: JComponent?)

    /**
     * Adds the specified component below the editor and paints a separator line above it.
     * If a separator line is not needed, set the client property to `true`:
     * <pre>    component.putClientProperty(SEPARATOR_DISABLED, true);    </pre>
     * Otherwise, a separator line will be painted by a
     * [TEARLINE_COLOR][com.intellij.openapi.editor.colors.EditorColors.TEARLINE_COLOR] if it is set.
     *
     *
     * This method allows adding several components below the editor.
     * To change the order of components, the specified component may implement the
     * [Weighted][com.intellij.openapi.util.Weighted] interface.
     */
    //abstract fun addBottomComponent(@NotNull editor: FileEditor?, @NotNull component: JComponent?)
    //abstract fun removeBottomComponent(
       // @NotNull editor: FileEditor?,
       // @NotNull component: JComponent?
    //)



    /**
     * Must be called from [EDT](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html).
     *
     * @return opened file editors
     */

    fun openEditor(
         descriptor: OpenFileDescriptor,
        focusEditor: Boolean
    ): List<FileEditor> {
        return openFileEditor(descriptor, focusEditor)
    }


    abstract fun openFileEditor(
         descriptor: FileEditorNavigatable,
        focusEditor: Boolean
    ): List<FileEditor>

    /**
     * @return the project which the file editor manager is associated with.
     */

    abstract val project: Project?

    abstract fun registerExtraEditorDataProvider(
        provider: EditorDataProvider?,
        parentDisposable: Disposable
    )

    /**
     * Returns data associated with given editor/caret context. Data providers are registered via
     * [.registerExtraEditorDataProvider] method.
     */

    abstract fun getData(
         dataId: String,
      editor: Editor,
       caret: Caret
    ): Any?

    /**
     * Selects a specified file editor tab for the specified editor.
     *
     * @param file                 a file to switch the file editor tab for. The function does nothing if the file is not currently open in the editor.
     * @param fileEditorProviderId the ID of the file editor to open; matches the return value of [FileEditorProvider.getEditorTypeId]
     */
    abstract fun setSelectedEditor(
        file: VirtualFile,
        fileEditorProviderId: String
    )

    /**
     * [FileEditorManager] supports asynchronous opening of text editors, i.e. when one of `openFile*` methods returns, returned
     * editor might not be fully initialized yet. This method allows delaying (if needed) execution of a given runnable until the editor is
     * fully loaded.
     */
    abstract fun runWhenLoaded( editor: Editor,  runnable: Runnable)

    /**
     * Refreshes the text, colors and icon of the editor tabs representing the specified file.
     *
     * @param file refreshed file
     */
    fun updateFilePresentation( file: VirtualFile) {}

    /**
     * Updates the file color of the editor tabs representing the specified file.
     *
     * @param file refreshed file
     */
    fun updateFileColor( file: VirtualFile) {}

    companion object {
        val USE_CURRENT_WINDOW: Key<Boolean> = Key.create("OpenFile.searchForOpen")
        fun getInstance( project: Project): FileEditorManager {
            return project.get(FileEditorManager::class.java)
        }

    }
}