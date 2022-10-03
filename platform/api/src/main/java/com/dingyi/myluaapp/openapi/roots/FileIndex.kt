package com.dingyi.myluaapp.openapi.roots

import org.apache.commons.vfs2.FileObject


/**
 * Provides information about files contained in a project or module.
 * In this interface and its inheritors, methods checking specific file status ("isX", "getX") should be used from a read action.
 * Iteration methods ("iterateX") may be called outside of a read action (since iteration can take a long time),
 * but they should be prepared to project model being changed in the middle of the iteration.
 *
 * @see ProjectRootManager.getFileIndex
 * @see ModuleRootManager.getFileIndex
 */
interface FileIndex {
    /**
     * Returns the root file or directory for the content root, if it is valid.
     *
     * @return the content root file or directory, or null if content entry is invalid.
     */

    fun getFile(): FileObject?

    /**
     * Returns the URL of content root.
     * To validate returned roots, use
     * `[com.intellij.openapi.vfs.VirtualFileManager.findFileByUrl]`
     *
     * @return URL of content root, that should never be null.
     */
    fun getUrl(): String


    /**
     * Returns the list of files and directories for valid source roots under this content root.
     *
     * @return array of all valid source roots.
     */
    fun getSourceFolderFiles(): Array<FileObject>

    /**
     * Adds a source or test source root under the content root. This method may be called only on an instance obtained from [ModifiableRootModel].
     *
     * @param file         the file or directory to add as a source root.
     * @param isTestSource true if the file or directory is added as a test source root.
     * @return the object representing the added root.
     */

    fun addSourceFolder(
         file: FileObject,
        //isTestSource: Boolean
    ): Boolean


    /**
     * Removes a source or test source root from this content root. This method may be called only on an instance obtained from [ModifiableRootModel].
     *
     * @param sourceFolder the source root to remove (must belong to this content root).
     */
    fun removeSourceFolder( sourceFolder: FileObject)

    /**
     * Removes all source roots. This method may be called only on an instance obtained from [ModifiableRootModel].
     */
    fun clearSourceFolders()


}

