package com.dingyi.myluaapp.openapi.module

import com.dingyi.myluaapp.openapi.project.Project
import java.io.File
import java.nio.file.Path


/**
 * Represents the model for the list of modules in a project, or a temporary copy
 * of that model displayed in the configuration UI.
 *
 * @see ModuleManager.getModifiableModel
 */
interface ModifiableModuleModel {
    /**
     * Returns the list of all modules in the project. Same as [ModuleManager.getModules].
     *
     * @return the array of modules.
     */
    fun getModules(): Array<Module>


    fun newModule(filePath: String, moduleTypeId: String): Module

    /**
     * Creates a module of the specified type at the specified path and adds it to the project
     * to which the module manager is related. [.commit] must be called to
     * bring the changes in effect.
     *
     * @param file         path to an *.iml file where module configuration will be saved; name of the module will be equal to the file name without extension.
     * @param moduleTypeId the ID of the module type to create.
     * @return the module instance.
     */

    fun newModule(file: Path, moduleTypeId: String): Module {
        return newModule(
            file.toAbsolutePath().normalize().toString().replace(File.separatorChar, '/'),
            moduleTypeId
        )
    }





    /**
     * Loads a module from an .iml file with the specified path and adds it to the project.
     * [.commit] must be called to bring the changes in effect.
     *
     * @param filePath the path to load the module from.
     * @return the module instance.
     * @throws IOException                 if an I/O error occurred when loading the module file.
     * @throws ModuleWithNameAlreadyExists if a module with such a name already exists in the project.
     */


    fun loadModule(filePath: String): Module


    fun loadModule(file: Path): Module

    /**
     * Disposes of the specified module and removes it from the project. [.commit]
     * must be called to bring the changes in effect.
     *
     * @param module the module to remove.
     */
    fun disposeModule(module: Module)

    /**
     * Returns the project module with the specified name.
     *
     * @param name the name of the module to find.
     * @return the module instance, or null if no module with such name exists.
     */

    fun findModuleByName(name: String): Module

    /**
     * Disposes of all modules in the project.
     */
    fun dispose()

    /**
     * Checks if there are any uncommitted changes to the model.
     *
     * @return true if there are uncommitted changes, false otherwise
     */
    fun isChanged(): Boolean

    /**
     * Commits changes made in this model to the actual project structure.
     */
    fun commit()

    /**
     * Schedules the rename of a module to be performed when the model is committed.
     *
     * @param module  the module to rename.
     * @param newName the new name to rename the module to.
     * @throws ModuleWithNameAlreadyExists if a module with such a name already exists in the project.
     */

    fun renameModule(module: Module, newName: String)

    /**
     * Returns the project module which has been renamed to the specified name.
     *
     * @param newName the name of the renamed module to find.
     * @return the module instance, or null if no module has been renamed to such a name.
     */
    fun getModuleToBeRenamed(newName: String): Module?

    /**
     * Returns the name to which the specified module has been renamed.
     *
     * @param module the module for which the new name is requested.
     * @return the new name, or null if the module has not been renamed.
     */

    fun getNewName(module: Module): String?

    /**
     * @return the new name of `module` if it has been renamed or its old name it hasn't.
     */

    fun getActualName(module: Module?): String?
    fun getModuleGroupPath(module: Module): Array<String>
    fun hasModuleGroups(): Boolean
    fun setModuleGroupPath(
        module: Module, groupPath: Array<String>
    )


    fun getProject(): Project
}
