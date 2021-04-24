package com.dingyi.MyLuaApp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.MyLuaApp.base.BaseActivity
import java.lang.reflect.Type
import kotlin.properties.Delegates
import kotlin.reflect.KType

class SharedPreferencesUtil(private val activity: AppCompatActivity) {



    constructor(activity: AppCompatActivity, name:String):this(activity) {

        sharedPreferences=activity.getSharedPreferences(name,Context.MODE_PRIVATE)
    }

    private var sharedPreferences by Delegates.notNull<SharedPreferences>()

    fun <T> get(name:String, defaultValue:T): T? {
        if (defaultValue is String) {
            return sharedPreferences.getString(name, defaultValue) as T
        }else if(defaultValue is Int){
            return sharedPreferences.getInt(name,defaultValue as Int) as T
        }
        return null
    }

    fun <T> put(name:String, defaultValue:T) {
        if (defaultValue is String) {
           sharedPreferences.edit().putString(name,defaultValue as String).commit()
        }else if(defaultValue is Int){
            sharedPreferences.edit().putInt(name,defaultValue as Int).commit()
        }
    }


    companion object {
        fun getDefaultSharedPreferencesUtil(activity:Context): SharedPreferencesUtil {
            return SharedPreferencesUtil(activity as AppCompatActivity,"default");
        }
    }

}