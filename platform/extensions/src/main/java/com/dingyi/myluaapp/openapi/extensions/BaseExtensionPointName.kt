package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionPointImpl
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionsAreaImpl

abstract class BaseExtensionPointName<T : Any>(val name: String) {
    override fun toString(): String = name

    protected fun getPointImpl(areaInstance: AreaInstance?): ExtensionPointImpl<T> {
        val area = (areaInstance?.extensionArea ?: Extensions.getRootArea()) as ExtensionsAreaImpl
        return area.getExtensionPoint(name)
    }
}