package com.dingyi.myluaapp.build.api.services

/**
 * Wraps a single service instance. Implementations must be thread safe.
 */
interface Service {
    fun get(): Any
}
