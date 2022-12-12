package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.openapi.components.PersistentStateComponent
import com.dingyi.myluaapp.openapi.components.StateStorage
import com.intellij.openapi.progress.ProcessCanceledException
import org.json.JSONObject


abstract class StorageBaseEx<T : Any> : StateStorageBase<T>() {
    internal fun <S : Any> createGetSession(component: PersistentStateComponent<S>, componentName: String, stateClass: Class<S>, reload: Boolean = false): StateGetter<S> {
        return StateGetterImpl(component, componentName, getStorageData(reload), stateClass, this)
    }

    /**
     * serializedState is null if state equals to default (see XmlSerializer.serializeIfNotDefault)
     */
    abstract fun archiveState(storageData: T, componentName: String, serializedState: JSONObject?)
}

internal fun <S : Any> createStateGetter(isUseLoadedStateAsExisting: Boolean, storage: StateStorage, component: PersistentStateComponent<S>, componentName: String, stateClass: Class<S>, reloadData: Boolean): StateGetter<S> {
    if (isUseLoadedStateAsExisting && storage is StorageBaseEx<*>) {
        return storage.createGetSession(component, componentName, stateClass, reloadData)
    }

    return object : StateGetter<S> {
        override fun getState(mergeInto: S?): S? {
            return storage.getState(component, componentName, stateClass, mergeInto, reloadData)
        }

        override fun archiveState(): S? = null
    }
}


interface StateGetter<S : Any> {
    fun getState(mergeInto: S? = null): S?

    fun archiveState(): S?
}

private class StateGetterImpl<S : Any, T : Any>(private val component: PersistentStateComponent<S>,
                                                private val componentName: String,
                                                private val storageData: T,
                                                private val stateClass: Class<S>,
                                                private val storage: StorageBaseEx<T>) : StateGetter<S> {
    private var serializedState: JSONObject? = null

    override fun getState(mergeInto: S?): S? {
       // LOG.assertTrue(serializedState == null)

        serializedState = storage.getSerializedState(storageData, component, componentName, archive = false)
        return storage.deserializeState(serializedState, stateClass, mergeInto)
    }

    override fun archiveState() : S? {
        if (serializedState == null) {
            return null
        }

        val stateAfterLoad = try {
            component.state
        }
        catch (e: ProcessCanceledException) {
            throw e
        }
        catch (e: Throwable) {
            //PluginException.logPluginError(LOG, "Cannot get state after load", e, component.javaClass)
            null
        }

        val serializedStateAfterLoad = if (stateAfterLoad == null) {
            serializedState
        }
        else {

           serializeState(stateAfterLoad)?.let {
                if (it.length() == 0) null else it
            }
        }


       // storage.archiveState(storageData, componentName, serializedStateAfterLoad)
        return stateAfterLoad
    }
}