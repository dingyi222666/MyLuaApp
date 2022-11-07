package com.dingyi.myluaapp.openapi.extensions.dsl

import com.dingyi.myluaapp.openapi.dsl.plugin.extension.ExtensionsImplementationBuilder
import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.LoadingOrder

fun ExtensionsImplementationBuilder.toExtensionDescriptor(): ExtensionDescriptor {

    return ExtensionDescriptor(
        implementation = implementation,
        orderId = null,
        order = LoadingOrder.FIRST
    )
}