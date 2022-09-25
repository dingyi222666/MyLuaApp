package com.dingyi.myluaapp.openapi.fileTypes

import com.intellij.openapi.util.text.Strings


class ExtensionFileNameMatcher(privateExtension: String) : FileNameMatcher {

    private val myDotExtension: String

    private val extension: String = Strings.toLowerCase(privateExtension)

    init {
        myDotExtension = "." + this.extension
        require(!(extension.contains("*") || extension.contains("?"))) { "extension should not contain regexp but got: '$extension'" }
    }

    override fun acceptsCharSequence(fileName: CharSequence): Boolean {
        return Strings.endsWithIgnoreCase(fileName, myDotExtension)
    }


    override fun getPresentableString(): String = "*." + this.extension

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ExtensionFileNameMatcher
        return this.extension == that.extension
    }

    override fun hashCode(): Int {
        return extension.hashCode()
    }

    override fun toString(): String {
        return getPresentableString()
    }
}
