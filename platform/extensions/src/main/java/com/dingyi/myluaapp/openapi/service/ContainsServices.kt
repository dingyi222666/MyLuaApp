package com.dingyi.myluaapp.openapi.service

/**
 * Represents a source of services.
 */
 interface ContainsServices {
    fun asProvider(): ServiceProvider
}