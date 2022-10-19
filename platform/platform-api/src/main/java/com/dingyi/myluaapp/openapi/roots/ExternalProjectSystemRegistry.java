// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.roots;

import com.dingyi.myluaapp.openapi.application.ApplicationManager;
import com.dingyi.myluaapp.openapi.module.Module;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface ExternalProjectSystemRegistry {
  static ExternalProjectSystemRegistry getInstance() {
    return ApplicationManager.INSTANCE.getApplication().get(ExternalProjectSystemRegistry.class);
  }

  @NotNull
  ProjectModelExternalSource getSourceById(@NotNull String id);

  @Nullable
  ProjectModelExternalSource getExternalSource(@NotNull Module module);

  /**
   * These fields are temporary added to API until we have proper extension points for different external systems.
   */
  String MAVEN_EXTERNAL_SOURCE_ID = "Maven";
  String EXTERNAL_SYSTEM_ID_KEY = "external.system.id";
  String IS_MAVEN_MODULE_KEY = "org.jetbrains.idea.maven.project.MavenProjectsManager.isMavenModule";
}
