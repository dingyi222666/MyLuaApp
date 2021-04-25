package com.dingyi.MyLuaApp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class SharedPreferencesUtil(private val activity: AppCompatActivity) {

    constructor(activity: AppCompatActivity, name:String):this(activity) {

        mSharedPreferences=activity.getSharedPreferences(name,Context.MODE_PRIVATE)
    }

    private var mSharedPreferences by Delegates.notNull<SharedPreferences>()

    fun <T> get(name:String, defaultValue:T): T? {
        if (defaultValue is String) {
            return mSharedPreferences.getString(name, defaultValue) as T
        }else if(defaultValue is Int){
            return mSharedPreferences.getInt(name,defaultValue as Int) as T
        }
        return null
    }

    fun <T> put(name:String, defaultValue:T) {
        if (defaultValue is String) {
           mSharedPreferences.edit().putString(name,defaultValue as String).commit()
        }else if(defaultValue is Int){
            mSharedPreferences.edit().putInt(name,defaultValue as Int).commit()
        }
    }


    companion object {
        fun getDefaultSharedPreferencesUtil(activity:Context): SharedPreferencesUtil {
            return SharedPreferencesUtil(activity as AppCompatActivity,"default");
        }
    }

}