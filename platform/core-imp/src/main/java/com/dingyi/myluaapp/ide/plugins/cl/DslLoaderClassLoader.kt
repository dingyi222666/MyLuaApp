package com.dingyi.myluaapp.ide.plugins.cl

import com.dingyi.myluaapp.ide.android.cl.AndroidClassLoader
import java.util.regex.Pattern

class DslLoaderClassLoader(
    dexPath: String,
    parent: ClassLoader
) : AndroidClassLoader(dexPath, null, null, parent) {


    val allowLoadClasses = mutableListOf(
        "java.lang.*",
        "kotlin.*",
        "com.dingyi.myluapp.*",
        "com.android.*"
    ).map { it.toRegex() }.toMutableList()


    override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        val superClass = super.loadClass(name, resolve)

        val allowLoadClass = allowLoadClasses.count {
            it.matches(name)
        } > 0

        if (!allowLoadClass) {
            return null
        }

        return superClass

    }


    fun addAllowLoadClass(className: String) {
        allowLoadClasses.add(className.toRegex())
    }

}