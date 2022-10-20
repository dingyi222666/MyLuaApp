package com.dingyi.myluaapp.openapi.roots.libraries

import com.dingyi.myluaapp.openapi.application.ApplicationManager.getApplication
import com.dingyi.myluaapp.openapi.project.Project
import org.jetbrains.annotations.NonNls

abstract class LibraryTablesRegistrar {
    /**
     * Returns the table containing application-level libraries. These libraries are shown in 'Project Structure' | 'Platform Settings' | 'Global Libraries'
     * and may be added to dependencies of modules in any project.
     */
    abstract val libraryTable: LibraryTable

    /**
     * Returns the table containing project-level libraries for given `project`. These libraries are shown in 'Project Structure'
     * | 'Project Settings' | 'Libraries' and may be added to dependencies of the corresponding project's modules only.
     */
    abstract fun getLibraryTable(project: Project): LibraryTable

    /**
     * Returns the standard or a custom library table registered via [CustomLibraryTableDescription].
     */
    abstract fun getLibraryTableByLevel(@NonNls level: String?, project: Project): LibraryTable?

    /**
     * Returns a custom library table registered via [CustomLibraryTableDescription].
     */
    abstract fun getCustomLibraryTableByLevel(@NonNls level: String?): LibraryTable?
    abstract val customLibraryTables: List<LibraryTable?>

    companion object {
        @NonNls
        val PROJECT_LEVEL = "project"

        @NonNls
        val APPLICATION_LEVEL = "application"
        @JvmStatic
        val instance: LibraryTablesRegistrar
            get() = getApplication().get(
                LibraryTablesRegistrar::class.java
            )
    }
}