package com.dingyi.myluaapp.openapi.language

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.util.KeyedExtensionCollector
import com.dingyi.myluaapp.util.KeyedLazyInstance
import com.intellij.openapi.util.Key
import com.intellij.util.containers.ContainerUtil

open class LanguageExtension<T : Any> constructor(
    epName: String,
    val defaultImplementation: T?
) : KeyedExtensionCollector<T, Language>(epName) {
    private  /* non static!!! */ val myCacheKey: Key<T>
    private  /* non static!!! */ val myAllCacheKey: Key<List<T>>

    constructor(epName: ExtensionPointName<out KeyedLazyInstance<T>>) : this(
        epName.name,
        null
    ) {
    }


    constructor(
        epName: ExtensionPointName<out KeyedLazyInstance<T>>,
        defaultImplementation: T?
    ) : this(epName.name, defaultImplementation) {
    }

    constructor(epName: String) : this(epName,null)


    init {
        myCacheKey = Key.create("EXTENSIONS_IN_LANGUAGE_$epName")
        myAllCacheKey = Key.create("ALL_EXTENSIONS_IN_LANGUAGE_$epName")
    }


    override fun keyToString(key: Language): String {
        return key.getID()
    }


    fun clearCache(language: Language) {
        clearCacheForDerivedLanguages(language)
        clearCache()
    }

    private fun clearCacheForDerivedLanguages(language: Language) {
        val languages: Set<Language> =
            LanguageUtil.getAllDerivedLanguages(language) // includes language itself
        for (derivedLanguage in languages) {
            clearCacheForLanguage(derivedLanguage)

        }
    }

    private fun clearCacheForLanguage(language: Language) {
        language.putUserData(myCacheKey, null)
        language.putUserData(myAllCacheKey, null)
        super.invalidateCacheForExtension(language.getID())
    }

    override fun invalidateCacheForExtension(key: String?) {
        super.invalidateCacheForExtension(key)
        val language = Language.findLanguageByID(key)
        if (language != null) {
            clearCacheForDerivedLanguages(language)
        }
    }

    fun forLanguage(l: Language): T? {
        val cached = l.getUserData<T>(myCacheKey)
        if (cached != null) return cached
        var result: T? = findForLanguage(l) ?: return null
        result = l.putUserDataIfAbsent(myCacheKey, result as T)
        return result
    }

    protected fun findForLanguage(language: Language?): T? {
        var l = language
        while (l != null) {
            val extensions = forKey(l)
            if (extensions.isNotEmpty()) {
                return extensions[0]
            }
            l = l.getBaseLanguage()
        }
        return defaultImplementation
    }

    /**
     * @see .allForLanguageOrAny
     */

    fun allForLanguage(language: Language): List<T> {
        val cached = language.getUserData(myAllCacheKey)
        if (cached != null) return cached
        val result = collectAllForLanguage(language)
        return language.putUserDataIfAbsent(myAllCacheKey, result)
    }


    private fun collectAllForLanguage(language: Language): List<T> {
        var copyList = true
        var result: MutableList<T>? = null
        var l: Language? = language
        while (l != null) {
            val list = forKey(l).toMutableList()
            if (result == null) {
                result = list
            } else if (list.isNotEmpty()) {
                if (copyList) {
                    result = ArrayList(ContainerUtil.concat(result, list))
                    copyList = false
                } else {
                    result.addAll(list)
                }
            }
            l = l.getBaseLanguage()
        }
        return result.checkNotNull()
    }


    protected override fun buildExtensions(stringKey: String, key: Language): List<T> {
        val allKeys = HashSet<String>()
        allKeys.add(stringKey)
        return buildExtensions(allKeys)
    }


    fun allForLanguageOrAny(l: Language): List<T> {
        val forLanguage = allForLanguage(l)
        return if (l === Language.ANY) forLanguage else ContainerUtil.concat(
            forLanguage, allForLanguage(
                Language.ANY
            )
        )
    }

    override fun addExplicitExtension(key: Language, t: T) {
        clearCacheForLanguage(key)
        super.addExplicitExtension(key, t)
    }

    override fun removeExplicitExtension(key: Language, t: T) {
        clearCacheForLanguage(key)
        super.removeExplicitExtension(key, t)
    }

    protected override fun ensureValuesLoaded() {
        super.ensureValuesLoaded()
    }
}
