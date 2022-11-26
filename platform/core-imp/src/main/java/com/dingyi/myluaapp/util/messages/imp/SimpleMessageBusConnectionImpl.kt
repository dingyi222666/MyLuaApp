package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.MessageHandler
import com.dingyi.myluaapp.util.messages.SimpleMessageBusConnection
import com.dingyi.myluaapp.util.messages.Topic
import com.intellij.util.ArrayUtilRt
import java.util.Arrays
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Predicate

internal class SimpleMessageBusConnectionImpl(bus: MessageBusImpl): SimpleMessageBusConnection,
    MessageBusImpl.MessageHandlerHolder {



    override fun disconnect() {
        // already disposed
        val bus = bus ?: return
        this.bus = null
        // reset as bus will not remove disposed connection from list immediately
        // bus.notifyConnectionTerminated(subscriptions.getAndSet(ArrayUtilRt.EMPTY_OBJECT_ARRAY))
    }


    @JvmField
    var bus: MessageBusImpl?

    @JvmField
    protected val subscriptions = AtomicReference(ArrayUtilRt.EMPTY_OBJECT_ARRAY)

    override val isDisposed: Boolean
        get() = bus == null

    init {
        this.bus = bus
    }

    override fun <L : Any> subscribe(topic: Topic<L>, handler: L) {
        var list: Array<Any>
        var newList: Array<Any>
        do {
            list = subscriptions.get()
            if (list.isEmpty()) {
                newList = arrayOf(topic, handler)
            } else {
                val size = list.size
                @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
                newList = Arrays.copyOf(list, size + 2)
                newList[size] = topic
                newList[size + 1] = handler
            }
        } while (!subscriptions.compareAndSet(list, newList))
        //bus?.notifyOnSubscription(topic)
    }

    override fun <L> collectHandlers(topic: Topic<L>, result: MutableList<L>) {
        val list = subscriptions.get() as List<L>
        var i = 0
        val n = list.size
        while (i < n) {
            if (list[i] === topic) {
                result.add(list[i + 1])
            }
            i += 2
        }
    }

    override fun disconnectIfNeeded(predicate: Predicate<Class<*>>) {
        while (true) {
            val list = subscriptions.get()
            var newList: MutableList<Any>? = null
            var i = 0
            while (i < list.size) {
                if (predicate.test(list[i + 1].javaClass)) {
                    if (newList == null) {
                        newList = list.asList().subList(0, i).toMutableList()
                    }
                } else if (newList != null) {
                    newList.add(list[i])
                    newList.add(list[i + 1])
                }
                i += 2
            }

            if (newList == null) {
                return
            }

            if (newList.isEmpty()) {
                disconnect()
                return
            } else if (subscriptions.compareAndSet(list, newList.toTypedArray())) {
                break
            }
        }
    }


    override fun toString() = subscriptions.get().contentToString()

}
