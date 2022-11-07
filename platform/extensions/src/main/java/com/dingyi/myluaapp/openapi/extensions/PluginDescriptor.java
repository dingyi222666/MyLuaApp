// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions;

import com.intellij.openapi.util.NlsSafe;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public interface PluginDescriptor {

    @NotNull
    PluginId getPluginId();

    @Nullable
    ClassLoader getPluginClassLoader();


    default @NotNull ClassLoader getClassLoader() {
        ClassLoader classLoader = getPluginClassLoader();
        return classLoader == null ? getClass().getClassLoader() : classLoader;
    }

    @NotNull
    String getAuthor();

    int getMinSdkVersion();

    /**
     * @deprecated Use {@link #getPluginPath()}
     */
    @Deprecated
    default File getPath() {
        Path path = getPluginPath();
        return path == null ? null : path.toFile();
    }

    Path getPluginPath();

    @Nullable
    @Nls
    String getDescription();


    @NotNull
    String getVersion();

    @NlsSafe String getName();


    boolean isEnabled();

    void setEnabled(boolean enabled);
}