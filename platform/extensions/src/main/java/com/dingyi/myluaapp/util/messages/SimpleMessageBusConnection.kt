package com.dingyi.myluaapp.util.messages

interface SimpleMessageBusConnection {
  /**
   * Subscribes given handler to the target endpoint within the current connection.
   *
   * @param topic   target endpoint
   * @param handler target handler to use for incoming messages
   * @param <L>     interface for working with the target topic
   * @throws IllegalStateException if there is already registered handler for the target endpoint within the current connection.
   * Note that previously registered handler is not replaced by the given one then
   * @see MessageBus.syncPublisher
  </L> */
  fun <L : Any> subscribe(topic: Topic<L>, handler: L)

  /**
   * Disconnects current connections from the [message bus][MessageBus] and drops all queued but not dispatched messages (if any)
   */
  fun disconnect()
}