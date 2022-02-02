package com.dingyi.myluaapp.build.default

import btree4j.BTree
import btree4j.BTreeIndex
import btree4j.Value
import btree4j.utils.lang.StringUtils
import com.dingyi.myluaapp.build.api.Cache
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.google.gson.Gson
import java.io.Closeable
import java.io.File

class DefaultCache(
    cacheDir: String
):Cache {

    private val btree = BTreeIndex(File("$cacheDir/cache.bin"), false)
        .apply {
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                this.create(false)
            }
        }

    private val map = mutableMapOf<String, Any>()

    //local cache
    override fun <T> getCache(key: String): T {
        return map[key] as T
    }

    override fun putCache(key: String, value: Any) {
        map[key] = value
    }

    override fun saveCacheToDisk(key: String, value: String) {
        btree.apply {
            init(false)
        }
            .putValue(Value(key), Value(value))


    }

    override fun readCacheFromDisk(key: String): String? {
        return btree.apply {
            init(false)
        }.getValue(Value(key))?.data?.let {
            StringUtils.toString(it)
        }
    }

    override fun close() {
        btree.flush()
        btree.close()
        map.clear()
    }


}