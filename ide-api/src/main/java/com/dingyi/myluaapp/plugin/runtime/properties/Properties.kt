package com.dingyi.myluaapp.plugin.runtime.properties

import com.dingyi.myluaapp.plugin.api.Properties
import com.tencent.mmkv.MMKV

class Properties(
    private val pluginId:String = "default"
):Properties {
    private val mmkv = MMKV.mmkvWithID(pluginId)

    override fun putString(key: String, value: String) {
        mmkv.putString(key, value)

    }

    override fun getString(key: String): String? {
        return mmkv.getString(key, "")
    }
}