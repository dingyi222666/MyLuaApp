package com.dingyi.MyLuaApp.utils

import android.content.Context
import android.content.SharedPreferences
import com.dingyi.MyLuaApp.base.BaseActivity
import java.lang.reflect.Type
import kotlin.reflect.KType

class SharedPreferencesUtil(private val name:String,private val activity: BaseActivity<*>) {

    private var sharedPreferences: SharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE);

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
        fun getDefaultSharedPreferencesUtil(activity:BaseActivity<*>): SharedPreferencesUtil {
            return SharedPreferencesUtil("default",activity);
        }
    }

}