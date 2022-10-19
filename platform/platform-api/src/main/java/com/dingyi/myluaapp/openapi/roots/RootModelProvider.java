// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.roots;

import com.dingyi.myluaapp.openapi.module.Module;

import org.jetbrains.annotations.NotNull;


public interface RootModelProvider {
  Module @NotNull [] getModules();

  ModuleRootModel getRootModel(@NotNull Module module);
}
