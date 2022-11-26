package com.dingyi.myluaapp.openapi.actions.internal

import com.dingyi.myluaapp.openapi.actions.DataContext
import com.dingyi.myluaapp.openapi.actions.DataKey
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolderBase

class DataContextImpl:DataContext {

    private val userDataHolder = UserDataHolderBase()

    override fun getData(dataId: String): Any? {
        return userDataHolder.getUserData(Key.create(dataId))
    }

    override fun <T> getData(key: DataKey<T>): T? {
        return userDataHolder.getUserData(key)
    }

    override fun <T> putData(key: DataKey<T>, t: T?) {
        userDataHolder.putUserData(key, t)
    }
}