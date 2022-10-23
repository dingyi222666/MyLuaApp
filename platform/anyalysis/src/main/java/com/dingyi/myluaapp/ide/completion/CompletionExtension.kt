package com.dingyi.myluaapp.ide.completion

import com.dingyi.myluaapp.openapi.language.Language
import com.dingyi.myluaapp.openapi.language.LanguageExtension



class CompletionExtension<T:Any>(epName: String) : LanguageExtension<T>(epName) {

    override fun buildExtensions(stringKey: String, key: Language): List<T> {
        return buildExtensions(getAllBaseLanguageIdsWithAny(key))
    }

    override fun invalidateCacheForExtension(key: String?) {
        super.invalidateCacheForExtension(key)
        // clear entire cache because, if languages are unloaded, we won't be able to find cache keys for unloaded dialects of
        // given language
        clearCache()
        if ("any" == key) {
            for (language in Language.getRegisteredLanguages()) {
                super.invalidateCacheForExtension(keyToString(language))
            }
        }
    }


    private fun getAllBaseLanguageIdsWithAny( key: Language): Set<String> {
        var key: Language? = key
        val allowed: MutableSet<String> = HashSet()
        while (key != null) {
            allowed.add(keyToString(key))
            /*for (metaLanguage in MetaLanguage.all()) {
                if (metaLanguage.matchesLanguage(key)) allowed.add(metaLanguage.getID())
            }*/
            key = key.getBaseLanguage()
        }
        allowed.add("any")
        return allowed
    }
}
