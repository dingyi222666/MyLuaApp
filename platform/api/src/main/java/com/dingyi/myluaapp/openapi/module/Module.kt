package com.dingyi.myluaapp.openapi.module


import com.dingyi.myluaapp.openapi.Disposable
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import org.jetbrains.annotations.NonNls
import java.io.File
import java.nio.file.Path


/**
 * Represents a module in an IDEA project.
 *
 * @see ModuleManager.getModules
 */
interface Module : ServiceRegistry, Disposable {


    /**
     * Returns the project to which this module belongs.
     *
     * @return the project instance.
     */
    val project: Project

    /**
     * Returns the name of this module.
     *
     * @return the module name.
     */
    val name: String

    /**
     * Checks if the module instance has been disposed and unloaded.
     *
     * @return true if the module has been disposed, false otherwise
     */
    val isDisposed: Boolean
    val isLoaded: Boolean


    companion object {
        /**
         * The empty array of modules which can be reused to avoid unnecessary allocations.
         */
        val EMPTY_ARRAY = arrayOfNulls<Module>(0)

        @NonNls
        const val ELEMENT_TYPE = "type"
    }
}