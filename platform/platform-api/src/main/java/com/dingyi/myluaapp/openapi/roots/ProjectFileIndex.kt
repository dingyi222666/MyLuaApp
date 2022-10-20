// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.roots

import com.dingyi.myluaapp.openapi.module.Module
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile

/**
 * Provides information about files contained in a project. Should be used from a read action.
 *
 * @see ProjectRootManager.getFileIndex
 */
interface ProjectFileIndex : FileIndex {
    /**
     * Returns `true` if `file` is located under project content or library roots and not excluded or ignored
     */
    fun isInProject(file: VirtualFile): Boolean

    /**
     * Returns `true` if `file` is located under project content or library roots, regardless of whether it's marked as excluded or not,
     * and returns `false` if `file` is located outside or it or one of its parent directories is ignored.
     */
    fun isInProjectOrExcluded(file: VirtualFile): Boolean

    /**
     * Returns module to which content the specified file belongs or null if the file does not belong to content of any module.
     */
    fun getModuleForFile(file: VirtualFile): Module?

    /**
     * Returns module to which content the specified file belongs or null if the file does not belong to content of any module.
     *
     * @param honorExclusion if `false` the containing module will be returned even if the file is located under a folder marked as excluded
     */
    fun getModuleForFile(file: VirtualFile, honorExclusion: Boolean): Module?

    /**
     * Returns the order entries which contain the specified file (either in CLASSES or SOURCES).
     */
    fun getOrderEntriesForFile(file: VirtualFile): List<OrderEntry?>

    /**
     * Returns a classpath entry to which the specified file or directory belongs.
     *
     * @return the file for the classpath entry, or null if the file is not a compiled
     * class file or directory belonging to a library.
     */
    fun getClassRootForFile(file: VirtualFile): VirtualFile?

    /**
     * Returns the module source root or library source root to which the specified file or directory belongs.
     *
     * @return the file for the source root, or null if the file is not located under any of the source roots for the module.
     */
    fun getSourceRootForFile(file: VirtualFile): VirtualFile?

    /**
     * Returns the module content root to which the specified file or directory belongs or null if the file does not belong to content of any module.
     */
    fun getContentRootForFile(file: VirtualFile): VirtualFile?

    /**
     * Returns the module content root to which the specified file or directory belongs or null if the file does not belong to content of any module.
     *
     * @param honorExclusion if `false` the containing content root will be returned even if the file is located under a folder marked as excluded
     */
    fun getContentRootForFile(file: VirtualFile, honorExclusion: Boolean): VirtualFile?

    @Deprecated("use {@link com.intellij.openapi.roots.PackageIndex#getPackageNameByDirectory(VirtualFile)} from Java plugin instead.")
    fun getPackageNameByDirectory(dir: VirtualFile): String?

    /**
     * Returns true if `file` is a file which belongs to the classes (not sources) of some library which is included into dependencies
     * of some module.
     */
    @Deprecated(
        """name of this method may be misleading, actually it doesn't check that {@code file} has the 'class' extension. 
    Use {@link #isInLibraryClasses} with additional {@code !file.isDirectory()} check instead.   """
    )
    fun isLibraryClassFile(file: VirtualFile): Boolean

    /**
     * Returns true if `fileOrDir` is a file or directory from production/test sources of some module or sources of some library which is included into dependencies
     * of some module.
     */
    fun isInSource(fileOrDir: VirtualFile): Boolean

    /**
     * Returns true if `fileOrDir` belongs to classes of some library which is included into dependencies of some module.
     */
    fun isInLibraryClasses(fileOrDir: VirtualFile): Boolean

    /**
     * @return true if the file belongs to the classes or sources of a library added to dependencies of the project,
     * false otherwise
     */
    fun isInLibrary(fileOrDir: VirtualFile): Boolean

    /**
     * Returns true if `fileOrDir` is a file or directory from sources of some library which is included into dependencies
     * of some module.
     */
    fun isInLibrarySource(fileOrDir: VirtualFile): Boolean

    /**
     * Checks if the specified file or directory is located under project roots but the file itself or one of its parent directories is
     * either excluded from the project or ignored by [FileTypeRegistry.isFileIgnored]).
     *
     * @return true if `file` is excluded or ignored, false otherwise.
     */
    fun isExcluded(file: VirtualFile): Boolean
    fun getSourceFolder(fileOrDir: VirtualFile): SourceFolder? {
        return null
    }

    companion object {
        fun getInstance(project: Project): ProjectFileIndex {
            return project.get(ProjectFileIndex::class.java)
        }
    }
}