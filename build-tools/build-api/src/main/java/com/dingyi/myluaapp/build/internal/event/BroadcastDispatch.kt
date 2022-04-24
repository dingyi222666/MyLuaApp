package com.dingyi.myluaapp.build.internal.event

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.internal.dispatch.Dispatch
import com.dingyi.myluaapp.build.internal.dispatch.MethodInvocation
import com.dingyi.myluaapp.build.internal.dispatch.ReflectionDispatch


/**
 * An immutable composite [Dispatch] implementation. Optimized for a small number of elements, and for infrequent modification.
 */
abstract class BroadcastDispatch<T : Any> private constructor(type: Class<T>) :
    AbstractBroadcastDispatch<T>(type) {


    fun add(dispatch: Dispatch<MethodInvocation>): BroadcastDispatch<T> {
        return add(dispatch, dispatch)
    }

    fun add(listener: T): BroadcastDispatch<T> {
        return add(listener, ReflectionDispatch(listener))
    }

    fun add(methodName: String, action: Action<*>): BroadcastDispatch<T> {
        assertIsMethod(methodName)
        return add(
            action,
            ActionInvocationHandler(
                methodName,
                action as Action<Any?>
            )
        )
    }

    abstract fun add(
        handler: Any,
        dispatch: Dispatch<MethodInvocation>
    ): BroadcastDispatch<T>

    private fun assertIsMethod(methodName: String) {
        for (method in type.methods) {
            if (method.name == methodName) {
                return
            }
        }
        throw IllegalArgumentException(
            String.format(
                "Method %s() not found for listener type %s.", methodName,
                type.simpleName
            )
        )
    }

    abstract fun isEmpty(): Boolean

    abstract fun remove(listener: Any): BroadcastDispatch<T>

    abstract fun addAll(listeners: Collection<T>): BroadcastDispatch<T>

    abstract fun removeAll(listeners: Collection<*>): BroadcastDispatch<T>

    abstract fun visitListeners(visitor: Action<T>)
    private class ActionInvocationHandler constructor(
        private val methodName: String,
        private val action: Action<Any?>
    ) :
        Dispatch<MethodInvocation> {

        override fun dispatch(message: MethodInvocation) {
            if (message.getMethod().name == methodName) {
                action.execute(message.getArguments()[0])
            }
        }


    }

    private class EmptyDispatch<T : Any> internal constructor(type: Class<T>) :
        BroadcastDispatch<T>(type) {
        override fun toString(): String {
            return "<empty>"
        }

        override fun isEmpty(): Boolean {
            return true
        }

        override fun remove(listener: Any): BroadcastDispatch<T> {
            return this
        }

        override fun removeAll(listeners: Collection<*>): BroadcastDispatch<T> {
            return this
        }

        override fun add(
            handler: Any,
            dispatch: Dispatch<MethodInvocation>
        ): BroadcastDispatch<T> {
            return SingletonDispatch(type, handler, dispatch)
        }

        override fun visitListeners(visitor: Action<T>) {}
        override fun addAll(listeners: Collection<T>): BroadcastDispatch<T> {
            val result: MutableList<SingletonDispatch<T>> = ArrayList()
            for (listener in listeners) {
                val dispatch = SingletonDispatch(
                    type,
                    listener,
                    ReflectionDispatch(listener)
                )
                if (!result.contains(dispatch)) {
                    result.add(dispatch)
                }
            }
            if (result.isEmpty()) {
                return this
            }
            return if (result.size == 1) {
                result.iterator().next()
            } else CompositeDispatch(type, result)
        }

        override fun dispatch(message: MethodInvocation) {}

    }

    private class SingletonDispatch<T : Any>(
        type: Class<T>,
        val handler: Any,
        private val dispatch: Dispatch<MethodInvocation>
    ) :
        BroadcastDispatch<T>(type) {
        override fun toString(): String {
            return handler.toString()
        }

        override fun equals(obj: Any?): Boolean {
            val other = obj as? SingletonDispatch<*>
            return handler == other?.handler || handler == other?.handler
        }

        override fun hashCode(): Int {
            return handler.hashCode()
        }

        override fun add(
            handler: Any,
            dispatch: Dispatch<MethodInvocation>
        ): BroadcastDispatch<T> {

            if (this.handler == handler) {
                return this
            }

            val result = mutableListOf<SingletonDispatch<T>>()

            result.add(this)
            result.add(SingletonDispatch(type, handler, dispatch))
            return CompositeDispatch(type, result)
        }

        override fun addAll(listeners: Collection<T>): BroadcastDispatch<T> {
            val result = mutableListOf<SingletonDispatch<T>>()
            result.add(this)
            for (listener in listeners) {
                if (handler == listener) {
                    continue
                }
                val dispatch = SingletonDispatch(
                    type,
                    listener,
                    ReflectionDispatch(listener)
                )
                if (!result.contains(dispatch)) {
                    result.add(dispatch)
                }
            }
            return if (result.size == 1) {
                this
            } else CompositeDispatch(type, result)
        }

        override fun remove(listener: Any): BroadcastDispatch<T> {
            return if (handler == listener) {
                EmptyDispatch(type)
            } else this
        }

        override fun removeAll(listeners: Collection<*>): BroadcastDispatch<T> {
            for (listener in listeners) {
                if (handler == listener) {
                    return EmptyDispatch(type)
                }
            }
            return this
        }

        override fun isEmpty(): Boolean {
            return false
        }

        override fun visitListeners(visitor: Action<T>) {
            if (type.isInstance(handler)) {
                visitor.execute(type.cast(handler))
            }
        }

        override fun dispatch(message: MethodInvocation) {
            dispatch(message, dispatch)
        }

    }

    private class CompositeDispatch<T : Any> constructor(
        type: Class<T>,
        private val dispatchers: List<SingletonDispatch<T>>
    ) : BroadcastDispatch<T>(type) {

        override fun toString(): String {
            return dispatchers.toString()
        }

        override fun add(
            handler: Any,
            dispatch: Dispatch<MethodInvocation>
        ): BroadcastDispatch<T> {
            val result = mutableListOf<SingletonDispatch<T>>()
            for (listener in dispatchers) {
                if (listener.handler == handler) {
                    return this
                }
                result.add(listener)
            }
            result.add(SingletonDispatch(type, handler, dispatch))
            return CompositeDispatch(type, result)
        }

        override fun addAll(listeners: Collection<T>): BroadcastDispatch<T> {
            val result = mutableListOf<SingletonDispatch<T>>()
            result.addAll(dispatchers)
            for (listener in listeners) {
                val dispatch = SingletonDispatch(
                    type,
                    listener,
                    ReflectionDispatch(listener)
                )
                if (!result.contains(dispatch)) {
                    result.add(dispatch)
                }
            }
            return if (result == dispatchers) {
                this
            } else CompositeDispatch(type, result)
        }

        override fun remove(listener: Any): BroadcastDispatch<T> {
            val result = mutableListOf<SingletonDispatch<T>>()
            var found = false
            for (dispatch in dispatchers) {
                if (dispatch.handler == listener) {
                    found = true
                } else {
                    result.add(dispatch)
                }
            }
            if (!found) {
                return this
            }
            return if (result.size == 1) {
                result.iterator().next()
            } else CompositeDispatch(type, result)
        }

        override fun removeAll(listeners: Collection<*>): BroadcastDispatch<T> {
            val listenerList = listeners.toSet()
            val result = mutableListOf<SingletonDispatch<T>>()
            for (dispatch in dispatchers) {
                if (!listenerList.contains(dispatch.handler)) {
                    result.add(dispatch)
                }
            }
            if (result.size == 0) {
                return EmptyDispatch(type)
            }
            if (result.size == 1) {
                return result.iterator().next()
            }
            return if (result == dispatchers) {
                this
            } else CompositeDispatch(type, result)
        }

        override fun visitListeners(visitor: Action<T>) {
            for (dispatcher in dispatchers) {
                dispatcher.visitListeners(visitor)
            }
        }

        override fun isEmpty(): Boolean {
            return false
        }

        override fun dispatch(message: MethodInvocation) {
            dispatch(message, dispatchers.iterator())
        }
    }

    companion object {
        fun <T : Any> empty(type: Class<T>): BroadcastDispatch<T> {
            return EmptyDispatch(type)
        }
    }
}

inline fun <reified T : Any> BroadcastDispatch.Companion.empty(): BroadcastDispatch<T> {
    return BroadcastDispatch.empty<T>(T::class.java)
}
