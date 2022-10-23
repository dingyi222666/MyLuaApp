package com.dingyi.myluaapp.openapi.util


import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.openapi.extensions.ExtensionPoint
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointAndAreaListener
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointPriorityListener
import com.dingyi.myluaapp.openapi.extensions.Extensions
import com.dingyi.myluaapp.openapi.extensions.ExtensionsArea
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.util.KeyedLazyInstance
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer


import com.intellij.util.SmartList
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.annotations.NonNls
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Function
import java.util.function.Predicate


open class KeyedExtensionCollector<T : Any, KeyT:Any>(val name: String) : ModificationTracker {


    protected val myLock: String = "lock for KeyedExtensionCollector $name"

    /** Guarded by [.myLock]  */
    private var myExplicitExtensions = mutableMapOf<String, MutableList<T>>()
    private val myCache: ConcurrentMap<String, List<T>> = ConcurrentHashMap()
    private val myTracker: SimpleModificationTracker = SimpleModificationTracker()
    private val myEpListenerAdded = AtomicBoolean()

    constructor(epName: ExtensionPointName<out KeyedLazyInstance<T>>) : this(epName.name) {}

    fun clearCache() {
        myCache.clear()
        myTracker.incModificationCount()
    }

    private fun addExtensionPointListener(point: ExtensionPoint<KeyedLazyInstance<T>>) {
        if (myEpListenerAdded.compareAndSet(false, true)) {
            point.addExtensionPointListener(MyExtensionPointListener(), false, null)
        }
    }

    protected open fun invalidateCacheForExtension(key: String?) {
        if (key != null) {
            myCache.remove(key)
        }
        myTracker.incModificationCount()
    }

    open fun addExplicitExtension(key: KeyT, t: T) {
        synchronized(myLock) {
            val stringKey = keyToString(key)
            if (myExplicitExtensions === emptyMap<String, List<T>>()) {
                myExplicitExtensions =
                    HashMap()
            }
            val list: MutableList<T> =
                myExplicitExtensions.computeIfAbsent(stringKey,
                    Function<String, MutableList<T>> { _: String? -> SmartList() })
            list.add(t)
            invalidateCacheForExtension(stringKey)
        }
    }

    fun addExplicitExtension(key: KeyT, t: T, parentDisposable: Disposable) {
        addExplicitExtension(key, t)
        Disposer.register(parentDisposable) { removeExplicitExtension(key, t) }
    }

    open fun removeExplicitExtension(key: KeyT, t: T) {
        synchronized(myLock) {
            val stringKey = keyToString(key)
            val list = myExplicitExtensions[stringKey]
            if (list != null) {
                list.remove(t)
                if (list.isEmpty()) {
                    myExplicitExtensions.remove(stringKey)
                }
            }
            invalidateCacheForExtension(stringKey)
        }
    }

    protected open fun keyToString(key: KeyT): String {
        return key.toString()
    }

    /**
     * @see findSingle
     */
    fun forKey(key: KeyT): List<T> {
        val stringKey = keyToString(key)
        var cached = myCache[stringKey]
        if (cached != null) {
            return cached
        }
        val list = buildExtensions(stringKey, key)
        // tiny optimisations to save memory
        cached = if (list.isEmpty()) {
            emptyList()
        } else if (list.size == 1) {
            listOf(list[0])
        } else {
            ContainerUtil.immutableList(list)
        }
        val prev = myCache.putIfAbsent(stringKey, cached)
        return prev ?: cached
    }

    fun findSingle(key: KeyT): T? {
        val list = forKey(key)
        return if (list.isEmpty()) null else list[0]
    }

    protected open fun buildExtensions(stringKey: String, key: KeyT): List<T> {
        // compute out of our lock (https://youtrack.jetbrains.com/issue/IDEA-208060)
        val extensions: List<KeyedLazyInstance<T>> = extensions as List<KeyedLazyInstance<T>>
        synchronized(myLock) {
            val list: List<T>? = myExplicitExtensions[stringKey]
            var result: MutableList<T>? =
                if (list != null) ArrayList(list) else null
            result = buildExtensionsFromExtensionPoint(
                result,
                { bean: KeyedLazyInstance<T> -> stringKey == bean.key },
                extensions
            )
            return ContainerUtil.notNullize(result)
        }
    }

    // must be called not under our lock
    protected val extensions: List<KeyedLazyInstance<T>>
        get() {
            val point: ExtensionPoint<KeyedLazyInstance<T>>? = point
            return if (point == null) {
                emptyList()
            } else {
                addExtensionPointListener(point)
                point.extensionList

            }
        }

    fun buildExtensionsFromExtensionPoint(
        result: MutableList<T>?,
        isMyBean: Predicate<in KeyedLazyInstance<T>>,
        extensions: List<KeyedLazyInstance<T>>
    ): MutableList<T> {
        var result = result
        for (bean in extensions) {
            if (!isMyBean.test(bean)) {
                continue
            }
            val instance = instantiate<T>(bean) ?: continue
            if (result == null) {
                result = SmartList()
            }
            result.add(instance)
        }
        return result.checkNotNull()
    }

    protected fun buildExtensions(keys: Set<String>): List<T> {
        val extensions = extensions as List<KeyedLazyInstance<T>>
        synchronized(myLock) {
            var result =
                buildExtensionsFromExplicitRegistration(
                    null
                ) { o: String? ->
                    keys.contains(
                        o
                    )
                }
            result = buildExtensionsFromExtensionPoint(
                result,
                Predicate<KeyedLazyInstance<T>> { bean: KeyedLazyInstance<T> ->
                    keys.contains(
                        bean.key
                    )
                }, extensions
            )
            return ContainerUtil.notNullize(result)
        }
    }

    protected fun buildExtensionsFromExplicitRegistration(
        result: MutableList<T>?,
        isMyBean: Predicate<in String>
    ): MutableList<T> {
        val result: MutableList<T> = result ?: mutableListOf()
        for ((key, list) in myExplicitExtensions) {
            if (isMyBean.test(key)) {
                result.addAll(list)
            }
        }
        return result
    }


    val point: ExtensionPoint<KeyedLazyInstance<T>>?
        get() = Extensions.getRootArea().getExtensionPointIfRegistered(name)

    fun hasAnyExtensions(): Boolean {
        synchronized(myLock) {
            if (!myExplicitExtensions.isEmpty()) {
                return true
            }
            val point: ExtensionPoint<KeyedLazyInstance<T>>? = point
            return point != null && point.size() !== 0
        }
    }

    override fun getModificationCount(): Long {
        return myTracker.getModificationCount()
    }

    protected open fun ensureValuesLoaded() {
        val point: ExtensionPoint<KeyedLazyInstance<T>>? = point
        if (point != null) {
            for (bean in point.extensionList) {
                bean.getInstance()
            }
        }
    }

    private inner class MyExtensionPointListener :
        ExtensionPointAndAreaListener<KeyedLazyInstance<T>>, ExtensionPointPriorityListener {
        override fun extensionAdded(
            bean: KeyedLazyInstance<T>,
            pluginDescriptor: PluginDescriptor
        ) {
            synchronized(myLock) {
                if (bean.key == null) {
                    error(
                        "No key specified for extension of class " + (bean.getInstance() as Any)::class.java
                    )
                }
                invalidateCacheForExtension(bean.key)
            }
        }

        override fun extensionRemoved(
            bean: KeyedLazyInstance<T>,
            pluginDescriptor: PluginDescriptor
        ) {
            synchronized(myLock) { invalidateCacheForExtension(bean.key) }
        }

        override fun areaReplaced(area: ExtensionsArea) {
            myCache.clear()
            myTracker.incModificationCount()
        }
    }

    companion object {
        private val LOG = Logger.getInstance(
            KeyedExtensionCollector::class.java
        )

        fun <T> instantiate(bean: KeyedLazyInstance<T>): T? {
            return try {
                bean.getInstance()
            } catch (e: Exception) {
                LOG.error(e)
                null
            } catch (e: LinkageError) {
                LOG.error(e)
                null
            }
        }
    }
}