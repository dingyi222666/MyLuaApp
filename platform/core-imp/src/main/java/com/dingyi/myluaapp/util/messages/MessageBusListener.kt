package com.dingyi.myluaapp.util.messages


interface MessageBusListener<L> {

    fun getTopic(): Topic<L>


    fun getListener(): L
}
