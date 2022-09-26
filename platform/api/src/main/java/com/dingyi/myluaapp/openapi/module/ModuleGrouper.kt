package com.dingyi.myluaapp.openapi.module

import com.dingyi.myluaapp.openapi.project.Project
import com.intellij.openapi.util.NlsSafe


/**
 * Use this class to determine how modules show by organized in a tree. It supports the both ways of module grouping: the old one where
 * groups are specified explicitly and the new one where modules are grouped accordingly to their qualified names.
 */

abstract class ModuleGrouper {
    /**
     * Returns names of parent groups for a module
     */
    abstract fun getGroupPath(module: Module): List<String>

    /**
     * Returns names of parent groups for a module
     */
  /*  abstract fun getGroupPath(description: ModuleDescription): List<String>*/

    /**
     * Returns name which should be used for a module when it's shown under its group
     */
    @NlsSafe
    abstract fun getShortenedName(module: Module): String

    /**
     * Returns name which should be used for a module when it's shown under its ancestor group which qualified name is [parentGroupName].
     * If [parentGroupName] is `null` returns the full module name.
     */
    abstract fun getShortenedName(module: Module, parentGroupName: String?): String

    /**
     * Returns name which should be used for a module with name [name] when it's shown under its group
     */
    abstract fun getShortenedNameByFullModuleName(name: String): String

    /**
     * Returns name which should be used for a module with name [name] when it's shown under its ancestor group which qualified name is [parentGroupName].
     * If [parentGroupName] is `null` returns the full module name.
     */
    abstract fun getShortenedNameByFullModuleName(name: String, parentGroupName: String?): String

    abstract fun getGroupPathByModuleName(name: String): List<String>

    /**
     * If [module] itself can be considered as a group, returns its groups. Otherwise returns null.
     */
    abstract fun getModuleAsGroupPath(module: Module): List<String>?

  /**
     * If [description] itself can be considered as a group, returns its groups. Otherwise returns null.
     */
//    abstract fun getModuleAsGroupPath(description: ModuleDescription): List<String>?

    abstract fun getAllModules(): Array<Module>

    /**
     * Determines whether module group nodes containing single child should be joined with the child nodes. E.g. the modules `foo.bar.baz`
     * and `foo.bar.baz2` will form the following tree if [compactGroupNodes] is `false`
     * ```
     * foo
     *  bar
     *   baz
     *   baz2
     * ```
     * and the following tree if [compactGroupNodes] is `true`:
     * ```
     * foo.bar
     *  baz
     *  baz2
     * ```
     */
    abstract fun compactGroupNodes(): Boolean

    companion object {
        @JvmStatic
        @JvmOverloads
        fun instanceFor(project: Project, moduleModel: ModifiableModuleModel): ModuleGrouper {
            return ModuleManager.getInstance(project).getModuleGrouper(moduleModel)
        }
    }
}
