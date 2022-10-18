package com.dingyi.myluaapp.openapi.language

import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.fileTypes.FileType
import com.dingyi.myluaapp.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.util.ArrayUtilRt
import com.intellij.util.ConcurrencyUtil
import com.intellij.util.containers.ContainerUtil
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap


/**
 * The base class for all programming language support implementations.
 * Specific language implementations should inherit from this class
 * and its registered instance wrapped with [LanguageFileType] via `com.intellij.fileType` extension point.
 * There should be exactly one instance of each Language.
 * It is usually created when creating [LanguageFileType] and can be retrieved later with [.findInstance].
 *
 *
 * The language coming from file type can be changed by [com.intellij.psi.LanguageSubstitutor].
 */
abstract class Language : UserDataHolderBase {


    private val myBaseLanguage: Language?
    private val myID: String
    private val myMimeTypes: Array<String>
    private var myDialects = ContainerUtil.createLockFreeCopyOnWriteList<Language>()

    constructor(ID: String) : this(ID, *ArrayUtilRt.EMPTY_STRING_ARRAY)


    constructor(
        ID: String,
        vararg mimeTypes: String
    ) : this(null, ID, *mimeTypes)


    constructor(
        baseLanguage: Language?,
        ID: String,
        vararg mimeTypes: String
    ) {
        /*if (baseLanguage is MetaLanguage) {
            throw ImplementationConflictException(
                ("MetaLanguage cannot be a base language.\n" +
                        "This language: '" + ID + "'\n" +
                        "Base language: '" + baseLanguage.getID()).toString() + "'",
                null, this, baseLanguage
            )
        }*/
        myBaseLanguage = baseLanguage
        myID = ID
        myMimeTypes =
            if (mimeTypes.isEmpty()) ArrayUtilRt.EMPTY_STRING_ARRAY else mimeTypes as Array<String>
        val langClass: Class<out Language> = javaClass
        var prev = ourRegisteredLanguages.putIfAbsent(langClass, this)
        if (prev != null) {
            error(
                "Language of '$langClass' is already registered: $prev"/*,
                null,
                prev,
                this*/
            )
        }
        prev = ourRegisteredIDs.putIfAbsent(ID, this)
        if (prev != null) {
            error(
                "Language with ID '" + ID + "' is already registered: " + prev.javaClass/*,
                null,
                prev,
                this*/
            )
        }
        for (mimeType in mimeTypes) {
            if (mimeType.isEmpty()) {
                continue
            }
            var languagesByMimeType = ourRegisteredMimeTypes.get(mimeType)
            if (languagesByMimeType == null) {
                languagesByMimeType = ConcurrencyUtil.cacheOrGet(
                    ourRegisteredMimeTypes,
                    mimeType,
                    ContainerUtil.createConcurrentList()
                )
            }
            languagesByMimeType.add(this)
        }
        baseLanguage?.myDialects?.add(this)
    }

    open fun unregisterLanguage(pluginDescriptor: PluginDescriptor) {
        //IElementType.unregisterElementTypes(this, pluginDescriptor)
        /* val referenceProvidersRegistry: ReferenceProvidersRegistry =
             ApplicationManager.getApplication().getServiceIfCreated(
                 ReferenceProvidersRegistry::class.java
             )
         if (referenceProvidersRegistry != null) {
             referenceProvidersRegistry.unloadProvidersFor(this)
         }*/
        ourRegisteredLanguages.remove(javaClass)
        ourRegisteredIDs.remove(getID())
        for (mimeType in getMimeTypes()) {
            ourRegisteredMimeTypes.remove(mimeType)
        }
        val baseLanguage = getBaseLanguage()
        if (baseLanguage != null) {
            baseLanguage.unregisterDialect(this)
        }
    }


    open fun unregisterDialect(language: Language) {
        myDialects.remove(language)
    }

    /**
     * @param klass `java.lang.Class` of the particular language. Serves key purpose.
     * @return instance of the `klass` language registered if any.
     */
    open fun <T : Language> findInstance(klass: Class<T>): T {
        return ourRegisteredLanguages.get(klass) as T
    }

    /**
     * @param mimeType of the particular language.
     * @return collection of all languages for the given `mimeType`.
     */

    open fun findInstancesByMimeType(mimeType: String): Collection<Language> {
        val result = ourRegisteredMimeTypes[mimeType]
        return if (result == null) emptyList() else Collections.unmodifiableCollection(result)
    }

    override fun toString(): String {
        return "Language: $myID"
    }

    /**
     * Returns the list of MIME types corresponding to the language. The language MIME type is used for specifying the base language
     * of a JSP page.
     *
     * @return The list of MIME types.
     */
    open fun getMimeTypes(): Array<String> {
        return myMimeTypes
    }

    /**
     * Returns a user-readable name of the language (language names are not localized).
     *
     * @return the name of the language.
     */

    open fun getID(): String {
        return myID
    }


    open fun getAssociatedFileType(): LanguageFileType? {
        return FileTypeRegistry.instance.findFileTypeByLanguage(this)
    }

    open fun findMyFileType(types: Array<FileType>): LanguageFileType? {
        for (fileType in types) {
            if (fileType is LanguageFileType) {
                val languageFileType = fileType as LanguageFileType
                if (languageFileType.getLanguage() === this) {
                    return languageFileType
                }
            }
        }

        return null
    }


    open fun getBaseLanguage(): Language? {
        return myBaseLanguage
    }


    open fun getDisplayName(): String {
        return getID()
    }

    fun `is`(another: Language): Boolean {
        return this === another
    }

    /**
     * @return whether identifiers in this language are case-sensitive. By default, delegates to the base language (if present) or returns false (otherwise).
     */
    open fun isCaseSensitive(): Boolean {
        return myBaseLanguage != null && myBaseLanguage.isCaseSensitive()
    }


    fun isKindOf(another: Language): Boolean {
        var l: Language? = this
        while (l != null) {
            if (l.`is`(another)) return true
            l = l.getBaseLanguage()
        }
        return false
    }

    fun isKindOf(anotherLanguageId: String): Boolean {
        var l:Language? = this
        while (l != null) {
            if (l.getID() == anotherLanguageId) return true
            l = l.getBaseLanguage()
        }
        return false
    }


    open fun getDialects(): List<Language> {
        return myDialects
    }


    open fun findLanguageByID(id: String?): Language? {
        return if (id == null) null else ourRegisteredIDs[id]
    }

    companion object {

        private val ourRegisteredLanguages = ConcurrentHashMap<Class<out Language>, Language>()
        private val ourRegisteredMimeTypes = ConcurrentHashMap<String, MutableList<Language>>()
        private val ourRegisteredIDs = ConcurrentHashMap<String, Language>()


        /**
         * @return collection of all languages registered so far.
         */

        fun getRegisteredLanguages(): Collection<Language> {
            val languages = ourRegisteredLanguages.values
            return Collections.unmodifiableCollection(languages.toList())
        }

    }
}