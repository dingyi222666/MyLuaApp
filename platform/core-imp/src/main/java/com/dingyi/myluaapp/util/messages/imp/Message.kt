package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.Topic
import java.lang.invoke.MethodHandle

internal class Message(
    @JvmField val topic: Topic<*>,
    // we don't bind args as part of MethodHandle creation, because object is not known yet - so, MethodHandle here is not ready to use
    @JvmField val method: MethodHandle,
    @JvmField val methodName: String,
    // it allows us to cache MethodHandle per method and partially reuse it
    @JvmField val args: Array<Any?>?,
    @JvmField val handlers: Array<Any?>,
    @JvmField val bus: MessageBusImpl,
) {
    // to avoid creating Message for each handler
    // see note about pumpMessages in createPublisher (invoking job handlers can be stopped and continued as part of another pumpMessages call)
    @JvmField var currentHandlerIndex = 0

    override fun toString(): String {
        return "Message(topic=$topic, method=$methodName, args=${args.contentToString()}, handlers=${handlers.contentToString()})"
    }
}
