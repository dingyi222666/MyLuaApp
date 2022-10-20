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
package com.dingyi.myluaapp.openapi.roots.libraries

import com.dingyi.myluaapp.openapi.roots.libraries.LibraryKindRegistry.Companion.getInstance
import org.jetbrains.annotations.NonNls

class LibraryKind(@param:NonNls val kindId: String) {

    /**
     * @param kindId must be unique among all [com.intellij.openapi.roots.libraries.LibraryType] and [com.intellij.openapi.roots.libraries.LibraryPresentationProvider] implementations
     */
    init {
        val kind = ourAllKinds[kindId]
        /*  if (kind != null && !(kind instanceof TemporaryLibraryKind)) {
      throw new IllegalArgumentException("Kind " + kindId + " is not unique");
       }*/
        ourAllKinds[kindId] = this
    }

    override fun toString(): String {
        return "LibraryKind:$kindId"
    }

    companion object {
        private val ourAllKinds: MutableMap<String?, LibraryKind> = HashMap()

        /**
         * @param kindId must be unique among all [LibraryType] and [LibraryPresentationProvider] implementations
         * @return new [LibraryKind] instance
         */
        fun create(@NonNls kindId: String): LibraryKind {
            return LibraryKind(kindId)
        }

        @Deprecated(
            """it's better to store instance of {@code LibraryKind} instead of looking it by ID; if you really need to find an instance by
    its ID, use {@link LibraryKindRegistry#findKindById(String)}""", ReplaceWith(
                "getInstance().findKindById(kindId)",
                "com.dingyi.myluaapp.openapi.roots.libraries.LibraryKindRegistry.Companion.getInstance"
            )
        )
        fun findById(kindId: String?): LibraryKind? {
            return getInstance().findKindById(kindId)
        }

        fun findByIdInternal(kindId: String): LibraryKind? {
            return ourAllKinds[kindId]
        }

        fun unregisterKind(kind: LibraryKind) {
            ourAllKinds.remove(kind.kindId)
        }

        fun registerKind(kind: LibraryKind) {
            ourAllKinds[kind.kindId] = kind
        }
    }
}