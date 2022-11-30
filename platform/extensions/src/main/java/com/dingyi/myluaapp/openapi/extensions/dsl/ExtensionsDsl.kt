package com.dingyi.myluaapp.openapi.extensions.dsl

import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionPointBuilder
import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionsImplementationBuilder
import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointDescriptor
import com.dingyi.myluaapp.openapi.extensions.LoadingOrder

fun ExtensionsImplementationBuilder.toExtensionDescriptor(): ExtensionDescriptor {

    return ExtensionDescriptor(
        implementation = implementation,
        orderId = null,
        order = LoadingOrder.FIRST
    ).also {
        it.rawDescriptor = this
    }
}

fun ExtensionPointBuilder.toExtensionPointDescriptor(): ExtensionPointDescriptor {
    return ExtensionPointDescriptor(
        name = name,
        isBean = this.beanClass != null,
        isDynamic = true,
        isNameQualified = isQualifiedName,
        className = if (beanClass != null) beanClass.toString() else interfaceClass.toString()
    ).also {
        it.rawDescriptor = this
    }
}