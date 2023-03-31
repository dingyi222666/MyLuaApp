package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionsAreaImpl
import com.dingyi.myluaapp.openapi.util.Disposable
import com.dingyi.myluaapp.openapi.util.Disposer


object Extensions {
    private var ourRootArea: ExtensionsAreaImpl? = null

    fun setRootArea(area: ExtensionsAreaImpl) {
        ourRootArea = area
    }
    fun setRootArea(area: ExtensionsAreaImpl, parentDisposable: Disposable) {
        val oldRootArea = ourRootArea
        ourRootArea = area
        Disposer.register(parentDisposable) {
          //  ourRootArea?.notifyAreaReplaced(oldRootArea)
            ourRootArea = oldRootArea
        }
    }

    //@Deprecated("Use {@link ComponentManager#getExtensionArea()}")
    fun getRootArea(): ExtensionsArea {
        return ourRootArea ?: throw IllegalStateException("Extensions root area is not initialized")
    }
}