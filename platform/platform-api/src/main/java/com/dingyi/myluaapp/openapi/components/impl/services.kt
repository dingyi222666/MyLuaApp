package com.dingyi.myluaapp.openapi.components

import com.dingyi.myluaapp.openapi.components.impl.IComponentStore
import com.dingyi.myluaapp.openapi.service.ServiceRegistry


val ServiceRegistry.stateStore: IComponentStore
    get() {
        return when (this) {
           // is ProjectStoreOwner -> this.componentStore
            else -> {
                // module or application service
                get(IComponentStore::class.java)
            }
        }
    }