package com.dingyi.myluaapp.openapi.components.impl

import com.dingyi.myluaapp.configurationStore.StateStorageManager
import com.dingyi.myluaapp.openapi.components.PersistentStateComponent
import com.dingyi.myluaapp.openapi.dsl.plugin.service.ServiceDslBuilder
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.dingyi.myluaapp.util.messages.MessageBus
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.TestOnly
import java.nio.file.Path


interface IComponentStore {
    val storageManager: StateStorageManager

    fun setPath(path: Path)

    //?
    fun initComponent(component: Any, serviceDescriptor: ServiceDslBuilder.ServiceImplBuilder?, pluginId: PluginId?)


    fun unloadComponent(component: Any) {
    }

    fun initPersistencePlainComponent(component: Any, @NlsSafe key: String)

    fun reloadStates(componentNames: Set<String>, messageBus: MessageBus)

    fun reloadState(componentClass: Class<out PersistentStateComponent<*>>)

    fun isReloadPossible(componentNames: Set<String>): Boolean

    suspend fun save(forceSavingAllSettings: Boolean = false)

    @TestOnly
    fun saveComponent(component: PersistentStateComponent<*>)

    @TestOnly
    fun removeComponent(name: String)

    @TestOnly
    fun clearCaches() {
    }


    fun release() {}
}