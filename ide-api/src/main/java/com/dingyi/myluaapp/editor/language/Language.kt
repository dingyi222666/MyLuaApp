package com.dingyi.myluaapp.editor.language

import android.os.Bundle
import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.complete.AutoCompleteProvider
import io.github.rosemoe.sora.lang.EmptyLanguage

import io.github.rosemoe.sora.lang.analysis.AnalyzeManager
import io.github.rosemoe.sora.lang.completion.CompletionPublisher
import io.github.rosemoe.sora.langs.textmate.theme.TextMateColorScheme
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.util.MyCharacter
import kotlinx.coroutines.*

abstract class Language : EmptyLanguage() {

    protected var currentHighlightProvider: HighlightProvider? = null

    protected var currentAutoCompleteProvider: AutoCompleteProvider? = null

    private var superJob: Job = Job()

    protected var coroutine: CoroutineScope = CoroutineScope(Dispatchers.IO + (superJob ?: Job()))

    private var lastAutoCompleteJob:Job = Job()

    abstract fun getName(): String

    abstract fun getHighlightProvider(): HighlightProvider

    abstract fun getAutoCompleteProvider(): AutoCompleteProvider

    final override fun requireAutoComplete(
        content: ContentReference,
        position: CharPosition,
        publisher: CompletionPublisher,
        extraArguments: Bundle
    ) {

        currentAutoCompleteProvider = currentAutoCompleteProvider ?: getAutoCompleteProvider()

        lastAutoCompleteJob.cancel()

        lastAutoCompleteJob = coroutine.launch {
            currentAutoCompleteProvider?.requireAutoComplete(content, position, publisher)
        }
    }

    final override fun getAnalyzeManager(): AnalyzeManager {
        currentHighlightProvider = currentHighlightProvider ?: getHighlightProvider()
        return currentHighlightProvider ?: getHighlightProvider()
    }

    override fun destroy() {
        superJob.cancel()
        coroutine.cancel()

    }


    var tabSize = 4;


}