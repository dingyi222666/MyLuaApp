package com.dingyi.myluaapp.openapi.fileTypes

import com.intellij.openapi.util.NlsSafe


interface FileNameMatcher {

    @Deprecated("use {@link #acceptsCharSequence(CharSequence)} ")
    fun accept(fileName: String): Boolean {
        return acceptsCharSequence(fileName)
    }

    /**
     * This method must be overridden in specific matchers, it's default only for compatibility reasons.
     * @return whether the given file name is accepted by this matcher.
     */
    fun acceptsCharSequence(fileName: CharSequence): Boolean {
        return accept(fileName.toString())
    }


    fun getPresentableString():String
}
