/*
 * Copyright 2000-2017 JetBrains s.r.o.
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

import com.dingyi.myluaapp.openapi.module.Module
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.roots.libraries.Library
import com.dingyi.myluaapp.openapi.util.PathsList
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.util.Condition
import com.intellij.util.NotNullFunction
import com.intellij.util.Processor

/**
 *
 * Interface for convenient processing dependencies of a module or a project. Allows to process [OrderEntry]s
 * and collect classes and source roots.
 *
 *
 * Use [.orderEntries] or [ModuleRootModel.orderEntries] to process dependencies of a module
 * and use [.orderEntries] to process dependencies of all modules in a project.
 *
 *
 * Note that all configuration methods modify [OrderEnumerator] instance instead of creating a new one.
 */
abstract class OrderEnumerator {
    /**
     * Skip test dependencies
     *
     * @return this instance
     */
    abstract fun productionOnly(): OrderEnumerator

    /**
     * Skip runtime-only dependencies
     *
     * @return this instance
     */
    abstract fun compileOnly(): OrderEnumerator

    /**
     * Skip compile-only dependencies
     *
     * @return this instance
     */
    abstract fun runtimeOnly(): OrderEnumerator
    abstract fun withoutSdk(): OrderEnumerator
    abstract fun withoutLibraries(): OrderEnumerator
    abstract fun withoutDepModules(): OrderEnumerator

    /**
     * Skip root module's entries
     * @return this
     */
    abstract fun withoutModuleSourceEntries(): OrderEnumerator
    fun librariesOnly(): OrderEnumerator {
        return withoutSdk().withoutDepModules().withoutModuleSourceEntries()
    }

    fun sdkOnly(): OrderEnumerator {
        return withoutDepModules().withoutLibraries().withoutModuleSourceEntries()
    }

    val allLibrariesAndSdkClassesRoots: Array<VirtualFile>
        get() = withoutModuleSourceEntries().withoutDepModules().recursively().exportedOnly()
            .classes().usingCache().roots
    val allSourceRoots: Array<VirtualFile>
        get() = recursively().exportedOnly().sources().usingCache().roots

    /**
     * Recursively process modules on which the module depends. This flag is ignored for modules imported from Maven because for such modules
     * transitive dependencies are propagated to the root module during importing.
     *
     * @return this instance
     */
    abstract fun recursively(): OrderEnumerator

    /**
     * Skip not exported dependencies. If this method is called after [.recursively] direct non-exported dependencies won't be skipped
     *
     * @return this instance
     */
    abstract fun exportedOnly(): OrderEnumerator

    /**
     * Process only entries which satisfies the specified condition
     *
     * @param condition filtering condition
     * @return this instance
     */
    abstract fun satisfying(condition: Condition<in OrderEntry?>): OrderEnumerator

    /**
     * Use `provider.getRootModel()` to process module dependencies
     *
     * @param provider provider
     * @return this instance
     */
    abstract fun using(provider: RootModelProvider): OrderEnumerator

    /**
     * Determine if, given the current enumerator settings and handlers for a module, should the
     * enumerator recurse to further modules based on the given ModuleOrderEntry?
     *
     * @param entry the ModuleOrderEntry in question (m1 -> m2)
     * @param handlers custom handlers registered to the module
     * @return true if the enumerator would have recursively processed the given ModuleOrderEntry.
     */
    abstract fun shouldRecurse(
        entry: ModuleOrderEntry,
        handlers: List<OrderEnumerationHandler?>
    ): Boolean

    /**
     * @return [OrderRootsEnumerator] instance for processing classes roots
     */
    abstract fun classes(): OrderRootsEnumerator

    /**
     * @return [OrderRootsEnumerator] instance for processing source roots
     */
    abstract fun sources(): OrderRootsEnumerator

    /**
     * @param rootType root type
     * @return [OrderRootsEnumerator] instance for processing roots of the specified type
     */
    abstract fun roots(rootType: OrderRootType): OrderRootsEnumerator

    /**
     * @param rootTypeProvider custom root type provider
     * @return [OrderRootsEnumerator] instance for processing roots of the provided type
     */
    abstract fun roots(rootTypeProvider: NotNullFunction<in OrderEntry?, out OrderRootType?>): OrderRootsEnumerator

    /**
     * @return classes roots for all entries processed by this enumerator
     */
    val classesRoots: Array<VirtualFile>
        get() = classes().roots

    /**
     * @return source roots for all entries processed by this enumerator
     */
    val sourceRoots: Array<VirtualFile>
        get() = sources().roots

    /**
     * @return list containing classes roots for all entries processed by this enumerator
     */
    val pathsList: PathsList
        get() = classes().pathsList

    /**
     * @return list containing source roots for all entries processed by this enumerator
     */
    val sourcePathsList: PathsList
        get() = sources().pathsList

    /**
     * Runs `processor.process()` for each entry processed by this enumerator.
     *
     * @param processor processor
     */
    abstract fun forEach(processor: Processor<in OrderEntry?>)

    /**
     * Runs `processor.process()` for each library processed by this enumerator.
     *
     * @param processor processor
     */
    abstract fun forEachLibrary(processor: Processor<in Library?>)

    /**
     * Runs `processor.process()` for each module processed by this enumerator.
     *
     * @param processor processor
     */
    abstract fun forEachModule(processor: Processor<in Module?>)

    companion object {
        /**
         * Creates new enumerator instance to process dependencies of `module`
         *
         * @param module module
         * @return new enumerator instance
         */
        fun orderEntries(module: Module): OrderEnumerator {
            return ModuleRootManager.getInstance(module).orderEntries()
        }

        /**
         * Creates new enumerator instance to process dependencies of all modules in `project`. Only first level dependencies of
         * modules are processed so [.recursively] option is ignored and [.withoutDepModules] option is forced
         *
         * @param project project
         * @return new enumerator instance
         */
        fun orderEntries(project: Project): OrderEnumerator {
            return ProjectRootManager.getInstance(project).orderEntries()
        }
    }
}