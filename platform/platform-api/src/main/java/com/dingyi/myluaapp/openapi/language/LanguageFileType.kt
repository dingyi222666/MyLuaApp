package com.dingyi.myluaapp.openapi.language

import com.dingyi.myluaapp.openapi.fileTypes.FileType


/**
 * Kind of file types capable to provide [Language].
 * Note that the associated language can still be overridden by a [com.intellij.psi.LanguageSubstitutor].
 */
abstract class LanguageFileType protected constructor(
    /**
     * Returns the language used in the files of the type.
     * @return The language instance.
     */
    private val language: Language,
) : FileType {

    /**
     * Creates a language file type for the specified language.
     * @param language The language used in the files of the type.
     * @param isSecondary If true, this language file type will never be returned as the associated file type for the language.
     * (Used when a file type is reusing the language of another file type, e.g. XML).
     */
    /**
     * Creates a language file type for the specified language.
     * @param language The language used in the files of the type.
     */
    init {
        // passing Language instead of lazy resolve on getLanguage call (like LazyRunConfigurationProducer), is ok because:
        // 1. Usage of FileType nearly always requires Language
        // 2. FileType is created only on demand (if deprecated FileTypeFactory is not used).
    }

    /**
     * Returns the language used in the files of the type.
     * @return The language instance.
     */

    fun getLanguage(): Language {
        return language
    }

    override fun isBinary(): Boolean {
        return false
    }


    open fun getDisplayName(): String {
        return language.getDisplayName()
    }
}