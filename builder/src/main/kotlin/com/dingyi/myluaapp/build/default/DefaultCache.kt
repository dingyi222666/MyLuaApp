package com.dingyi.myluaapp.build.default


import com.dingyi.myluaapp.build.api.Cache
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.google.gson.Gson
import java.io.Closeable
import java.io.File

class DefaultCache(
    private val cacheDir: String
):Cache {


    private val map = mutableMapOf<String, Any>()

    private var gson: Gson? = Gson()

    private val cacheDiskMap = mutableMapOf<String, String>()

    //local cache
    override fun <T> getCache(key: String): T {
        return map[key] as T
    }

    override fun putCache(key: String, value: Any) {
        map[key] = value
    }

    override fun saveCacheToDisk(key: String, value: String) {
        cacheDiskMap[key] = value
        saveAllCacheToDisk()
    }


    override fun readCacheFromDisk(key: String): String? {
        val memoryCache = cacheDiskMap[key]

        if (memoryCache != null) {
            return memoryCache
        }

        readAllCacheFromDisk()

        return cacheDiskMap[key]

    }

    private fun readAllCacheFromDisk() {
        val file = File(cacheDir, "cache.json")
        if (!file.isFile) {
            file.parentFile?.mkdirs()
            file.createNewFile()
            file.writeText("{}")
        }
        val readMap = gson?.fromJson(
            file.readText(),
            getJavaClass<Map<String, String>>()
        )

        readMap?.let {
            cacheDiskMap.putAll(it)
        }

    }

    private fun saveAllCacheToDisk() {
        val file = File(cacheDir, "cache.json")
        if (!file.isFile) {
            file.parentFile?.mkdirs()
            file.createNewFile()

        }
        gson?.toJson(cacheDiskMap)?.let { file.writeText(it) }
    }

    override fun close() {
        gson = null
        map.clear()
    }


}