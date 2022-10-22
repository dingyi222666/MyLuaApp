package com.dingyi.myluaapp.ide.completion.internal

import com.dingyi.myluaapp.ide.completion.CompletionElement
import com.dingyi.myluaapp.ide.completion.PrefixMatcher


/**
 * @author peter
 */
class CompletionResult protected constructor(
    element: CompletionElement,
    matcher: PrefixMatcher,
) {
    private val myLookupElement: CompletionElement
    val prefixMatcher: PrefixMatcher

    init {
        myLookupElement = element
        prefixMatcher = matcher
    }


    val element: CompletionElement
        get() = myLookupElement


    fun withLookupElement(element: CompletionElement): CompletionResult {
        if (!prefixMatcher.prefixMatches(element)) {
            throw AssertionError("The new element doesn't match the prefix")
        }
        return CompletionResult(element, prefixMatcher)
    }

    val isStartMatch: Boolean
        get() = prefixMatcher.isStartMatch(myLookupElement)

    override fun toString(): String {
        return myLookupElement.toString()
    }

    companion object {
        fun wrap(
           lookupElement: CompletionElement,
          matcher: PrefixMatcher,

        ): CompletionResult? {
            return if (matcher.prefixMatches(lookupElement)) {
                CompletionResult(lookupElement, matcher)
            } else null
        }
    }
}
