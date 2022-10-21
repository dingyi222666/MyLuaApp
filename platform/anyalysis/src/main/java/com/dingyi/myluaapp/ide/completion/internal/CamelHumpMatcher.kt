package com.dingyi.myluaapp.ide.completion.internal


import com.dingyi.myluaapp.ide.completion.PrefixMatcher
import com.dingyi.myluaapp.openapi.editor.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.codeStyle.MinusculeMatcher
import com.intellij.psi.codeStyle.NameUtil
import com.intellij.util.containers.FList
import com.intellij.util.text.CharArrayUtil


/**
 * @author peter
 */
class CamelHumpMatcher constructor(
    prefix: String,
    private val myCaseSensitive: Boolean,
    val isTypoTolerant: Boolean
) :
    PrefixMatcher(prefix) {
    private val myMatcher: MinusculeMatcher
    private val myCaseInsensitiveMatcher: MinusculeMatcher

    constructor(prefix: String) : this(prefix, true) {}
    constructor(prefix: String, caseSensitive: Boolean) : this(prefix, caseSensitive, false) {}

    init {
        myMatcher = createMatcher(myCaseSensitive)
        myCaseInsensitiveMatcher = createMatcher(false)
    }

    override fun isStartMatch(name: String): Boolean {
        return myMatcher.isStartMatch(name)
    }


    override fun prefixMatches(name: List<String>): Boolean {
        return prefixMatchersInternal(name, true)
    }

    override fun prefixMatches(name: String): Boolean {
        return if (name.startsWith("_") && /*CodeInsightSettings.getInstance()
                .getCompletionCaseSensitive() === CodeInsightSettings.FIRST_LETTER &&*/
            firstLetterCaseDiffers(name)
        ) {
            false
        } else myMatcher.matches(name)
    }

    private fun firstLetterCaseDiffers(name: String): Boolean {
        val nameFirst = skipUnderscores(name)
        val prefixFirst = skipUnderscores(prefix)
        return nameFirst < name.length && prefixFirst < prefix.length &&
                caseDiffers(name[nameFirst], prefix.get(prefixFirst))
    }


    private fun prefixMatchersInternal(
        element: List<String>,
        itemCaseInsensitive: Boolean
    ): Boolean {
        for (name in element) {
            if (itemCaseInsensitive && StringUtil.startsWithIgnoreCase(
                    name,
                    prefix
                ) || prefixMatches(name)
            ) {
                return true
            }
            /* if (itemCaseInsensitive && CodeInsightSettings.ALL !== CodeInsightSettings.getInstance()
                     .getCompletionCaseSensitive()
             ) {
                 if (myCaseInsensitiveMatcher.matches(name)) {
                     return true
                 }
             }*/
        }
        return false
    }

    override fun cloneWithPrefix(prefix: String): PrefixMatcher {
        return if (prefix == this.prefix) {
            this
        } else CamelHumpMatcher(prefix, myCaseSensitive, isTypoTolerant)
    }

    private fun createMatcher(caseSensitive: Boolean): MinusculeMatcher {
        val prefix = applyMiddleMatching(prefix)
        var builder = NameUtil.buildMatcher(prefix)
        if (caseSensitive) {
            /* val setting: Int = CodeInsightSettings.getInstance().getCompletionCaseSensitive()
             if (setting == CodeInsightSettings.FIRST_LETTER) {
                 builder = builder.withCaseSensitivity(NameUtil.MatchingCaseSensitivity.FIRST_LETTER)
             } else if (setting == CodeInsightSettings.ALL) {*/
            builder = builder.withCaseSensitivity(NameUtil.MatchingCaseSensitivity.ALL)
            /*}*/
        }
        if (isTypoTolerant) {
            builder = builder.typoTolerant()
        }
        return builder.build()
    }

    override fun toString(): String {
        return prefix
    }

    override fun matchingDegree(string: String): Int {
        return matchingDegree(string, matchingFragments(string))
    }

    fun matchingFragments(string: String): FList<TextRange> {
        return myMatcher.matchingFragments(string)
            .map {
                TextRange(it.startOffset, it.endOffset)
            }.let {
                FList.createFromReversed(it)
            }
    }

    fun matchingDegree(string: String, fragments: FList<out TextRange>): Int {
        val underscoreEnd = skipUnderscores(string)
        if (underscoreEnd > 0) {
            val ciRanges = myCaseInsensitiveMatcher.matchingFragments(string)
            if (ciRanges != null && !ciRanges.isEmpty()) {
                val matchStart = ciRanges[0].startOffset
                if (matchStart > 0 && matchStart <= underscoreEnd) {
                    return myCaseInsensitiveMatcher.matchingDegree(
                        string.substring(matchStart),
                        true
                    ) - 1
                }
            }
        }
        return myMatcher.matchingDegree(string, true, fragments.map {
            com.intellij.openapi.util.TextRange(it.startOffset, it.endOffset)
        }.let {
            FList.createFromReversed(it)
        })
    }

    companion object {
        private var ourForceStartMatching = false
        private fun skipUnderscores(name: String): Int {
            return CharArrayUtil.shiftForward(name, 0, "_")
        }

        private fun caseDiffers(c1: Char, c2: Char): Boolean {
            return Character.isLowerCase(c1) != Character.isLowerCase(c2) || Character.isUpperCase(
                c1
            ) != Character.isUpperCase(c2)
        }

        fun applyMiddleMatching(prefix: String): String {
            // Default prefix
            return prefix
        }


    }
}
