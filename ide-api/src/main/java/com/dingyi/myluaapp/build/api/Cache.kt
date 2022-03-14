package com.dingyi.myluaapp.build.api

import java.io.Closeable

interface Cache : Closeable {

    fun <T> getCache(key: String): T

    fun putCache(key: String, value: Any)


    fun saveCacheToDisk(key: String, value: String)

    fun readCacheFromDisk(key: String): String?

}