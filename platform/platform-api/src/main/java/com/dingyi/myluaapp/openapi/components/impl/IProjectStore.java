package com.dingyi.myluaapp.openapi.components.impl;



import com.dingyi.myluaapp.openapi.project.Project;
import com.dingyi.myluaapp.openapi.vfs.VirtualFile;
import com.intellij.openapi.util.Key;

import org.jetbrains.annotations.*;

import java.nio.file.Path;

public interface IProjectStore extends IComponentStore {
    Key<Boolean> COMPONENT_STORE_LOADING_ENABLED = Key.create("COMPONENT_STORE_LOADING_ENABLED");

    @NotNull Path getProjectBasePath();

    @NotNull String getProjectName();

    /**
     * The path to project configuration file - `misc.xml` for directory-based and `*.ipr` for file-based.
     */
    @NotNull Path getProjectFilePath();

    @NotNull Path getWorkspacePath();

    void clearStorages();

    boolean isOptimiseTestLoadSpeed();

    void setOptimiseTestLoadSpeed(boolean optimiseTestLoadSpeed);

    boolean isProjectFile(@NotNull VirtualFile file);

    /*
     * The directory of project configuration files for directory-based project or null for file-based.
     */
    @Nullable Path getDirectoryStorePath();

    void setPath(@NotNull Path path, boolean isRefreshVfsNeeded, @Nullable Project template);

    @Nullable String getProjectWorkspaceId();
}