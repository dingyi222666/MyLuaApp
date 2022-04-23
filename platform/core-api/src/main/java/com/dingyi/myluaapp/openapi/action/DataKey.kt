package com.dingyi.myluaapp.openapi.action

import org.jetbrains.kotlin.com.intellij.openapi.util.Key
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


/**
 * Type-safe named key.
 *
 * @param <T> Data type.
 * @see CommonDataKeys
 *

</T> */
class DataKey<T> private constructor(
   val name: String
): Key<T>(name) {

    /**
     * For short notation, use `MY_KEY.is(dataId)` instead of `MY_KEY.getName().equals(dataId)`.
     *
     * @param dataId key name
     * @return `true` if name of DataKey equals to `dataId`, `false` otherwise
     */
    fun `is`(dataId: String): Boolean {
        return name == dataId
    }


    fun getData(dataContext: DataContext): T? {
        return dataContext.getData(name) as T?
    }


    companion object {
        private val ourDataKeyIndex: ConcurrentMap<String, DataKey<*>> = ConcurrentHashMap()

        fun <T> create( name: String): DataKey<T> {
            return ourDataKeyIndex.computeIfAbsent(name) { DataKey<T>(name) } as DataKey<T>
        }


        fun allKeys(): Array<DataKey<*>> {
            return ourDataKeyIndex.values.toTypedArray()
        }
    }
}
