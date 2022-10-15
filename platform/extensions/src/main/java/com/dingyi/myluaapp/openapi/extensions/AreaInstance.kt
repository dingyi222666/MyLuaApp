// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.extensions

import org.jetbrains.annotations.ApiStatus

/**
 * An element of the container system with which extensions may be associated. It's either [Application][com.intellij.openapi.application.Application],
 * [Project][com.intellij.openapi.project.Project] or [Module][com.intellij.openapi.module.Module].
 */
interface AreaInstance {
    @get:ApiStatus.Internal
    val extensionArea: ExtensionsArea
}