package com.dingyi.myluaapp.build.internal.event

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.internal.dispatch.Dispatch
import com.dingyi.myluaapp.build.internal.dispatch.MethodInvocation
import com.dingyi.myluaapp.build.internal.dispatch.ProxyDispatchAdapter

open class ListenerBroadcast<T : Any>(
    private val type: Class<T>
):Dispatch<MethodInvocation> {

    private lateinit var source: ProxyDispatchAdapter<T>

    private var broadcast = BroadcastDispatch.empty(type)


    /**
     * Returns the broadcaster. Any method call on this object is broadcast to all listeners.
     *
     * @return The broadcaster.
     */
    fun getSource(): T {
        if (this::source.isInitialized.not()) {
            source = ProxyDispatchAdapter(this, type)
        }
        return source.getSource()
    }

    /**
     * Returns the type of listener to which this class broadcasts.
     *
     * @return The type of the broadcaster.
     */
    fun getType(): Class<T> {
        return type
    }

    /**
     * Returns `true` if no listeners are registered with this object.
     *
     * @return `true` if no listeners are registered with this object, `false` otherwise
     */
    fun isEmpty(): Boolean {
        return broadcast.isEmpty()
    }

    /**
     * Adds a listener.
     *
     * @param listener The listener.
     */
    fun add(listener: T) {
        broadcast = broadcast.add(listener)
    }

    /**
     * Adds the given listeners.
     *
     * @param listeners The listeners
     */
    fun addAll(listeners: Collection<T>) {
        broadcast = broadcast.addAll(listeners)
    }

    /**
     * Adds a [Dispatch] to receive events from this broadcast.
     */
    fun add(dispatch: Dispatch<MethodInvocation>) {
        broadcast = broadcast.add(dispatch)
    }

    /**
     * Adds an action to be executed when the given method is called.
     */
    fun add(methodName: String, action: Action<*>) {
        broadcast = broadcast.add(methodName, action)
    }

    /**
     * Removes the given listener.
     *
     * @param listener The listener.
     */
    fun remove(listener: Any) {
        broadcast = broadcast.remove(listener)
    }

    /**
     * Removes the given listeners.
     *
     * @param listeners The listeners
     */
    fun removeAll(listeners: Collection<*>) {
        broadcast = broadcast.removeAll(listeners)
    }

    /**
     * Removes all listeners.
     */
    fun removeAll() {
        broadcast = BroadcastDispatch.empty(type)
    }

    /**
     * Broadcasts the given event to all listeners.
     *
     * @param event The event
     */
    override fun dispatch(event: MethodInvocation) {
        broadcast.dispatch(event)
    }

    fun visitListeners(visitor: Action<T>) {
        broadcast.visitListeners(visitor)
    }

    /**
     * Returns a new [ListenerBroadcast] with the same [BroadcastDispatch] as this class.
     */
    fun copy(): ListenerBroadcast<T> {
        val result = ListenerBroadcast<T>(type)
        result.broadcast = broadcast
        return result
    }

}