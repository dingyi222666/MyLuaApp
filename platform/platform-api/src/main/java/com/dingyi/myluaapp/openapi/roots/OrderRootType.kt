// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.roots

import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName.Companion.create
import java.util.Collections

/**
 * Root types that can be queried from OrderEntry.
 *
 * @author dsl
 * @see OrderEntry
 */
open class OrderRootType protected constructor(private val myName: String) {
    /**
     * A temporary solution to exclude DOCUMENTATION from getAllTypes() and handle it only in special
     * cases if supported by LibraryType.
     */
    class DocumentationRootType : OrderRootType("DOCUMENTATION") {
        override fun skipWriteIfEmpty(): Boolean {
            return true
        }
    }

    fun name(): String {
        return myName
    }

    /**
     * Whether this root type should be skipped when writing a Library if the root type doesn't contain
     * any roots.
     *
     * @return true if empty root type should be skipped, false otherwise.
     */
    open fun skipWriteIfEmpty(): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun toString(): String {
        return "Root " + name()
    }

    companion object {
        private var ourExtensionsLoaded = false
        val EP_NAME = create<OrderRootType>("com.intellij.orderRootType")

        @JvmField
        var ourPersistentOrderRootTypes = arrayOf<PersistentOrderRootType>()

        /**
         * Classpath without output directories for modules. Includes:
         *
         *  * classes roots for libraries and jdk
         *  * recursively for module dependencies: only exported items
         *
         */
        @JvmField
        val CLASSES: OrderRootType =
            PersistentOrderRootType("CLASSES", "classPath", null, "classPathEntry")

        /**
         * Sources. Includes:
         *
         *  * production and test source roots for modules
         *  * source roots for libraries and jdk
         *  * recursively for module dependencies: only exported items
         *
         */
        @JvmField
        val SOURCES: OrderRootType =
            PersistentOrderRootType("SOURCES", "sourcePath", null, "sourcePathEntry")

        /**
         * Generic documentation order root type
         */
        val DOCUMENTATION: OrderRootType = DocumentationRootType()

        @get:Synchronized
        val allTypes: Array<out OrderRootType>
            get() = allPersistentTypes
        val allPersistentTypes: Array<PersistentOrderRootType>
            get() {
                if (!ourExtensionsLoaded) {
                    ourExtensionsLoaded = true
                    EP_NAME.extensionList
                }
                return ourPersistentOrderRootTypes
            }
        val sortedRootTypes: List<PersistentOrderRootType>
            get() {
                var allTypes: List<PersistentOrderRootType> = ArrayList()

                allTypes =
                    allTypes
                        .toMutableList()
                        .apply { addAll(allPersistentTypes) }
                        .sortedWith { o1: PersistentOrderRootType, o2: PersistentOrderRootType ->
                            o1.name().compareTo(o2.name(), ignoreCase = true)
                        }
                return allTypes
            }

        protected fun <T> getOrderRootType(orderRootTypeClass: Class<out T>): T? {
            val rootTypes = EP_NAME.extensionList
            for (rootType in rootTypes) {
                if (orderRootTypeClass.isInstance(rootType)) {
                    return rootType as T
                }
            }
            assert(false) { "Root type $orderRootTypeClass not found. All roots: $rootTypes" }
            return null
        }
    }
}