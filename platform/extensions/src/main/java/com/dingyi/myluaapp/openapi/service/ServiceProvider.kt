package com.dingyi.myluaapp.openapi.service

import androidx.annotation.Nullable

interface ServiceProvider {

    /**
     * Locates a service instance of the given type. Returns null if this provider does not provide a service of this type.
     */
    @Nullable
    fun getService(serviceType: Class<*>): Service?


}