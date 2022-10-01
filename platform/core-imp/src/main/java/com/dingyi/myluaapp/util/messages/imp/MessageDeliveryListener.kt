package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.Topic


fun interface MessageDeliveryListener {
    fun messageDelivered(
        topic: Topic<*>?,
        messageName: String?,
        handler: Any?,
        durationNanos: Long
    )
}
