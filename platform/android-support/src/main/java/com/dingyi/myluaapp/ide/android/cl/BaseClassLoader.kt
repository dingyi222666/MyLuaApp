package com.dingyi.myluaapp.ide.android.cl

import java.net.URL
import java.util.Enumeration

interface BaseClassLoader {
    fun findClass(name: String?): Class<*>?
    fun findResource(name: String?): URL?
    fun findResources(name: String?): Enumeration<URL>?
}