package com.dingyi.myluaapp.util.messages

import java.lang.invoke.MethodHandle
import java.lang.reflect.Method

/**
 * Defines contract for generic message subscriber processor.
 */
fun interface MessageHandler {
    /**
     * Is called on new message arrival. Given method identifies method used by publisher (see [Topic.getListenerClass]),
     * given parameters were used by the publisher during target method call.
     *
     * @param event   information about target method called by the publisher
     * @param params  called method arguments
     */
    fun handle(event: MethodHandle, vararg params: Any?)
}