package com.dingyi.myluaapp.openapi.roots.libraries;


import com.dingyi.myluaapp.openapi.application.ApplicationManager;
import com.dingyi.myluaapp.openapi.project.Project;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public abstract class LibraryTablesRegistrar {
    @NonNls public static final String PROJECT_LEVEL = "project";
    @NonNls
    public static final String APPLICATION_LEVEL = "application";

    public static LibraryTablesRegistrar getInstance() {
        return ApplicationManager.INSTANCE.getApplication().get(LibraryTablesRegistrar.class);
    }

    /**
     * Returns the table containing application-level libraries. These libraries are shown in 'Project Structure' | 'Platform Settings' | 'Global Libraries'
     * and may be added to dependencies of modules in any project.
     */
    public abstract @NotNull LibraryTable getLibraryTable();

    /**
     * Returns the table containing project-level libraries for given {@code project}. These libraries are shown in 'Project Structure'
     * | 'Project Settings' | 'Libraries' and may be added to dependencies of the corresponding project's modules only.
     */
    public abstract @NotNull LibraryTable getLibraryTable(@NotNull Project project);

    /**
     * Returns the standard or a custom library table registered via {@link CustomLibraryTableDescription}.
     */
    public abstract @Nullable LibraryTable getLibraryTableByLevel(@NonNls String level, @NotNull Project project);

    /**
     * Returns a custom library table registered via {@link CustomLibraryTableDescription}.
     */
    public abstract @Nullable LibraryTable getCustomLibraryTableByLevel(@NonNls String level);

    public abstract @NotNull List<LibraryTable> getCustomLibraryTables();
}

