package com.dingyi.myluaapp.openapi.module

import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.service.get
import com.intellij.util.graph.Graph
import java.io.File
import java.io.IOException
import java.nio.file.Path


/**
 * Provides services for working with the modules of a project.
 */

abstract class ModuleManager {
    /**
     * Creates a module of the specified type at the specified path and adds it to the project
     * to which the module manager is related.
     *
     * @param filePath     path to an *.iml file where module configuration will be saved; name of the module will be equal to the file name without extension.
     * @param moduleTypeId the ID of the module type to create.
     * @return the module instance.
     */
    abstract fun newModule(
        filePath: String,
        moduleTypeId: String
    ): Module


    fun newModule(file: Path, moduleTypeId: String): Module {
        return newModule(file.toString().replace(File.separatorChar, '/'), moduleTypeId)
    }

    /**
     * Creates a non-persistent module of the specified type and adds it to the project
     * to which the module manager is related. [.commit] must be called to
     * bring the changes in effect.
     *
     * In contrast with modules created by [.newModule],
     * non-persistent modules aren't stored on a filesystem and aren't being written
     * in a project XML file. When IDE closes, all non-persistent modules vanishes out.
     */

    fun newNonPersistentModule(moduleName: String, id: String): Module {
        throw UnsupportedOperationException()
    }


    abstract fun loadModule(filePath: String): Module

    /**
     * Loads a module from an .iml file with the specified path and adds it to the project.
     *
     * @param file the path to load the module from.
     * @return the module instance.
     * @throws IOException                 if an I/O error occurred when loading the module file.
     * @throws ModuleWithNameAlreadyExists if a module with such a name already exists in the project.
     */

    @Throws(IOException::class)
    abstract fun loadModule(file: Path): Module

    /**
     * Disposes of the specified module and removes it from the project.
     *
     * @param module the module to remove.
     */
    abstract fun disposeModule(module: Module)

    /**
     * Returns the list of all modules in the project.
     *
     * @return the array of modules.
     */
    abstract fun getModules(): Array<Module>

    /**
     * Returns the project module with the specified name.
     *
     * @param name the name of the module to find.
     * @return the module instance, or null if no module with such name exists.
     */

    abstract fun findModuleByName(name: String): Module

    /**
     * Returns the list of modules sorted by dependency (the modules which do not depend
     * on anything are in the beginning of the list, a module which depends on another module
     * follows it in the list).
     *
     * @return the sorted array of modules.
     */
    abstract fun getSortedModules(): Array<Module>
    /*

        */
    /**
     * Returns the module comparator which can be used for sorting modules by dependency
     * (the modules which do not depend on anything are in the beginning of the list,
     * a module which depends on another module follows it in the list).
     *
     * @return the module comparator instance.
     *//*


    abstract fun moduleDependencyComparator(): Comparator<Module?>?

    */
    /**
     * Returns the list of modules which directly depend on the specified module.
     *
     * @param module the module for which the list of dependent modules is requested.
     * @return list of *modules that depend on* given module.
     * @see ModuleUtilCore.getAllDependentModules
     *//*

    abstract fun getModuleDependentModules(module: Module): List<Module>

    */
    /**
     * Checks if one of the specified modules directly depends on the other module.
     *
     * @param module   the module to check the dependency for.
     * @param onModule the module on which `module` may depend.
     * @return true if `module` directly depends on `onModule`, false otherwise.
     *//*

    abstract fun isModuleDependent(module: Module, onModule: Module): Boolean

    */
    /**
     * Returns the graph of dependencies between modules in the project.
     *
     * @return the module dependency graph.
     *//*

    abstract fun moduleGraph(): Graph<Module>

    */
    /**
     * Returns the graph of dependencies between modules in the project.
     *
     * @param includeTests whether test-only dependencies should be included
     * @return the module dependency graph.
     *//*

    abstract fun moduleGraph(includeTests: Boolean): Graph<Module>
*/


    /**
     * Returns the path to the group to which the specified module belongs, as an array of group names starting from the project root.
     *
     *
     * **Use [ModuleGrouper.getGroupPath] instead.** Explicit module groups will be replaced
     * by automatic module grouping accordingly to qualified names of modules, see [IDEA-166061](https://youtrack.jetbrains.com/issue/IDEA-166061) for details.
     *
     * @param module the module for which the path is requested.
     * @return the path to the group for the module, or null if the module does not belong to any group.
     */
    abstract fun getModuleGroupPath(module: Module): Array<String>
    abstract fun hasModuleGroups(): Boolean


    companion object {
        /**
         * Returns the module manager instance for the current project.
         *
         * @param project the project for which the module manager is requested.
         * @return the module manager instance.
         */
        fun getInstance(project: Project): ModuleManager {
            return project.get()//ModuleManager::class.java)
        }
    }
}
