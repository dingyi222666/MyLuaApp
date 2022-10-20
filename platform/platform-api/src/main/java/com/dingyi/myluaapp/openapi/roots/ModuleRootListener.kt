// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.roots

import java.util.EventListener

/**
 * Project root changes.
 *
 *
 * Instead of events with {[ModuleRootEvent.isCausedByWorkspaceModelChangesOnly]} one may use
 * [com.intellij.workspaceModel.ide.WorkspaceModelChangeListener] and get  more fine-grained incremental events.
 *
 * @see ProjectTopics.PROJECT_ROOTS
 */
interface ModuleRootListener : EventListener {
    fun beforeRootsChange(event: ModuleRootEvent) {}
    fun rootsChanged(event: ModuleRootEvent) {}
}