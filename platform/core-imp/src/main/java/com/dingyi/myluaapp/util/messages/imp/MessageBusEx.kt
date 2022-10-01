package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.MessageBus
import org.jetbrains.annotations.TestOnly
import java.util.function.Predicate

interface MessageBusEx : MessageBus {
    fun clearPublisherCache()


    /**
     * Must be called only on a root bus.
     */
    fun disconnectPluginConnections(predicate: Predicate<Class<*>>)

    @TestOnly
    fun clearAllSubscriberCache()


}
