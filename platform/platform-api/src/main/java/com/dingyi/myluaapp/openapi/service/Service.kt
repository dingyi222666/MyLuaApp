package com.dingyi.myluaapp.openapi.service

/**
 * Wraps a single service instance. Implementations must be thread safe.
 */
interface Service {
    fun get(): Any

    fun getDisplayName(): String

}