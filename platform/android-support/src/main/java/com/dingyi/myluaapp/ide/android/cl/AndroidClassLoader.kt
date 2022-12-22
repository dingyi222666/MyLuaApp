package com.dingyi.myluaapp.ide.android.cl

import dalvik.system.DexClassLoader
import java.net.URL
import java.util.Enumeration

open class AndroidClassLoader(
    dexPath: String,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    parent: ClassLoader
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent),BaseClassLoader {

    init {
        getParentClassLoaderAsAndroidClassLoader()?.addClassLoader(this)
    }
    fun getParentClassLoaderAsAndroidClassLoader(): AndroidParentClassLoader? {
        if (parent is AndroidParentClassLoader) {
            return parent as AndroidParentClassLoader
        }
        return null
    }


    public override fun findClass(name: String?): Class<*>? {
        return super.findClass(name)
    }
    public override fun findResource(name: String?): URL? {
        return super.findResource(name)
    }

    public override fun findResources(name: String?): Enumeration<URL>? {
        return super.findResources(name)
    }

}