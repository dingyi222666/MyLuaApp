package com.dingyi.myluaapp.ide.android.cl

import dalvik.system.DexClassLoader

open class AndroidClassLoader(
    dexPath: String,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    parent: ClassLoader
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {

    init {

        getParentClassLoaderAsAndroidClassLoader()?.addClassLoader(this)
    }



    fun getParentClassLoaderAsAndroidClassLoader(): AndroidParentClassLoader? {
        if (parent is AndroidParentClassLoader) {
            return parent as AndroidParentClassLoader
        }
        return null
    }

    public override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        var loadedClass = findLoadedClass(name)

        if (loadedClass != null) {
            return loadedClass
        }

        //
        loadedClass = super.findClass(name)


        return loadedClass


    }


}