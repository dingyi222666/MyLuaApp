package com.dingyi.myluaapp.openapi.language

import org.jetbrains.annotations.Contract


abstract class LanguageMatcher internal constructor() {
    abstract fun matchesLanguage(language: Language): Boolean

    companion object {
        /**
         * Given the filter language X returns the matcher which matches language L if one the following is `true`:
         *

         *
         */
        @Contract(pure = true)
        fun match(language: Language): LanguageMatcher {
            return ExactMatcher(language)
        }



        /**
         * Given the filter language X returns the matcher which matches language L if one the following is `true`:
         *
         */
        @Contract(pure = true)
        fun matchWithDialects(language: Language): LanguageMatcher {

            return LanguageKindMatcher(language)

        }
    }
}

internal class ExactMatcher(private val myLanguage: Language) :
    LanguageMatcher() {
    override fun matchesLanguage(language: Language): Boolean {
        return myLanguage.`is`(language)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val matcher = o as ExactMatcher
        return if (myLanguage != matcher.myLanguage) false else true
    }

    override fun hashCode(): Int {
        return myLanguage.hashCode()
    }

    override fun toString(): String {
        return myLanguage.toString()
    }
}

internal class LanguageKindMatcher(private val myLanguage: Language) :
    LanguageMatcher() {
    override fun matchesLanguage(language: Language): Boolean {
        return language.isKindOf(myLanguage)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val matcher = o as LanguageKindMatcher
        return if (myLanguage != matcher.myLanguage) false else true
    }

    override fun hashCode(): Int {
        return myLanguage.hashCode()
    }

    override fun toString(): String {
        return "$myLanguage with dialects"
    }
}
