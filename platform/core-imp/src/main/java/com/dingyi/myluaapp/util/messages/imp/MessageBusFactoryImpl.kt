package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.util.messages.MessageBus
import com.dingyi.myluaapp.util.messages.MessageBusFactory
import com.dingyi.myluaapp.util.messages.MessageBusOwner

class MessageBusFactoryImpl : MessageBusFactory() {

    override fun createMessageBus(
        owner: MessageBusOwner,
        parentBus: MessageBus?
    ): MessageBus {
        if (parentBus == null) {
            return RootBus(owner)
        }
        val parent = parentBus as CompositeMessageBus
        return if (parent.getParent() == null) {
            CompositeMessageBus(owner, parent)
        } else {
            MessageBusImpl(owner, parent)
        }
    }

    companion object {

        fun createRootBus(owner: MessageBusOwner): RootBus {
            return RootBus(owner)
        }
    }
}
