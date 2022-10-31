package com.dingyi.myluaapp.openapi.components


/**
 * We have a framework for persisting component states (see [State] [Storage]). It allows to specify which file
 * should hold persisting data. There is a number of standard file system anchors like 'workspace file', 'project config dir' which
 * can be used for defining a storage file's path. Hence, IJ provides special support for such anchors in the form of macros,
 * i.e. special markers that are mapped to the current file system environment at runtime.
 *
 *
 * This class holds those markers and utility method for working with them.
 */
object StoragePathMacros {
    /**
     * [Workspace][com.intellij.openapi.project.Project.getWorkspaceFile] file key.
     * `'Workspace file'` holds settings that are local to a particular environment and should not be shared with another
     * team members.
     */
    val WORKSPACE_FILE = "\$WORKSPACE_FILE$"

    /**
     * Storage file for cache-like data. Stored outside of project directory (if project level component)
     * and outside of application configuration directory (if application level component).
     */
    const val CACHE_FILE = "\$CACHE_FILE$"

    /**
     * Same as [.WORKSPACE_FILE], but stored per-product. Applicable only for project-level.
     */
    const val PRODUCT_WORKSPACE_FILE = "\$PRODUCT_WORKSPACE_FILE$"


    const val MODULE_FILE = "\$MODULE_FILE$"

    /**
     * Application level non-roamable storage.
     */
    const val NON_ROAMABLE_FILE = "other.xml"
}
