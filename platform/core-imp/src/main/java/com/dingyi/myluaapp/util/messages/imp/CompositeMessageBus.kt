package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.ListenerDescriptor
import com.dingyi.myluaapp.util.messages.MessageBusOwner
import com.dingyi.myluaapp.util.messages.Topic
import com.dingyi.myluaapp.util.messages.Topic.BroadcastDirection
import com.intellij.openapi.util.Disposer
import com.intellij.util.ArrayUtilRt
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.annotations.TestOnly
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.function.Predicate


private val EMPTY_MAP = HashMap<String, MutableList<ListenerDescriptor>>()

@Suppress("ReplaceGetOrSet")
open class CompositeMessageBus : MessageBusImpl, MessageBusEx {
    private val childBuses = ContainerUtil.createLockFreeCopyOnWriteList<MessageBusImpl>()

    constructor(owner: MessageBusOwner, parentBus: CompositeMessageBus) : super(owner, parentBus)

    // root message bus constructor
    internal constructor(owner: MessageBusOwner) : super(owner)


    override fun hasChildren() = !childBuses.isEmpty()

    fun addChild(bus: MessageBusImpl) {
        childrenListChanged(this)
        childBuses.add(bus)
    }

    fun onChildBusDisposed(childBus: MessageBusImpl) {
        val removed = childBuses.remove(childBus)
        childrenListChanged(this)
        //LOG.assertTrue(removed)
    }

    override fun <L> createPublisher(
        topic: Topic<L>,
        direction: BroadcastDirection
    ): MessagePublisher<L> {
        return when (direction) {
            BroadcastDirection.TO_PARENT -> ToParentMessagePublisher(topic, this)
            BroadcastDirection.TO_DIRECT_CHILDREN -> {
                require(parentBus == null) {
                    "Broadcast direction TO_DIRECT_CHILDREN is allowed only for app level message bus. " +
                            "Please publish to app level message bus or change topic ${topic.listenerClass} broadcast direction to NONE or TO_PARENT"
                }
                ToDirectChildrenMessagePublisher(topic = topic, bus = this, childBuses = childBuses)
            }

            else -> MessagePublisher(topic, this)
        }
    }

    override fun computeSubscribers(topic: Topic<*>): Array<Any?> {
        // light project
        return if (owner.isDisposed) ArrayUtilRt.EMPTY_OBJECT_ARRAY else super.computeSubscribers(
            topic
        )
    }

    override fun doComputeSubscribers(topic: Topic<*>, result: MutableList<Any>) {
        /* if (subscribeLazyListeners) {
             subscribeLazyListeners(topic)
         }*/

        super.doComputeSubscribers(topic, result)

        if (topic.broadcastDirection == BroadcastDirection.TO_CHILDREN) {
            for (childBus in childBuses) {
                if (!childBus.isDisposed) {
                    childBus.doComputeSubscribers(topic, result)
                }
            }
        }
    }

    override fun notifyOnSubscriptionToTopicToChildren(topic: Topic<*>) {
        for (childBus in childBuses) {
            childBus.subscriberCache.remove(topic)
            childBus.notifyOnSubscriptionToTopicToChildren(topic)
        }
    }

    override fun notifyConnectionTerminated(topicAndHandlerPairs: Array<Any>): Boolean {
        val isChildClearingNeeded = super.notifyConnectionTerminated(topicAndHandlerPairs)
        if (!isChildClearingNeeded) {
            return false
        }

        childBuses.forEach { it.clearSubscriberCache(topicAndHandlerPairs) }

        // disposed handlers are not removed for TO_CHILDREN topics in the same way as for others directions
        // because it is not wise to check each child bus - waitingBuses list can be used instead of checking each child bus message queue
        rootBus.queue.queue.removeIf { nullizeHandlersFromMessage(it, topicAndHandlerPairs) }
        return false
    }

    override fun clearSubscriberCache(topicAndHandlerPairs: Array<Any>) {
        super.clearSubscriberCache(topicAndHandlerPairs)

        childBuses.forEach { it.clearSubscriberCache(topicAndHandlerPairs) }
    }

    override fun removeEmptyConnectionsRecursively() {
        super.removeEmptyConnectionsRecursively()

        childBuses.forEach(MessageBusImpl::removeEmptyConnectionsRecursively)
    }

    /**
     * Clear publisher cache, including child buses.
     */
    override fun clearPublisherCache() {
        // keep it simple - we can infer plugin id from topic.getListenerClass(), but granular clearing not worth the code complication
        publisherCache.clear()
        childBuses.forEach { childBus ->
            if (childBus is CompositeMessageBus) {
                childBus.clearPublisherCache()
            } else {
                childBus.publisherCache.clear()
            }
        }
    }


    override fun disconnectPluginConnections(predicate: Predicate<Class<*>>) {
        super.disconnectPluginConnections(predicate)

        childBuses.forEach { it.disconnectPluginConnections(predicate) }
    }

    @TestOnly
    override fun clearAllSubscriberCache() {
        //LOG.assertTrue(rootBus !== this)

        rootBus.subscriberCache.clear()
        subscriberCache.clear()
        childBuses.forEach { it.subscriberCache.clear() }
    }

    override fun disposeChildren() {
        childBuses.forEach(Disposer::dispose)
    }
}

private class ToDirectChildrenMessagePublisher<L>(
    topic: Topic<L>,
    bus: CompositeMessageBus,
    private val childBuses: List<MessageBusImpl>
) : MessagePublisher<L>(topic, bus), InvocationHandler {
    override fun publish(method: Method, args: Array<Any?>?, queue: MessageQueue?): Boolean {
        var exceptions: MutableList<Throwable>? = null
        var hasHandlers = false
        var handlers = bus.subscriberCache.computeIfAbsent(topic, bus::computeSubscribers)
        if (handlers.isNotEmpty()) {
            exceptions = executeOrAddToQueue(
                topic = topic,
                method = method,
                args = args,
                handlers = handlers,
                jobQueue = queue,
                prevExceptions = null,
                bus = bus
            )
            hasHandlers = true
        }

        for (childBus in childBuses) {
            // light project in tests is not disposed correctly
            if (childBus.owner.isDisposed) {
                continue
            }

            handlers = childBus.subscriberCache.computeIfAbsent(topic) { topic1 ->
                val result = mutableListOf<Any>()
                childBus.doComputeSubscribers(
                    topic = topic1,
                    result = result
                )
                if (result.isEmpty()) {
                    ArrayUtilRt.EMPTY_OBJECT_ARRAY
                } else {
                    result.toTypedArray()
                }
            }
            if (handlers.isEmpty()) {
                continue
            }

            hasHandlers = true
            exceptions = executeOrAddToQueue(
                topic = topic,
                method = method,
                args = args,
                handlers = handlers,
                jobQueue = queue,
                prevExceptions = exceptions,
                bus = childBus
            )
        }
        exceptions?.let(::throwExceptions)
        return hasHandlers
    }
}

private fun childrenListChanged(changedBus: MessageBusImpl) {
    var bus = changedBus
    while (true) {
        bus.subscriberCache.keys.removeIf { it.broadcastDirection == BroadcastDirection.TO_CHILDREN }
        bus = bus.parentBus ?: break
    }
}

private fun computeNewHandlers(handlers: List<Any>, excludeClassNames: Set<String?>): List<Any>? {
    var newHandlers: MutableList<Any>? = null
    var i = 0
    val size = handlers.size
    while (i < size) {
        val handler = handlers[i]
        if (excludeClassNames.contains(handler::class.java.name)) {
            if (newHandlers == null) {
                newHandlers =
                    if (i == 0) mutableListOf() else handlers.subList(0, i).toMutableList()
            }
        } else {
            newHandlers?.add(handler)
        }
        i++
    }
    return newHandlers
}

/**
 * Returns true if no more handlers.
 */
private fun nullizeHandlersFromMessage(
    message: Message,
    topicAndHandlerPairs: Array<Any>
): Boolean {
    var nullElementCount = 0
    for (messageIndex in message.handlers.indices) {
        val handler = message.handlers[messageIndex]
        if (handler == null) {
            nullElementCount++
        }

        var i = 0
        while (i < topicAndHandlerPairs.size) {
            if (message.topic === topicAndHandlerPairs[i] && handler === topicAndHandlerPairs[i + 1]) {
                message.handlers[messageIndex] = null
                nullElementCount++
            }
            i += 2
        }
    }
    return nullElementCount == message.handlers.size
}
