package com.dingyi.myluaapp.util.messages

interface MessageBusOwner {
    val isDisposed: Boolean


    fun createListener( descriptor: ListenerDescriptor): Any
}
