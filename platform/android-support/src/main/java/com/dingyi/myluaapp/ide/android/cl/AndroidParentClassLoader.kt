package com.dingyi.myluaapp.ide.android.cl

import java.net.URL
import java.util.Enumeration

open class AndroidParentClassLoader(
    parent: ClassLoader = getSystemClassLoader()
) : ClassLoader(parent) {

    private val allChildClassLoader = mutableListOf<BaseClassLoader>()



    public override fun loadClass(name: String, resolve: Boolean): Class<*> {
        val superClass = kotlin.runCatching { findClass(name) }.getOrNull()
        if (superClass != null) {
            return superClass
        }

        val iterator = allChildClassLoader.iterator()

        while (iterator.hasNext()) {

            val targetClass = kotlin.runCatching {
                iterator.next()
                    .findClass(name)
            }.getOrNull()

            if (targetClass != null) {
                return targetClass
            }

        }

        throw ClassNotFoundException(name)
    }

    override fun findResources(name: String?): Enumeration<URL> {
        val superValue = kotlin.runCatching {
            super.findResources(name)
        }.getOrNull()

        if (superValue != null) {
            return superValue
        }

        val iterator = allChildClassLoader.iterator()

        while (iterator.hasNext()) {

            val targetValue = kotlin.runCatching {
                iterator.next().findResources(name)
            }.getOrNull()

            if (targetValue != null) {
                return targetValue
            }

        }

        // will throw Exception
        return super.findResources(name)
    }



    override fun findResource(name: String?): URL {
        val superValue = kotlin.runCatching {
            super.findResource(name)
        }.getOrNull()

        if (superValue != null) {
            return superValue
        }

        val iterator = allChildClassLoader.iterator()

        while (iterator.hasNext()) {

            val targetValue = kotlin.runCatching {
                iterator.next().findResource(name)
            }.getOrNull()

            if (targetValue != null) {
                return targetValue
            }

        }

        // will throw Exception
        return super.findResource(name)
    }


    fun addClassLoader(androidClassLoader: BaseClassLoader) {
        synchronized(this) {
            allChildClassLoader.add(androidClassLoader)
        }
    }
}