package com.dingyi.myluaapp.ide.completion

import com.dingyi.myluaapp.openapi.progress.ProgressManager


abstract class PrefixMatcher protected constructor(val prefix: String) {


    fun isStartMatch(element: CompletionElement): Boolean {
        return isStartMatch(element.getString())

    }

    open fun isStartMatch(name: String): Boolean {
        return prefixMatches(name)
    }

    abstract fun prefixMatches(name:  CompletionElement): Boolean


    abstract fun prefixMatches(name:  String): Boolean


    abstract fun cloneWithPrefix(prefix: String): PrefixMatcher

    open fun matchingDegree(string: String): Int {
        return 0
    }

    /**
     * Filters _names for strings that match given matcher and sorts them.
     * "Start matching" items go first, then others.
     * Within both groups names are sorted lexicographically in a case-insensitive way.
     */
     fun sortMatching(_names: Collection<String>): LinkedHashSet<String> {
        ProgressManager.checkCanceled()
        if (prefix.isEmpty()) {
            return LinkedHashSet(_names)
        }
        val sorted: MutableList<String> = ArrayList()
        for (name in _names) {
            if (prefixMatches(name)) {
                sorted.add(name)
            }
        }
        ProgressManager.checkCanceled()
        sorted.sortWith(String.CASE_INSENSITIVE_ORDER)
        ProgressManager.checkCanceled()
        val result: LinkedHashSet<String> = LinkedHashSet()
        for (name in sorted) {
            if (isStartMatch(name)) {
                result.add(name)
            }
        }
        ProgressManager.checkCanceled()
        result.addAll(sorted)
        return result
    }

    companion object {
        val ALWAYS_TRUE: PrefixMatcher = PlainPrefixMatcher("")
    }
}
