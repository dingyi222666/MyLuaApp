package com.dingyi.myluaapp.build.api.objects

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.provider.Provider


/**
 * Provides a domain object of the given type.
 *
 * @param <T> type of domain object
 * @since 4.10
</T> */
interface NamedDomainObjectProvider<T> : Provider<T> {
    /**
     * Configures the domain object with the given action. Actions are run in the order added.
     *
     * @param action A [Action] that can configure the domain object when required.
     * @since 4.10
     */
    fun configure(action: Action<in T>)

    /**
     * The domain object name referenced by this provider.
     *
     *
     * Must be constant for the life of the object.
     *
     * @return The domain object. Never null.
     * @since 4.10
     */
    fun getName(): String
}
