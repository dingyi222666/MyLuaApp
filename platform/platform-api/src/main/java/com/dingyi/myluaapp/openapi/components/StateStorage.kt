package com.dingyi.myluaapp.openapi.components


interface StateStorage {
    val isUseVfsForWrite: Boolean
        get() = false

    /**
     * You can call this method only once.
     * If state exists and not archived - not-null result.
     * If doesn't exists or archived - null result.
     */
    fun <T : Any> getState(component: Any?, componentName: String, stateClass: Class<T>, mergeInto: T?, reload: Boolean): T?

    fun hasState(componentName: String, reloadData: Boolean): Boolean


}