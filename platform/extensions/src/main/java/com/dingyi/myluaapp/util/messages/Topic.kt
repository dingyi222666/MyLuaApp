package com.dingyi.myluaapp.util.messages

import org.jetbrains.annotations.NonNls
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Defines messaging endpoint within particular [bus][MessageBus].
 *
 * @param <L> type of the interface that defines contract for working with the particular topic instance
</L> */
class Topic<L> {
    /**
     * Indicates that messages the of annotated topic are published to an application level message bus.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(AnnotationTarget.FIELD)
    annotation class AppLevel

    /**
     * Indicates that messages the of annotated topic are published to a project level message bus.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(AnnotationTarget.FIELD)
    annotation class ProjectLevel

    /**
     * @return human-readable name of the current topic. Is intended to be used in informational/logging purposes only
     */
    @get:NonNls
    val displayName: String

    /**
     * Allows to retrieve class that defines contract for working with the current topic. Either publishers or subscribers use it:
     *
     *  *
     * publisher [receives][MessageBus.syncPublisher] object that IS-A target interface from the messaging infrastructure.
     * It calls target method with the target arguments on it then (method of the interface returned by the current method);
     *
     *  *
     * the same method is called on handlers of all [subscribers][MessageBusConnection.subscribe] that
     * should receive the message;
     *
     *
     *
     * @return class of the interface that defines contract for working with the current topic
     */
    val listenerClass: Class<L>

    /**
     * @return broadcasting strategy configured for the current topic. Default value is [BroadcastDirection.TO_CHILDREN]
     * @see BroadcastDirection
     */
    val broadcastDirection: BroadcastDirection
    val isImmediateDelivery: Boolean

    /**
     * Consider using [.Topic] and [BroadcastDirection.NONE].
     */
    constructor(listenerClass: Class<L>) : this(
        listenerClass.simpleName,
        listenerClass,
        BroadcastDirection.TO_CHILDREN
    ) {
    }

    constructor(listenerClass: Class<L>, broadcastDirection: BroadcastDirection) : this(
        listenerClass.simpleName,
        listenerClass,
        broadcastDirection
    ) {
    }

    constructor(
        listenerClass: Class<L>,
        broadcastDirection: BroadcastDirection,
        immediateDelivery: Boolean
    ) {
        displayName = listenerClass.simpleName
        this.listenerClass = listenerClass
        this.broadcastDirection = broadcastDirection
        isImmediateDelivery = immediateDelivery
    }

    @JvmOverloads
    constructor(
        @NonNls name: String,
        listenerClass: Class<L>,
        broadcastDirection: BroadcastDirection = BroadcastDirection.TO_CHILDREN
    ) {
        displayName = name
        this.listenerClass = listenerClass
        this.broadcastDirection = broadcastDirection
        isImmediateDelivery = false
    }

    override fun toString(): String {
        return "Topic(" +
                "name='" + displayName + '\'' +
                ", listenerClass=" + listenerClass +
                ", broadcastDirection=" + broadcastDirection +
                ", immediateDelivery=" + isImmediateDelivery +
                ')'
    }

    /**
     * [Message buses][MessageBus] may be organised into [hierarchies][MessageBus.getParent]. That allows to provide
     * additional messaging features like `'broadcasting'`. Here it means that messages sent to particular topic within
     * particular message bus may be dispatched to subscribers of the same topic within another message buses.
     *
     *
     * Current enum holds available broadcasting options.
     */
    enum class BroadcastDirection {
        /**
         * The message is dispatched to all subscribers of the target topic registered within the child message buses.
         *
         *
         * Example:
         * <pre>
         * parent-bus &lt;--- topic1
         * /       \
         * /         \
         * topic1 ---&gt; child-bus1     child-bus2 &lt;--- topic1
        </pre> *
         *
         *
         * Here subscribers of the `'topic1'` registered within the `'child-bus1'` and `'child-bus2'`
         * will receive the message sent to the `'topic1'` topic at the `'parent-bus'`.
         */
        TO_CHILDREN,

        /**
         * Use only for application level publishers. To avoid collection subscribers from modules.
         */
        TO_DIRECT_CHILDREN,

        /**
         * No broadcasting is performed.
         */
        NONE,

        /**
         * The message send to particular topic at particular bus is dispatched to all subscribers of the same topic within the parent bus.
         *
         *
         * Example:
         * <pre>
         * parent-bus &lt;--- topic1
         * |
         * child-bus &lt;--- topic1
        </pre> *
         *
         *
         * Here subscribers of the `topic1` registered within `'parent-bus'` will receive messages posted
         * to the same topic within `'child-bus'`.
         */
        TO_PARENT
    }

    companion object {
        fun <L> create(@NonNls displayName: String, listenerClass: Class<L>): Topic<L> {
            return Topic(displayName, listenerClass)
        }

        fun <L> create(
            @NonNls displayName: String,
            listenerClass: Class<L>,
            direction: BroadcastDirection
        ): Topic<L> {
            return Topic(displayName, listenerClass, direction)
        }
    }
}