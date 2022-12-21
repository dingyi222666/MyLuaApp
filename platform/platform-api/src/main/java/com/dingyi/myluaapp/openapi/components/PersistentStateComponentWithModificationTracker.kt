package com.dingyi.myluaapp.openapi.components

/**
 * To check that component stat is changed and must be saved, `getState` is called.
 * To reduce `getState` calls [com.dingyi.myluaapp.openapi.util.ModificationTracker] can be implemented.
 * But common interface can lead to confusion (if meaning of 'modification count' for serialization and for other clients is different).
 * So, you can use this interface to distinguish use cases.
 */
interface PersistentStateComponentWithModificationTracker<T> : PersistentStateComponent<T> {
    val stateModificationCount: Long
}
