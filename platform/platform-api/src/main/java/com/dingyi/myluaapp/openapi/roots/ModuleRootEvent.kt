// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.roots

import com.dingyi.myluaapp.openapi.project.Project
import java.util.EventObject

abstract class ModuleRootEvent protected constructor(project: Project) : EventObject(project) {
    abstract val isCausedByFileTypesChange: Boolean

    /**
     * If you migrate [ModuleRootListener] to [com.intellij.workspaceModel.ide.WorkspaceModelChangeListener],
     * you should still keep [ModuleRootListener] implementation in the following cases:
     *
     *  *  your code needs to know about changes in [SyntheticLibrary], [AdditionalLibraryRootsProvider] or {DirectoryIndexExcludePolicy}.
     *  *  your code needs to be notified about explicit calls of [ProjectRootManagerEx.makeRootsChange]
     *  *  your code needs to know about creation/deletion of folders/files what are associate with [ContentEntry]. See also [com.intellij.util.indexing.EntityIndexingServiceEx.createWorkspaceEntitiesRootsChangedInfo]
     *
     * <br></br>
     * These APIs are going to be deprecated or made internal. But since there are still usages in IJ code and in plugins, you should make sure
     * your code works in these cases as well.
     * <br></br>
     * If it's your case, add the following check to your [ModuleRootListener]:
     * <pre>
     * void rootsChanged(@NotNull ModuleRootEvent event) {
     * if(event.isCausedByWorkspaceModelChangesOnly()) return;
     * }
    </pre> *
     * This way it will only handle the legacy events, while new more granular Workspace events will be handled by your [com.intellij.workspaceModel.ide.WorkspaceModelChangeListener].
     */
    abstract val isCausedByWorkspaceModelChangesOnly: Boolean
    val project: Project
        get() = getSource() as Project
}