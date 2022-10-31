package com.dingyi.myluaapp.ide.completion

/**
 * Provides completion items.
 *
 *
 * Register via [CompletionContributor.extend].
 *
 * @author peter
 */
abstract class CompletionProvider<V : CompletionParameters> {


    protected abstract fun addCompletions(
        parameters: V,
         result: CompletionResultSet
    )

    fun addCompletionVariants(
        parameters: V,
         result: CompletionResultSet
    ) {
        addCompletions(parameters, result)
    }
}
