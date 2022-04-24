package com.dingyi.myluaapp.build.internal.event


/**
 * Unified manager for all Gradle events.  Provides a simple way to find all listeners of a given type in the
 * system.
 *
 *
 * While the methods work with any Object, in general only interfaces should be used as listener types.
 *
 *
 * Implementations are thread-safe: A listener is notified by at most 1 thread at a time, and so do not need to be thread-safe. All listeners
 * of a given type receive events in the same order. Listeners can be added and removed at any time.
 */
interface ListenerManager {
    /**
     * Adds a listener.  A single object can implement multiple interfaces, and all interfaces are registered by a
     * single invocation of this method.  There is no order dependency: if a broadcaster has already been made for type
     * T, the listener will be registered with it if `(listener instanceof T)` returns true.
     *
     *
     * A listener will be used by a single thread at a time, so the listener implementation does not need to be thread-safe.
     *
     *
     * The listener will not receive events that are currently being broadcast from some other thread.
     *
     * @param listener the listener to add.
     */
    fun addListener(listener: Any)

    /**
     * Removes a listener.  A single object can implement multiple interfaces, and all interfaces are unregistered by a
     * single invocation of this method.  There is no order dependency: if a broadcaster has already been made for type
     * T, the listener will be unregistered with it if `(listener instanceof T)` returns true.
     *
     *
     * When this method returns, the listener will not be in use and will not receive any further events.
     *
     * @param listener the listener to remove.
     */
    fun removeListener(listener: Any)

    /**
     * Allows a client to query if any listeners of a particular type are currently registered.
     * If no, then the broadcaster for this listener type will not forward calls to any listeners.
     *
     *
     * Calling this method will instantiate a broadcaster for the type, if none yet exists.
     *
     * @param listenerClass The type of listener to check.
     * @return True if a listener of the specified type is currently registered, false otherwise.
     */
    fun <T> hasListeners(listenerClass: Class<T>): Boolean

    /**
     * Returns a broadcaster for the given listenerClass. Any method invoked on the broadcaster is forwarded to all registered
     * listeners of the given type. This is done synchronously. Any listener method with a non-void return type will return a null.
     * Exceptions are propagated, and multiple failures are packaged up in a [ListenerNotificationException].
     *
     *
     * A listener is used by a single thread at a time.
     *
     *
     * If there are no registered listeners for the requested type, a broadcaster is returned which does not forward method calls to any listeners.
     * The returned broadcasters are live, that is their list of listeners can be updated by calls to [.addListener] and [ ][.removeListener] after they have been returned.  Broadcasters are also cached, so that repeatedly calling
     * this method with the same listenerClass returns the same broadcaster object.
     *
     * @param listenerClass The type of listener for which to return a broadcaster.
     * @return The broadcaster that forwards method calls to all listeners of the same type that have been (or will be)
     * registered with this manager.
     */
    fun <T> getBroadcaster(listenerClass: Class<T>): T

    /**
     * Returns a broadcaster for the given listenerClass.  The returned broadcaster will delegate to the canonical
     * broadcaster returned by [.getBroadcaster] for the given listener type.  However, it can also have
     * listeners assigned/removed directly to/from it.  This allows these "anonymous" broadcasters to specialize what
     * listeners receive messages.  Each call creates a new broadcaster, so that client code can create as many "facets"
     * of the listener as they need.  The client code must provide some way for its users to register listeners on the
     * specialized broadcasters.
     *
     *
     * The returned value is not thread-safe.
     *
     * @param listenerClass The type of listener for which to create a broadcaster.
     * @return A broadcaster that forwards method calls to all listeners assigned to it, or of the same type that have
     * been (or will be) registered with this manager.
     */
    fun <T:Any> createAnonymousBroadcaster(listenerClass: Class<T>): AnonymousListenerBroadcast<T>

   /**
     * Uses the given object as a logger. Each listener class has exactly one logger associated with it. Any existing
     * logger for the listener class is discarded. Loggers are otherwise treated the same way as listeners.
     *
     * @param logger The new logger to use.
     */
    /*fun useLogger(logger: Any)*/
}