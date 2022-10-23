/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.openapi.roots

import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName.Companion.create
import com.dingyi.myluaapp.openapi.module.Module
import org.jetbrains.annotations.Contract

/**
 * Implement this extension to change how dependencies of modules are processed by the IDE. You may also need to register implementation of
 * [org.jetbrains.jps.model.java.impl.JpsJavaDependenciesEnumerationHandler] extension to ensure that the same logic applies inside
 * JPS build process.
 */
abstract class OrderEnumerationHandler {
    abstract class Factory {
        @Contract(pure = true)
        abstract fun isApplicable(module: Module): Boolean
        @Contract(pure = true)
        abstract fun createHandler(module: Module): OrderEnumerationHandler
    }

    enum class AddDependencyType {
        ADD, DO_NOT_ADD, DEFAULT
    }

    fun shouldAddDependency(
        orderEntry: OrderEntry,
        settings: OrderEnumeratorSettings
    ): AddDependencyType {
        return AddDependencyType.DEFAULT
    }

    fun shouldAddRuntimeDependenciesToTestCompilationClasspath(): Boolean {
        return false
    }

    fun shouldIncludeTestsFromDependentModulesToTestClasspath(): Boolean {
        return true
    }

    fun shouldProcessDependenciesRecursively(): Boolean {
        return true
    }

    /**
     * Returns `true` if resource files located under roots of types [org.jetbrains.jps.model.java.JavaModuleSourceRootTypes.SOURCES]
     * are copied to the module output.
     */
    fun areResourceFilesFromSourceRootsCopiedToOutput(): Boolean {
        return true
    }

    /**
     * Override this method to contribute custom roots for a library or SDK instead of the configured ones.
     * @return `false` if no customization was performed, and therefore the default roots should be added.
     */
    fun addCustomRootsForLibraryOrSdk(
        forOrderEntry: LibraryOrSdkOrderEntry,
        type: OrderRootType,
        urls: Collection<String>
    ): Boolean {
        return addCustomRootsForLibrary(forOrderEntry, type, urls)
    }

    @Deprecated("override {@link #addCustomRootsForLibraryOrSdk(LibraryOrSdkOrderEntry, OrderRootType, Collection)} instead.",
        ReplaceWith("false")
    )
    fun addCustomRootsForLibrary(
        forOrderEntry: OrderEntry,
        type: OrderRootType,
        urls: Collection<String>
    ): Boolean {
        return false
    }

    fun addCustomModuleRoots(
        type: OrderRootType,
        rootModel: ModuleRootModel,
        result: Collection<String>,
        includeProduction: Boolean,
        includeTests: Boolean
    ): Boolean {
        return false
    }

    companion object {
        val EP_NAME = create<Factory>("com.dingyi.myluaapp.orderEnumerationHandlerFactory")
    }
}