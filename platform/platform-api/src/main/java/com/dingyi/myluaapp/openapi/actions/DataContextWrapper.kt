package com.dingyi.myluaapp.openapi.actions

import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolder
import com.intellij.openapi.util.UserDataHolderBase
import org.jetbrains.annotations.NonNls


// We implement UserDataHolder to support DataManager.saveInDataContext/loadFromDataContext methods
abstract class DataContextWrapper(private val myDelegate: DataContext) : DataContext, UserDataHolder {
    private val myDataHolder: UserDataHolder = if (myDelegate is UserDataHolder) myDelegate else UserDataHolderBase()

    override fun getData(@NonNls dataId: String): Any? {
        return myDelegate.getData(dataId)
    }

    override fun <T> getUserData(key: Key<T>): T? {
        return myDataHolder.getUserData(key)
    }

    override fun <T> putUserData(key: Key<T>, value: T?) {
        myDataHolder.putUserData(key, value)
    }

    override fun <T> putData(key: DataKey<T>, t: T?) {
        putUserData(key,t)
    }
}
