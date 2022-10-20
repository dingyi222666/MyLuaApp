// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.roots.libraries

import com.dingyi.myluaapp.openapi.module.Module
import com.dingyi.myluaapp.openapi.module.ModuleManager
import com.dingyi.myluaapp.openapi.module.ModuleManager.Companion.getInstance
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.roots.LibraryOrderEntry
import com.dingyi.myluaapp.openapi.roots.ModuleRootManager
import com.dingyi.myluaapp.openapi.roots.OrderEntry
import com.dingyi.myluaapp.openapi.roots.OrderEnumerator.Companion.orderEntries
import com.dingyi.myluaapp.openapi.roots.OrderRootType
import com.dingyi.myluaapp.openapi.roots.ProjectRootManager
import com.dingyi.myluaapp.openapi.roots.libraries.LibraryTablesRegistrar.Companion.instance
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.util.Ref
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.text.StringTokenizer
import org.jetbrains.annotations.NonNls
import java.util.Arrays

object LibraryUtil {
    fun isClassAvailableInLibrary(library: Library, fqn: String): Boolean {
        return isClassAvailableInLibrary(library.getFiles(OrderRootType.CLASSES), fqn)
    }

    fun isClassAvailableInLibrary(files: Array<VirtualFile>, fqn: String): Boolean {
        return isClassAvailableInLibrary(listOf(*files), fqn)
    }

    fun isClassAvailableInLibrary(files: List<VirtualFile>, fqn: String): Boolean {
        for (file in files) {
            if (findInFile(file, StringTokenizer(fqn, "."))) return true
        }
        return false
    }

    fun findLibraryByClass(fqn: String, project: Project?): Library? {
        if (project != null) {
            val projectTable = instance.getLibraryTable(project)
            val library = findInTable(projectTable, fqn)
            if (library != null) {
                return library
            }
        }
        val table = instance.libraryTable
        return findInTable(table, fqn)
    }

    private fun findInFile(file: VirtualFile, tokenizer: StringTokenizer): Boolean {
        if (!tokenizer.hasMoreTokens()) return true
        @NonNls val name = StringBuilder(tokenizer.nextToken())
        if (!tokenizer.hasMoreTokens()) {
            name.append(".class")
        }
        val child = file.findChild(name.toString())
        return child != null && findInFile(child, tokenizer)
    }

    private fun findInTable(table: LibraryTable, fqn: String): Library? {
        for (library in table.libraries) {
            if (isClassAvailableInLibrary(library, fqn)) {
                return library
            }
        }
        return null
    }

    fun createLibrary(libraryTable: LibraryTable, @NonNls baseName: String): Library {
        var name = baseName
        var count = 2
        while (libraryTable.getLibraryByName(name) != null) {
            name = baseName + " (" + count++ + ")"
        }
        return libraryTable.createLibrary(name)
    }

    fun getLibraryRoots(project: Project): Array<VirtualFile> {
        return getLibraryRoots(getInstance(project).getModules(), true)
    }


    fun getLibraryRoots(
        modules: Array<Module>,
        includeSourceFiles: Boolean,
    ): Array<VirtualFile> {
        val roots: MutableSet<VirtualFile> = HashSet()
        for (module in modules) {
            val moduleRootManager = ModuleRootManager.getInstance(module)
            val orderEntries = moduleRootManager.orderEntries
            for (entry in orderEntries) {
                if (entry is LibraryOrderEntry) {
                    val library = entry.library
                    if (library != null) {
                        var files =
                            if (includeSourceFiles) library.getFiles(OrderRootType.SOURCES) else null
                        if (files == null || files.size == 0) {
                            files = library.getFiles(OrderRootType.CLASSES)
                        }
                        ContainerUtil.addAll(roots, *files)
                    }
                }
            /*else if (includeJdk && entry instanceof JdkOrderEntry) {
          JdkOrderEntry jdkEntry = (JdkOrderEntry)entry;
          VirtualFile[] files = includeSourceFiles ? jdkEntry.getRootFiles(OrderRootType.SOURCES) : null;
          if (files == null || files.length == 0) {
            files = jdkEntry.getRootFiles(OrderRootType.CLASSES);
          }
          ContainerUtil.addAll(roots, files);
        }*/
            }
        }
        return roots.toTypedArray()
    }

    fun findLibrary(module: Module, name: String): Library? {
        val result = Ref.create<Library>(null)
        orderEntries(module).forEachLibrary { library: Library? ->
            if (name == library!!.name) {
                result.set(library)
                return@forEachLibrary false
            }
            true
        }
        return result.get()
    }

    fun findLibraryEntry(file: VirtualFile, project: Project): OrderEntry? {
        val entries = ProjectRootManager.getInstance(project).fileIndex.getOrderEntriesForFile(file)
        for (entry in entries) {
            if (entry is LibraryOrderEntry) {
                return entry
            }
        }
        return null
    }
}