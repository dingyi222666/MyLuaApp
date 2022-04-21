package com.dingyi.myluaapp.build.api.services

import java.lang.reflect.Type

interface ServiceProvider {
    fun getService(serviceType: Type): Service
}