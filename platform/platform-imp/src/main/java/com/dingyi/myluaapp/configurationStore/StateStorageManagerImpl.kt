package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.application.IDEApplication
import com.dingyi.myluaapp.openapi.components.StateStorage
import com.dingyi.myluaapp.openapi.components.StateStorageOperation
import com.dingyi.myluaapp.openapi.components.Storage
import com.dingyi.myluaapp.openapi.components.stateStore
import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.util.ReflectionUtil
import com.intellij.util.ThreeState
import org.jetbrains.annotations.NonNls
import java.io.File
import java.nio.file.Path
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

open class StateStorageManagerImpl(
    override val componentManager: ServiceRegistry? = null,
    private val virtualFileTracker: StorageVirtualFileTracker? = createDefaultVirtualTracker(
        componentManager
    )
) : StateStorageManager {
    @Volatile
    protected var macros: List<Macro> = emptyList()

    protected val storageLock = ReentrantReadWriteLock()
    private val storages = HashMap<String, StateStorage>()

    val compoundStreamProvider: CompoundStreamProvider = CompoundStreamProvider()


    override fun addStreamProvider(provider: StreamProvider, first: Boolean) {
        if (first) {
            compoundStreamProvider.providers.add(0, provider)
        } else {
            compoundStreamProvider.providers.add(provider)
        }
    }



    override fun removeStreamProvider(clazz: Class<out StreamProvider>) {
        compoundStreamProvider.providers.removeAll { clazz.isInstance(it) }
    }

    // access under storageLock
    @Suppress("LeakingThis")
    private var isUseVfsListener = when (componentManager) {
        null, is IDEApplication -> ThreeState.NO
        else -> ThreeState.UNSURE // unsure because depends on stream provider state
    }

    override fun getStateStorage(storageSpec: Storage): StateStorage {
        TODO("Not yet implemented")
    }


    // overridden in upsource
    protected open fun createStateStorage(storageClass: Class<out StateStorage>,
                                          collapsedPath: String,
                                          exclusive: Boolean = false): StateStorage {
        if (storageClass != StateStorage::class.java) {
            val constructor = storageClass.constructors.first { it.parameterCount <= 3 }
            constructor.isAccessible = true
            if (constructor.parameterCount == 2) {
                return constructor.newInstance(componentManager!!, this) as StateStorage
            }
            else {
                return constructor.newInstance(collapsedPath, componentManager!!, this) as StateStorage
            }
        }

        if (isUseVfsListener == ThreeState.UNSURE) {
            isUseVfsListener = ThreeState.fromBoolean(!compoundStreamProvider.isApplicable(collapsedPath))
        }

        val filePath = expandMacro(collapsedPath)
      /*  @Suppress("DEPRECATION")
        if (stateSplitter != StateSplitter::class.java && stateSplitter != StateSplitterEx::class.java) {
            val storage = createDirectoryBasedStorage(filePath, collapsedPath, ReflectionUtil.newInstance(stateSplitter))
            if (storage is StorageVirtualFileTracker.TrackedStorage) {
                virtualFileTracker?.put(filePath.systemIndependentPath, storage)
            }
            return storage
        }*/

        val app = ApplicationManager.getApplication()

        val storage = createFileBasedStorage(filePath, collapsedPath)
        if (isUseVfsListener == ThreeState.YES && storage is StorageVirtualFileTracker.TrackedStorage) {
            virtualFileTracker?.put(filePath.toString(), storage)
        }
        return storage
    }

    protected open fun createFileBasedStorage(path: Path,
                                              collapsedPath: String): StateStorage {
        val provider =
            compoundStreamProvider

        return MyFileStorage(this, path, collapsedPath, provider)
    }



    companion object {
        private fun createDefaultVirtualTracker(componentManager: ServiceRegistry?): StorageVirtualFileTracker? {
            return when (componentManager) {
                null -> {
                    null
                }
                is IDEApplication -> {
                    StorageVirtualFileTracker((componentManager as IDEApplication).messageBus)
                }
                else -> {
                    val tracker = (ApplicationManager.getApplication().stateStore.storageManager as? StateStorageManagerImpl)?.virtualFileTracker
                        ?: return null
                    Disposer.register(componentManager, Disposable {
                        tracker.remove { it.storageManager.componentManager == componentManager }
                    })
                    tracker
                }
            }
        }
    }

    fun clearStorages() {
        storageLock.write {
            try {
                if (virtualFileTracker != null) {
                    clearVirtualFileTracker(virtualFileTracker)
                }
            }
            finally {
                storages.clear()
            }
        }
    }

    protected open fun clearVirtualFileTracker(virtualFileTracker: StorageVirtualFileTracker) {
        for (collapsedPath in storages.keys) {
            virtualFileTracker.remove(expandMacro(collapsedPath).systemIndependentPath)
        }
    }

    protected open fun normalizeFileSpec(fileSpec: String): String {
        val path = FileUtilRt.toSystemIndependentName(fileSpec)
        // fileSpec for directory based storage could be erroneously specified as "name/"
        return if (path.endsWith('/')) path.substring(0, path.length - 1) else path
    }

    //protected open fun getMacroSubstitutor(fileSpec: String): PathMacroSubstitutor? = macroSubstitutor

    override fun expandMacro(collapsedPath: String): Path {
        for ((key, value) in macros) {
            if (key == collapsedPath) {
                return value
            }

            if (collapsedPath.length > (key.length + 2) && collapsedPath[key.length] == '/' && collapsedPath.startsWith(key)) {
                return value.resolve(collapsedPath.substring(key.length + 1))
            }
        }

        throw IllegalStateException("Cannot resolve $collapsedPath in $macros")
    }

    // storageCustomizer - to ensure that other threads will use fully constructed and configured storage (invoked under the same lock as created)
    fun getOrCreateStorage(collapsedPath: String,
                           storageClass: Class<out StateStorage> = StateStorage::class.java,
                           exclusive: Boolean = false,
                           storageCustomizer: (StateStorage.() -> Unit)? = null,
                           storageCreator: StorageCreator? = null): StateStorage {
        val normalizedCollapsedPath = normalizeFileSpec(collapsedPath)
        val key = computeStorageKey(storageClass, normalizedCollapsedPath, collapsedPath, storageCreator)
        val storage = storageLock.read { storages.get(key) } ?: return storageLock.write {
            storages.getOrPut(key) {
                val storage = when (storageCreator) {
                    null -> createStateStorage(storageClass, normalizedCollapsedPath, exclusive)
                    else -> storageCreator.create(this)
                }
                storageCustomizer?.let { storage.it() }
                storage
            }
        }

        storageCustomizer?.let { storage.it() }
        return storage
    }

    private fun computeStorageKey(storageClass: Class<out StateStorage>, normalizedCollapsedPath: String, collapsedPath: String, storageCreator: StorageCreator?): String {
        if (storageClass != StateStorage::class.java) {
            return storageClass.name
        }
        if (normalizedCollapsedPath.isEmpty()) {
            throw Exception("Normalized path is empty, raw path '$collapsedPath'")
        }
        return storageCreator?.key ?: normalizedCollapsedPath
    }

    fun collapseMacro(path: String): String {
        for ((key, value) in macros) {
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
            val result = (path as java.lang.String).replace(value.systemIndependentPath, key)
            if (result !== path) {
                return result
            }
        }
        return normalizeFileSpec(path)
    }

    final override fun getOldStorage(component: Any, componentName: String, operation: StateStorageOperation): StateStorage? {
        val oldStorageSpec = getOldStorageSpec(component, componentName, operation) ?: return null
        return getOrCreateStorage(oldStorageSpec)
    }

    protected open fun getOldStorageSpec(component: Any, componentName: String, operation: StateStorageOperation): String? = null
}

fun removeMacroIfStartsWith(path: String, macro: String): String {
    return path.removePrefix("$macro/")
}



data class Macro(val key: String, var value: Path)


val Path.systemIndependentPath: String
    get() = toString().replace(File.separatorChar, '/')

val Path.parentSystemIndependentPath: String
    get() = parent!!.toString().replace(File.separatorChar, '/')
