package com.dingyi.myluaapp.configurationStore


import com.dingyi.myluaapp.openapi.components.StateStorage
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.diagnostic.logger
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicReference

abstract class StateStorageBase<T : Any> : StateStorage {
    companion object {
        private val LOG = logger<StateStorageBase<*>>()
    }

    private var isSavingDisabled = false

    protected val storageDataRef: AtomicReference<T> = AtomicReference()

    override fun <T : Any> getState(component: Any?, componentName: String, stateClass: Class<T>, mergeInto: T?, reload: Boolean): T? {
        return getState(component, componentName, stateClass, reload, mergeInto)
    }

    fun <T : Any> getState(component: Any?, componentName: String, stateClass: Class<T>, reload: Boolean = false, mergeInto: T? = null): T? {
        return deserializeState(getSerializedState(getStorageData(reload), component, componentName, archive = false), stateClass, mergeInto)
    }

    fun getStorageData(): T = getStorageData(false)

    open fun <S: Any> deserializeState(serializedState: JSONObject?, stateClass: Class<S>, mergeInto: S?): S? {
        return com.dingyi.myluaapp.configurationStore.deserializeState(serializedState, stateClass, mergeInto)
    }

    abstract fun getSerializedState(storageData: T, component: Any?, componentName: String, archive: Boolean = true): JSONObject?

    protected abstract fun hasState(storageData: T, componentName: String): Boolean

    final override fun hasState(componentName: String, reloadData: Boolean): Boolean {
        return hasState(getStorageData(reloadData), componentName)
    }

    protected fun getStorageData(reload: Boolean = false): T {
        val storageData = storageDataRef.get()
        if (storageData != null && !reload) {
            return storageData
        }

        val newStorageData = loadData()
        if (storageDataRef.compareAndSet(storageData, newStorageData)) {
            return newStorageData
        }
        else {
            return getStorageData(false)
        }
    }

    protected abstract fun loadData(): T

    fun disableSaving() {
        LOG.debug { "Disable saving: ${toString()}" }
        isSavingDisabled = true
    }

    fun enableSaving() {
        LOG.debug { "Enable saving: ${toString()}" }
        isSavingDisabled = false
    }

    protected fun checkIsSavingDisabled(): Boolean {
        if (isSavingDisabled) {
            LOG.debug { "Saving disabled: ${toString()}" }
            return true
        }
        else {
            return false
        }
    }
}



class UnresolvedReadOnlyFilesException(val files: List<VirtualFile>) : RuntimeException()