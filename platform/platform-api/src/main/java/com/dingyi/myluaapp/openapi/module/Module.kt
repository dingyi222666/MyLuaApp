package com.dingyi.myluaapp.openapi.module


import com.dingyi.myluaapp.openapi.project.Project
import com.intellij.openapi.Disposable
import org.jetbrains.annotations.NonNls


/**
 * Represents a module in an IDEA project.
 *
 * @see ModuleManager.getModules
 */
interface Module : Disposable {


    /**
     * Returns the project to which this module belongs.
     *
     * @return the project instance.
     */
    fun getProject(): Project

    /**
     * Returns the name of this module.
     *
     * @return the module name.
     */
    fun getName(): String

    /**
     * Checks if the module instance has been disposed and unloaded.
     *
     * @return true if the module has been disposed, false otherwise
     */
    fun isDisposed(): Boolean


    fun isLoaded(): Boolean


    companion object {
        /**
         * The empty array of modules which can be reused to avoid unnecessary allocations.
         */
        val EMPTY_ARRAY = arrayOfNulls<Module>(0)

        @NonNls
        const val ELEMENT_TYPE = "type"
    }
}