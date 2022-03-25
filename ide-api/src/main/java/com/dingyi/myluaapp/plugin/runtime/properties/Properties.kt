package com.dingyi.myluaapp.plugin.runtime.properties

import com.dingyi.myluaapp.plugin.api.Properties
import com.tencent.mmkv.MMKV

class Properties(
    private val pluginId:String = "default"
):Properties {
    private val mmkv = MMKV.mmkvWithID(pluginId)

    override fun putInt(key: String, value: Int) {
        mmkv.encode(key, value)
    }

    override fun getInt(key: String): Int? {
        return mmkv.decodeInt(key)
    }

    override fun putString(key: String, value: String) {
        mmkv.putString(key, value)
    }

    override fun getString(key: String): String? {
        return mmkv.getString(key, "")
    }

    override fun putBoolean(key: String, value: Boolean) {
        mmkv.putBoolean(key, value)
    }

    override fun getBoolean(key: String): Boolean {
        return mmkv.getBoolean(key, false)
    }

    override fun putStringArray(key: String, value: Set<String>) {
        mmkv.encode(key, value)
    }

    override fun getStringArray(key: String): Set<String>? {
        return mmkv.decodeStringSet(key)
    }
}