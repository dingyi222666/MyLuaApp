package com.dingyi.myluaapp.ide.completion

import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.language.Language
import com.dingyi.myluaapp.openapi.language.LanguageExtension
import com.dingyi.myluaapp.openapi.language.LanguageFileType
import com.dingyi.myluaapp.openapi.language.LanguageUtil
import com.dingyi.myluaapp.openapi.progress.ProgressManager
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.util.containers.MultiMap
import java.util.function.Predicate

class CompletionContributor {

    private val myMap = MultiMap<Predicate<VirtualFile>, CompletionProvider<CompletionParameters>>()


    /**
     *
     * @return hint text to be shown if no variants are found, typically "No suggestions"
     */
    fun handleEmptyLookup(parameters: CompletionParameters, editor: Editor): String? {
        return null
    }


    /**
     * The main contributor method that is supposed to provide completion variants to result, based on completion parameters.
     * The default implementation looks for [CompletionProvider]s you could register by
     * invoking [.extend] from your contributor constructor,
     * matches the desired completion type and [ElementPattern] with actual ones, and, depending on it, invokes those
     * completion providers.
     *
     *
     *
     * If you want to implement this functionality directly by overriding this method, the following is for you.
     * Always check that parameters match your situation, and that completion type ([CompletionParameters.getCompletionType]
     * is of your favourite kind. This method is run inside a read action. If you do any long activity non-related to PSI in it, please
     * ensure you call [ProgressManager.checkCanceled] often enough so that the completion process
     * can be cancelled smoothly when the user begins to type in the editor.
     */
    fun fillCompletionVariants(
        parameters: CompletionParameters, result: CompletionResultSet
    ) {

        for ((filter, providers) in myMap.entrySet()) {
            ProgressManager.checkCanceled()
            if (filter.test(parameters.file)) {
                for (provider in providers) {
                    provider.addCompletionVariants(parameters, result)
                    if (result.isStopped) {
                        return;
                    }
                }
            }
        }

    }

    fun suggestPrefix(parameters: CompletionParameters): String {
        return ""
    }


    fun extend(
        place: Predicate<VirtualFile>,
        provider: CompletionProvider<CompletionParameters>
    ) {
        myMap.putValue(place, provider)
    }

    companion object {
        val EP =
            ExtensionPointName<CompletionContributorEP>("com.dingyi.myluaapp.completion.contributor")

        private val INSTANCE: LanguageExtension<CompletionContributor> =
            CompletionExtension(EP.name)


        fun forParameters(parameters: CompletionParameters): List<CompletionContributor> {
            return forLanguage(LanguageUtil.getFileLanguage(parameters.file) ?: Language.ANY)
        }


        fun forLanguage(language: Language): List<CompletionContributor> {
            return INSTANCE.forKey(language)
        }


    }


}