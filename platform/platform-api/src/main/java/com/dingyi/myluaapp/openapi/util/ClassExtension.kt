package com.dingyi.myluaapp.openapi.util


import com.dingyi.myluaapp.util.KeyedLazyInstance
import com.intellij.util.containers.ContainerUtil
import java.util.function.Predicate


class ClassExtension<T : Any>(epName: String) :
    KeyedExtensionCollector<T, Class<*>>(epName) {



     override fun keyToString(key: Class<*>): String {
        return key.name
    }

    protected override fun buildExtensions(key: String, classKey: Class<*>): List<T> {
        val allSupers: MutableSet<String> = LinkedHashSet()
        collectSupers(classKey, allSupers)
        return buildExtensionsWithInheritance(allSupers)
    }

    private fun buildExtensionsWithInheritance(supers: Set<String>): List<T> {
        val extensions= extensions
        synchronized(myLock) {
            var result: MutableList<T>? = null
            for (aSuper in supers) {
                result = buildExtensionsFromExplicitRegistration(result,
                    { key: String -> aSuper == key })
            }
            for (aSuper in supers) {
                result = buildExtensionsFromExtensionPoint(
                    result,
                    { bean: KeyedLazyInstance<T> -> aSuper == bean.key },
                    extensions
                )
            }
            return ContainerUtil.notNullize(result)
        }
    }

    fun forClass(t: Class<*>): T? {
        val ts: List<T> = forKey(t)
        return if (ts.isEmpty()) null else ts[0]
    }

    override fun invalidateCacheForExtension(key: String?) {
        clearCache()
    }

    companion object {
        private fun collectSupers(classKey: Class<*>, allSupers: MutableSet<in String>) {
            allSupers.add(classKey.name)
            val interfaces = classKey.interfaces
            for (anInterface in interfaces) {
                collectSupers(anInterface, allSupers)
            }
            val superClass = classKey.superclass
            if (superClass != null) {
                collectSupers(superClass, allSupers)
            }
        }
    }
}