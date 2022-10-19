package com.dingyi.myluaapp.openapi.util

interface ExtensionFactory {
    fun createInstance(factoryArgument: String, implementationClass: String?): Any
}
