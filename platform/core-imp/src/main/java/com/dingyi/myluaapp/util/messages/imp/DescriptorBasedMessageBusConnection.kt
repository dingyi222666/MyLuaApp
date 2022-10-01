package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.Topic
import java.util.function.Predicate

internal class DescriptorBasedMessageBusConnection(/*@JvmField val module: PluginDescriptor,*/
                                                   @JvmField val topic: Topic<*>,
                                                   @JvmField val handlers: List<Any>) :
    MessageBusImpl.MessageHandlerHolder {
    override fun collectHandlers(topic: Topic<*>, result: MutableList<Any>) {
        if (this.topic === topic) {
            result.addAll(handlers)
        }
    }

    override fun disconnectIfNeeded(predicate: Predicate<Class<*>>) {
    }

    // never empty
    override val isDisposed: Boolean
        get() = false

    override fun toString() = "DescriptorBasedMessageBusConnection(handlers=$handlers)"
}
