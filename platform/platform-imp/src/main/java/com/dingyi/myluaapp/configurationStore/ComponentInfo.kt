package com.dingyi.myluaapp.configurationStore

import com.dingyi.myluaapp.openapi.components.PersistentStateComponentWithModificationTracker
import com.dingyi.myluaapp.openapi.components.State
import com.dingyi.myluaapp.openapi.components.Storage
import com.dingyi.myluaapp.openapi.util.ModificationTracker
import com.intellij.util.ThreeState
import java.util.concurrent.TimeUnit


internal fun createComponentInfo(
    component: Any,
    stateSpec: State?
): ComponentInfo {
    val result = when (component) {
        is PersistentStateComponentWithModificationTracker<*> -> ComponentWithStateModificationTrackerInfo(
            component,
            stateSpec
        )

        is ModificationTracker -> ComponentWithModificationTrackerInfo(
            component,
            stateSpec
        )

        else -> ComponentInfoImpl(component, stateSpec)
    }

    if (stateSpec != null && stateSpec.storages.isNotEmpty() && stateSpec.storages.all {
            it.deprecated || isUseSaveThreshold(
                it
            )
        }) {
        result.lastSaved = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toInt()
    }
    return result
}

private fun isUseSaveThreshold(storage: Storage): Boolean {
    return false
}

abstract class ComponentInfo {

    abstract val configurationSchemaKey: String?
    abstract val component: Any
    abstract val stateSpec: State?

    abstract val lastModificationCount: Long
    abstract val currentModificationCount: Long

    abstract val isModificationTrackingSupported: Boolean

    var lastSaved: Int = -1

    var affectedPropertyNames: List<String> = emptyList()

    open fun updateModificationCount(newCount: Long) {
    }
}

internal class ComponentInfoImpl(override val component: Any, override val stateSpec: State?) :
    ComponentInfo() {

    override val isModificationTrackingSupported = false
    override val configurationSchemaKey: String?
        get() = component::class.java.name

    override val lastModificationCount: Long
        get() = -1

    override val currentModificationCount: Long
        get() = -1
}

private abstract class ModificationTrackerAwareComponentInfo : ComponentInfo() {
    final override val isModificationTrackingSupported = true

    abstract override var lastModificationCount: Long

    final override fun updateModificationCount(newCount: Long) {
        lastModificationCount = newCount
    }
}

private class ComponentWithStateModificationTrackerInfo(
    override val component: PersistentStateComponentWithModificationTracker<*>,
    override val stateSpec: State?
) : ModificationTrackerAwareComponentInfo() {
    override val currentModificationCount: Long
        get() = component.stateModificationCount

    override val configurationSchemaKey: String?
        get() = component::class.java.name

    override var lastModificationCount = currentModificationCount
}

private class ComponentWithModificationTrackerInfo(
    override val component: ModificationTracker,
    override val stateSpec: State?
) : ModificationTrackerAwareComponentInfo() {
    override val currentModificationCount: Long
        get() = component.getModificationCount()

    override val configurationSchemaKey: String?
        get() = component::class.java.name

    override var lastModificationCount = currentModificationCount
}