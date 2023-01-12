package com.dingyi.myluaapp.ide

import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName


/**
 * Use extension point `com.intellij.applicationInitializedListener` to register listener.
 * Please note - you cannot use [ExtensionPointName.findExtension] because this extension point is cleared up after app loading.
 *
 *
 * Not part of [ApplicationLoadListener] to avoid class loading before application initialization.
 */

interface ApplicationInitializedListener {
    /**
     * Invoked when all application level components are initialized in the same thread where components are initializing (EDT is not guaranteed).
     * Write actions and time-consuming activities are not recommended because listeners are invoked sequentially and directly affects application start time.
     */
    fun componentsInitialized()
}
