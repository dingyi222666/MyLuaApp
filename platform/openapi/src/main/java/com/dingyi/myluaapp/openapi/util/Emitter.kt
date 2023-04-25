package com.dingyi.myluaapp.openapi.util


class Emitter<T : Any, F> {

    private val eventMap = mutableMapOf<T, MutableList<F>>()

    fun subscribe(t: T, f: F): Disposable {
        if (eventMap.containsKey(t)) {
            eventMap[t]?.add(f)
        } else {
            eventMap[t] = mutableListOf(f)
        }
        return Disposable {
            eventMap[t]?.remove(f)
        }
    }

    fun subscribe(f: F) {
        subscribe(Unit as T, f)
    }

    fun unsubscribe(f:F) {
        eventMap.values.forEach {
            it.remove(f)
        }
    }

    fun unsubscribeAll(t: T) {
        eventMap.remove(t)
    }

    fun emit(type: T, func: (F) -> Unit) {
        eventMap[type]?.forEach {
            func(it)
        }
    }

    fun emit(func: (F) -> Unit) {
        emit(Unit as T, func)
    }




}