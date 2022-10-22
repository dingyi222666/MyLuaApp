package com.dingyi.myluaapp.ide.completion.internal


import com.dingyi.myluaapp.ide.completion.CompletionElement
import com.dingyi.myluaapp.ide.completion.CompletionResultSet
import com.dingyi.myluaapp.ide.completion.PrefixMatcher
import com.dingyi.myluaapp.openapi.editor.TextRange
import com.intellij.psi.codeStyle.MinusculeMatcher
import com.intellij.util.containers.FList


/**
 * @author peter
 */
open class BetterPrefixMatcher(private val myOriginal: PrefixMatcher, minMatchingDegree: Int) :
    PrefixMatcher(myOriginal.prefix) {
    private val myHumpMatcher: CamelHumpMatcher? =
        if (myOriginal is CamelHumpMatcher) myOriginal else null
    private val myMinMatchingDegree: Int

    init {
        myMinMatchingDegree = minMatchingDegree
    }


    protected open fun createCopy(original: PrefixMatcher, degree: Int): BetterPrefixMatcher {
        return BetterPrefixMatcher(original, degree)
    }

    override fun prefixMatches(name: CompletionElement): Boolean {
        return myOriginal.prefixMatches(name)
    }

    override fun prefixMatches(name: String): Boolean {
        return prefixMatchesEx(name) == MatchingOutcome.BETTER_MATCH
    }

    protected open fun prefixMatchesEx(name: String): MatchingOutcome {
        return myHumpMatcher?.let { matchOptimized(name, it) } ?: matchGeneric(name)
    }

    private fun matchGeneric(name: String): MatchingOutcome {
        if (!myOriginal.prefixMatches(name)) return MatchingOutcome.NON_MATCH
        if (!myOriginal.isStartMatch(name)) return MatchingOutcome.WORSE_MATCH
        return if (myOriginal.matchingDegree(name) >= myMinMatchingDegree) MatchingOutcome.BETTER_MATCH else MatchingOutcome.WORSE_MATCH
    }

    private fun matchOptimized(name: String, matcher: CamelHumpMatcher): MatchingOutcome {
        val fragments = matcher.matchingFragments(name).map {
            com.intellij.openapi.util.TextRange(it.startOffset, it.endOffset)
        }.let {
            FList.createFromReversed(it)
        }


        if (!MinusculeMatcher.isStartMatch(fragments)) return MatchingOutcome.WORSE_MATCH
        return if (matcher.matchingDegree(
                name,
                fragments.map {
                    TextRange(it.startOffset, it.endOffset)
                }.let {
                    FList.createFromReversed(it)
                }
            ) >= myMinMatchingDegree
        ) MatchingOutcome.BETTER_MATCH else MatchingOutcome.WORSE_MATCH
    }

    protected enum class MatchingOutcome {
        NON_MATCH, WORSE_MATCH, BETTER_MATCH
    }

    override fun isStartMatch(name: String): Boolean {
        return myOriginal.isStartMatch(name)
    }

    override fun matchingDegree(string: String): Int {
        return myOriginal.matchingDegree(string)
    }

    override fun cloneWithPrefix(prefix: String): PrefixMatcher {
        return createCopy(myOriginal.cloneWithPrefix(prefix), myMinMatchingDegree)
    }

    class AutoRestarting private constructor(
        private val myResult: CompletionResultSet,
        original: PrefixMatcher,
        minMatchingDegree: Int
    ) : BetterPrefixMatcher(original, minMatchingDegree) {
        constructor(result: CompletionResultSet) : this(
            result,
            result.prefixMatcher,
            Int.MIN_VALUE
        ) {
        }

        override fun createCopy(original: PrefixMatcher, degree: Int): BetterPrefixMatcher {
            return AutoRestarting(myResult, original, degree)
        }

        override fun prefixMatchesEx(name: String): MatchingOutcome {
            val outcome = super.prefixMatchesEx(name)
            if (outcome == MatchingOutcome.WORSE_MATCH) {
                //myResult.restartCompletionOnAnyPrefixChange()
            }
            return outcome
        }
    }
}