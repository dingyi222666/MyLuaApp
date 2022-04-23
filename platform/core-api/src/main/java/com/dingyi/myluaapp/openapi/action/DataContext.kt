package com.dingyi.myluaapp.openapi.action

import org.jetbrains.kotlin.com.intellij.openapi.util.Key

/**
 * Allows an action to retrieve information about the context in which it was invoked.
 *
 * @see AnActionEvent#getDataContext()
 * @see com.intellij.openapi.actionSystem.PlatformDataKeys
 * @see DataKey
 * @see com.intellij.ide.DataManager
 * @see DataProvider
 */
interface DataContext {

    /**
     * Returns the object corresponding to the specified data identifier. Some of the supported
     * data identifiers are defined in the [com.intellij.openapi.actionSystem.PlatformDataKeys] class.
     *
     * **NOTE:** For implementation only, prefer [DataContext.getData] in client code.
     *
     * @param dataId the data identifier for which the value is requested.
     * @return the value, or null if no value is available in the current context for this identifier.
     */
    fun getData(dataId: String): Any?



    /**
     * Returns the value corresponding to the specified data key. Some of the supported
     * data identifiers are defined in the [com.intellij.openapi.actionSystem.PlatformDataKeys] class.
     *
     * @param key the data key for which the value is requested.
     * @return the value, or null if no value is available in the current context for this identifier.
     */

    fun <T> getData(key: DataKey<T>): T? {
        return getData(key) as T?
    }

    companion object {
        val EMPTY_CONTEXT = object : DataContext {
            override fun getData(dataId: String): Nothing? = null
            override fun <T> putData(key: DataKey<T>, t: T?) {}
        }
    }


    fun <T> putData(key: DataKey<T>, t: T?)


}