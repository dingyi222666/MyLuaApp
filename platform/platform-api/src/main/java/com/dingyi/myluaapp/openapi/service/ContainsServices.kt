package com.dingyi.myluaapp.openapi.service

/**
 * Represents a source of services.
 */
internal interface ContainsServices {
    fun asProvider(): ServiceProvider
}