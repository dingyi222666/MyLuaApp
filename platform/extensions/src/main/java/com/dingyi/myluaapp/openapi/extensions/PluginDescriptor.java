// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions;

import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

public interface PluginDescriptor {
  @NotNull PluginId getPluginId();

  @Nullable ClassLoader getPluginClassLoader();

  @ApiStatus.Experimental
  default @NotNull ClassLoader getClassLoader() {
    ClassLoader classLoader = getPluginClassLoader();
    return classLoader == null ? getClass().getClassLoader() : classLoader;
  }

    /**
   * @deprecated Use {@link #getPluginPath()}
   */
  @Deprecated
  default File getPath() {
    Path path = getPluginPath();
    return path == null ? null : path.toFile();
  }

  Path getPluginPath();

  @Nullable @Nls String getDescription();

  @Nullable String getChangeNotes();

  @NlsSafe String getName();

  @Nullable String getProductCode();

  @Nullable Date getReleaseDate();

  int getReleaseVersion();

  boolean isLicenseOptional();

  /**
   * @deprecated Do not use.
   */
  @Deprecated
  PluginId @NotNull [] getOptionalDependentPluginIds();

  @Nullable @NlsSafe String getVendor();

  @NlsSafe String getVersion();

  @Nullable String getResourceBundleBaseName();

  @Nullable @NlsSafe String getCategory();

  @Nullable String getVendorEmail();

  @Nullable String getVendorUrl();

  @Nullable String getUrl();


  @Nullable @NlsSafe String getSinceBuild();

  @Nullable @NlsSafe String getUntilBuild();


  /**
   * If true, this plugin requires restart even if it otherwise fulfills the requirements of dynamic plugins.
   */
  default boolean isRequireRestart() { return false; }

  boolean isEnabled();

  void setEnabled(boolean enabled);
}