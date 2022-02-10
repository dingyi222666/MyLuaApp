package com.dingyi.myluaapp.common.helper

class EventHelper {

    private val allEvent = mutableMapOf<Any,Any>()

    fun <T:Any,R:Any> registerEvent(t:T, r:R) {
        allEvent[t] = r
    }

    fun <T:Any,R:Any> getEvent(t:T):R {
        return allEvent[t] as R
    }


}