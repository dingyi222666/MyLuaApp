package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.openapi.Disposable
import com.dingyi.myluaapp.util.messages.MessageBus
import com.dingyi.myluaapp.util.messages.MessageBusConnection
import com.dingyi.myluaapp.util.messages.SimpleMessageBusConnection
import com.dingyi.myluaapp.util.messages.Topic

class MessageBusImpl:MessageBus {
    override fun getParent(): MessageBus {
        TODO("Not yet implemented")
    }

    override fun connect(): MessageBusConnection {
        TODO("Not yet implemented")
    }

    override fun connect(parentDisposable: Disposable): MessageBusConnection {
        TODO("Not yet implemented")
    }

    override fun simpleConnect(): SimpleMessageBusConnection {
        TODO("Not yet implemented")
    }

    override fun <L> syncPublisher(topic: Topic<L>): L {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

    override fun isDisposed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasUndeliveredEvents(topic: Topic<*>): Boolean {
        TODO("Not yet implemented")
    }
}