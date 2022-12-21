package com.dingyi.myluaapp.tests.openapi.util.messages

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.util.messages.MessageBus
import com.dingyi.myluaapp.util.messages.MessageBusOwner
import com.dingyi.myluaapp.util.messages.Topic
import com.dingyi.myluaapp.util.messages.imp.MessageBusFactoryImpl
import org.junit.Test


class MessageBusTest {


    val rootMessageBus: MessageBus = MessageBusFactoryImpl.createRootBus(object :
        com.dingyi.myluaapp.util.messages.MessageBusOwner {
        override val isDisposed: Boolean
            get() = false

    })

    fun interface Test1 {
        fun call2(i:Int)
    }

    val topic = Topic.create("Test", getJavaClass<Test1>())



    @Test
    fun testMessageBus() {

        val  callForClient= rootMessageBus.syncPublisher(topic)

        rootMessageBus.connect()
            .subscribe(topic,
                Test1 { println("call for client ${it}") })

        callForClient.call2(11)
    }
}