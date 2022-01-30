package com.dingyi.myluaapp.build.default

import com.dingyi.myluaapp.build.api.Cache
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.google.gson.Gson
import java.io.File

class DefaultCache(
    private val cacheDir:String
):Cache {
    private val map = mutableMapOf<String,Any>()

    private val gson = Gson()

    override fun <T> getCache(key: String): T {
        return map[key] as T
    }

    override fun putCache(key: String, value: Any) {
        map[key] = value
    }

    override fun saveCacheToDisk(key: String, value: String) {
        val allCache = readAllCacheFromDisk().toMutableMap()
        allCache[key] = value
        saveAllCacheToDisk(allCache)
    }

    override fun readCacheFromDisk(key: String): String? {
        return readAllCacheFromDisk()[key]
    }

    private fun saveAllCacheToDisk(cache:Map<String,String>) {
        val cacheFile = File(cacheDir,"cache.json")
            .apply {
                if (!exists()) {
                    createNewFile()
                }
            }

        cacheFile.writeText(gson.toJson(cache))

    }

    private fun readAllCacheFromDisk():Map<String,String> {
        val cacheFile = File(cacheDir,"cache.json")
            .apply {
                if (!exists()) {
                    parentFile?.mkdirs()
                    createNewFile()
                    writeText("{}")
                }
            }

        return gson.fromJson(cacheFile.readText(), getJavaClass<Map<String,String>>())
    }

}