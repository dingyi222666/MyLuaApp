package com.dingyi.myluaapp.configurationStore


import com.dingyi.myluaapp.openapi.components.StateStorage
import com.dingyi.myluaapp.openapi.components.StateStorageOperation
import com.dingyi.myluaapp.openapi.components.Storage
import com.dingyi.myluaapp.openapi.components.stateStore
import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import kotlinx.coroutines.runBlocking
import java.nio.file.Path

interface StateStorageManager {

    val componentManager: ServiceRegistry?

    fun getStateStorage(storageSpec: Storage): StateStorage

    fun addStreamProvider(provider: StreamProvider, first: Boolean = false)

    fun removeStreamProvider(clazz: Class<out StreamProvider>)

    fun getOldStorage(
        component: Any,
        componentName: String,
        operation: StateStorageOperation
    ): StateStorage?

    fun expandMacro(collapsedPath: String): Path

}



interface StorageCreator {
    val key: String

    fun create(storageManager: StateStorageManager): StateStorage
}

/**
 * Low-level method to save component manager state store. Use it with care and only if you understand what are you doing.
 * Intended for Java clients only. Do not use in Kotlin.
 */
@JvmOverloads
fun saveComponentManager(
    componentManager: ServiceRegistry,
    forceSavingAllSettings: Boolean = false
) {
    runBlocking {
        componentManager.stateStore.save(forceSavingAllSettings = forceSavingAllSettings)
    }
}
