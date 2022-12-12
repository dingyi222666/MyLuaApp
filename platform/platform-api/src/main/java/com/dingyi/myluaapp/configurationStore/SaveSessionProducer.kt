package com.dingyi.myluaapp.configurationStore

import java.io.IOException

interface SaveSession : StorageManagerFileWriteRequestor {
    @Throws(IOException::class)
    fun save()
}

interface SaveSessionProducer : StorageManagerFileWriteRequestor {
    @Throws(IOException::class)
    fun setState(component: Any?, componentName: String, state: Any?)

    /**
     * return null if nothing to save
     */
    fun createSaveSession(): SaveSession?
}

/**
 * A marker interface for [FileUndoProvider] to not process this file change event.
 */
interface StorageManagerFileWriteRequestor