package com.dingyi.myluaapp.openapi.language

import com.dingyi.myluaapp.openapi.fileTypes.FileType
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.util.text.StringUtil
import java.util.function.Predicate


object LanguageUtil {
    val LANGUAGE_COMPARATOR: Comparator<Language> =
        Comparator { o1, o2 ->
            StringUtil.naturalCompare(
                o1.getDisplayName(),
                o2.getDisplayName()
            )
        }



    fun getFileLanguage(file: VirtualFile?): Language? {
        if (file == null) return null

        return  getFileTypeLanguage(file.getFileType())
    }


    fun getFileTypeLanguage(fileType: FileType): Language? {
        return if (fileType is LanguageFileType) fileType.getLanguage() else null
    }


    fun getLanguageFileType(language: Language): FileType? {
        return language.getAssociatedFileType()
    }



    fun getAllDerivedLanguages( base: Language): Set<Language> {
        val result: MutableSet<Language> = HashSet()
        getAllDerivedLanguages(base, result)
        return result
    }

    private fun getAllDerivedLanguages(
         base: Language,
         result: MutableSet<in Language>
    ) {
        result.add(base)
        for (dialect: Language in base.getDialects()) {
            getAllDerivedLanguages(dialect, result)
        }
    }


    fun isFileLanguage(language: Language): Boolean {
        val type: LanguageFileType? = language.getAssociatedFileType()
        return type != null && !StringUtil.isEmpty(type.getDefaultExtension())
    }


    val fileLanguages: List<Language>
        get() {
            return getLanguages { lang ->
                isFileLanguage(
                    lang
                )
            }
        }


    fun getLanguages(filter: Predicate<in Language>): List<Language> {
        //LanguageParserDefinitions.INSTANCE.ensureValuesLoaded()
        val result = ArrayList<Language>()
        for (language in Language.getRegisteredLanguages()) {
            if (filter.test(language)) {
                result.add(language)
            }
        }
        result.sortWith(LANGUAGE_COMPARATOR)
        return result
    }



}
