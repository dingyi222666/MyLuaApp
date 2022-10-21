package com.dingyi.myluaapp.ide.completion

import com.intellij.openapi.util.text.StringUtil

class PlainPrefixMatcher @JvmOverloads constructor(
    prefix: String,
    private val myPrefixMatchesOnly: Boolean = false
) : PrefixMatcher(prefix) {
    override fun isStartMatch(name: String): Boolean {
        return StringUtil.startsWithIgnoreCase(name, prefix)
    }

    override fun prefixMatches(name: List<String>): Boolean {
        return name.any { prefixMatches(it) }
    }

    override fun prefixMatches(name: String): Boolean {
        return if (myPrefixMatchesOnly) {
            isStartMatch(name)
        } else StringUtil.containsIgnoreCase(name, prefix)
    }


    override fun cloneWithPrefix(prefix: String): PrefixMatcher {
        return PlainPrefixMatcher(prefix, myPrefixMatchesOnly)
    }
}
