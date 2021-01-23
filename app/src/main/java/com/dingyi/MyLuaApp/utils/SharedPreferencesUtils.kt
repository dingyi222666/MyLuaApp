package com.dingyi.MyLuaApp.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView


fun get(context: Context,key:String,default: Any): String? {
     val spUtil=context.getSharedPreferences("default",Context.MODE_PRIVATE);
     return spUtil.getString(key,default.toString())
}

fun put(context: Context,key: String,any:Any) {
    val spUtil=context.getSharedPreferences("default",Context.MODE_PRIVATE);
    spUtil.edit().putString(key, any.toString()).apply();
}

fun get(context: Context,spKey:String,key: String,default: Any):String? {
    val spUtil=context.getSharedPreferences(spKey,Context.MODE_PRIVATE);
    return spUtil.getString(key,default.toString())
}

fun put(context: Context,spKey: String,key: String,any: Any) {
    val spUtil=context.getSharedPreferences(spKey,Context.MODE_PRIVATE);
    spUtil.edit().putString(key, any.toString()).apply();
}