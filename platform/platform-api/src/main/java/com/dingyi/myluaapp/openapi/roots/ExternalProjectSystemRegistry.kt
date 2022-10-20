// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.roots

import com.dingyi.myluaapp.openapi.application.ApplicationManager.getApplication
import com.dingyi.myluaapp.openapi.module.Module

interface ExternalProjectSystemRegistry {
    fun getSourceById(id: String): ProjectModelExternalSource
    fun getExternalSource(module: Module): ProjectModelExternalSource?

    companion object {
        val instance: ExternalProjectSystemRegistry?
            get() = getApplication().get(
                ExternalProjectSystemRegistry::class.java
            )

        /**
         * These fields are temporary added to API until we have proper extension points for different external systems.
         */
        const val MAVEN_EXTERNAL_SOURCE_ID = "Maven"
        const val EXTERNAL_SYSTEM_ID_KEY = "external.system.id"
        const val IS_MAVEN_MODULE_KEY =
            "org.jetbrains.idea.maven.project.MavenProjectsManager.isMavenModule"
    }
}