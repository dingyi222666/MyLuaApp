// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.roots.libraries

import com.dingyi.myluaapp.openapi.application.ApplicationManager

open class LibraryKindRegistry {
  companion object {
    @JvmStatic
    fun getInstance(): LibraryKindRegistry = ApplicationManager.getApplication().get(
        LibraryKindRegistry::class.java)
  }

  fun findKindById(id: String?): LibraryKind? = LibraryKind.findByIdInternal(id)
}