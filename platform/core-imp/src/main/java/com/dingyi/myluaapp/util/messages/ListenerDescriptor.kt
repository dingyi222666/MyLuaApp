package com.dingyi.myluaapp.util.messages

class ListenerDescriptor(
    listenerClassName: String,
    topicClassName: String
) {

    val listenerClassName: String
    val topicClassName: String

    init {

        this.listenerClassName = listenerClassName
        this.topicClassName = topicClassName

    }
}
