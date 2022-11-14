package com.dingyi.myluaapp.ide.plugins.cl

import com.dingyi.myluaapp.ide.android.cl.AndroidClassLoader
import org.jetbrains.annotations.NonNls
import java.util.regex.Pattern

class DslLoaderClassLoader(
    dexPath: String,
    parent: ClassLoader
) : AndroidClassLoader(dexPath, null, null, parent) {


    val allowLoadClasses = mutableListOf(
        "java.lang.",
        "kotlin.",
        "com.dingyi.myluapp.",
        "com.android."
    )

    private fun mustBeLoadedByPlatform(@NonNls className: String): Boolean {
        return if (className.startsWith("java.")) {
            true
        } else className.startsWith("kotlin.") && ((className.startsWith("kotlin.jvm.functions.") ||
                (className.startsWith("kotlin.reflect.") &&
                        className.indexOf('.', 15 /* "kotlin.reflect".length */) < 0)))
        // some commonly used classes from kotlin-runtime must be loaded by the platform classloader. Otherwise if a plugin bundles its own version
        // of kotlin-runtime.jar it won't be possible to call platform's methods with these types in signatures from such a plugin.
        // We assume that these classes don't change between Kotlin versions so it's safe to always load them from platform's kotlin-runtime.
    }


    override fun loadClass(name: String, resolve: Boolean): Class<*>? {


        val allowLoadClass = allowLoadClasses.count {
            it.startsWith(name)
        } > 0

        if (!allowLoadClass) {
            return null
        }

        if (mustBeLoadedByPlatform(name)) {
            return Class.forName(name)
        }

        return super.loadClass(name, resolve)


    }


    fun addAllowLoadClass(className: String) {
        allowLoadClasses.add(className)
    }

}