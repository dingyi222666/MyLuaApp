package com.dingyi.myluaapp.ide.completion



import com.dingyi.myluaapp.ide.completion.internal.CompletionResult
import com.dingyi.myluaapp.openapi.progress.ProgressManager
import com.intellij.util.Consumer
import org.jetbrains.annotations.Contract



/**
 * [CompletionResultSet]s feed on [CompletionElement]s,
 * match them against specified
 * [PrefixMatcher] and give them to special [Consumer]
 * for further processing, which usually means
 * they will sooner or later appear in completion list. If they don't, there must be some [CompletionContributor]
 * up the invocation stack that filters them out.
 *
 * If you want to change the matching prefix, use [.withPrefixMatcher] or [.withPrefixMatcher]
 * to obtain another [CompletionResultSet] and give your lookup elements to that one.
 *
 * @author peter
 */
abstract class CompletionResultSet protected constructor(
    val prefixMatcher: PrefixMatcher,
    consumer: Consumer<in CompletionResult>,
    contributor: CompletionContributor
) :
    Consumer<CompletionElement> {
    private val myConsumer: Consumer<in CompletionResult>
    protected val myCompletionService: CompletionService = CompletionService.completionService
    protected val myContributor: CompletionContributor
    var isStopped = false
        private set

    init {
        myConsumer = consumer
        myContributor = contributor
    }

    protected val consumer: Consumer<in CompletionResult>
        protected get() = myConsumer

    override fun consume(element: CompletionElement) {
        addElement(element)
    }

    /**
     * If a given element matches the prefix, give it for further processing (which may eventually result in its appearing in the completion list).
     * @see .addAllElements
     */
    abstract fun addElement(element: CompletionElement)

    fun passResult(result: CompletionResult) {
        myConsumer.consume(result)
    }



    /**
     * Adds all elements from the given collection that match the prefix for further processing. The elements are processed in batch,
     * so that they'll appear in lookup all together.
     *
     *
     * This can be useful to ensure predictable order of top suggested elements.
     * Otherwise, when the lookup is shown, most relevant elements processed to that moment are put to the top
     * and remain there even if more relevant elements appear later.
     * These "first" elements may differ from completion invocation to completion invocation due to performance fluctuations,
     * resulting in varying preselected item in completion and worse user experience. Using `addAllElements`
     * instead of [.addElement] helps to avoid that.
     */
    fun addAllElements(elements: Iterable<CompletionElement>) {
        var seldomCounter = 0
        for (element in elements) {
            seldomCounter++
            addElement(element)
            if (seldomCounter % 1000 == 0) {
                ProgressManager.checkCanceled()
            }
        }

    }

    @Contract(pure = true)
    abstract fun withPrefixMatcher(matcher: PrefixMatcher): CompletionResultSet

    /**
     * Creates a default camel-hump prefix matcher based on given prefix
     */
    @Contract(pure = true)
    abstract fun withPrefixMatcher(prefix: String): CompletionResultSet

    abstract fun addLookupAdvertisement(text: String)

    /**
     * @return A result set with the same prefix, but the lookup strings will be matched case-insensitively. Their lookup strings will
     * remain as they are though, so upon insertion the prefix case will be changed.
     */
    @Contract(pure = true)
    abstract fun caseInsensitive(): CompletionResultSet
    fun stopHere() {
        isStopped = true
    }

    fun runRemainingContributors(
        parameters: CompletionParameters,
        passResult: Boolean
    ): LinkedHashSet<CompletionResult> {
        val elements: LinkedHashSet<CompletionResult> = LinkedHashSet<CompletionResult>()
        runRemainingContributors(
           parameters= parameters,
            { result: CompletionResult ->
                 if (passResult) {
                     passResult(result)
                 }
                 elements.add(result)
             })
        return elements
    }

    @JvmOverloads
    fun runRemainingContributors(
        parameters: CompletionParameters,
        consumer: Consumer<in CompletionResult>,
        stop: Boolean = true,
    ) {
        if (stop) {
            stopHere()
        }
        myCompletionService.getVariantsFromContributors(parameters, myContributor,consumer)
    }

    /**
     * Request that the completion contributors be run again when the user changes the prefix so that it becomes equal to the one given.
     */
   /* fun restartCompletionOnPrefixChange(prefix: String) {
        restartCompletionOnPrefixChange(StandardPatterns.string().equalTo(prefix))
    }*/

    /**
     * Request that the completion contributors be run again when the user changes the prefix in a way satisfied by the given condition.
     */
   /* abstract fun restartCompletionOnPrefixChange(prefixCondition: ElementPattern<String?>?)*/

    /**
     * Request that the completion contributors be run again when the user changes the prefix in any way.
     */
   /* fun restartCompletionOnAnyPrefixChange() {
        restartCompletionOnPrefixChange(StandardPatterns.string())
    }
*/
    /**
     * Request that the completion contributors be run again when the user types something into the editor so that no existing lookup elements match that prefix anymore.
     */
    /*abstract fun restartCompletionWhenNothingMatches()*/
}
