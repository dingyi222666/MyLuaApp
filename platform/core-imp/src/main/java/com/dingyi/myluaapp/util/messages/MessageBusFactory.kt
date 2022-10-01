package com.dingyi.myluaapp.util.messages

import com.dingyi.myluaapp.openapi.application.ApplicationManager

abstract class MessageBusFactory {

    abstract fun createMessageBus(
        owner: MessageBusOwner,
        parentBus: MessageBus?
    ): MessageBus

    companion object {
        val instance: MessageBusFactory
            get() = ApplicationManager.getIDEApplication()[MessageBusFactory::class.java]

        fun newMessageBus(owner: MessageBusOwner): MessageBus {
            return instance.createMessageBus(owner, null)
        }
    }
}
