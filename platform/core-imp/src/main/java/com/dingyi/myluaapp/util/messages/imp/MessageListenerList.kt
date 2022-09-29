package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.MessageBus
import com.dingyi.myluaapp.util.messages.SimpleMessageBusConnection
import com.dingyi.myluaapp.util.messages.Topic
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import org.jetbrains.annotations.NotNull
import java.util.concurrent.ConcurrentHashMap


class MessageListenerList<T : Any>(messageBus: MessageBus, topic: Topic<T>) {
    private val myMessageBus: MessageBus
    private val myTopic: Topic<T>
    private val myListenerToConnectionMap: MutableMap<T, SimpleMessageBusConnection> =
        ConcurrentHashMap()

    init {
        myTopic = topic
        myMessageBus = messageBus
    }

    fun add(listener: T) {
        val connection: SimpleMessageBusConnection = myMessageBus.simpleConnect()
        connection.subscribe(myTopic, listener)
        myListenerToConnectionMap[listener] = connection
    }

    fun add(listener: T, parentDisposable: Disposable) {
        Disposer.register(parentDisposable) { myListenerToConnectionMap.remove(listener) }
        val connection = myMessageBus.connect(parentDisposable)
        connection.subscribe(myTopic, listener)
        myListenerToConnectionMap[listener] = connection
    }

    fun remove(@NotNull listener: T) {
        myListenerToConnectionMap.remove(listener)?.disconnect()
    }
}