package com.dingyi.myluaapp.editor.lsp.client.complete

import com.dingyi.myluaapp.editor.complete.AutoCompleteProvider
import com.dingyi.myluaapp.editor.lsp.client.language.LSPLanguage
import com.dingyi.myluaapp.editor.lsp.ktx.completion
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.plugin.api.editor.Editor
import io.github.rosemoe.sora.lang.completion.CompletionItem
import io.github.rosemoe.sora.lang.completion.CompletionPublisher
import io.github.rosemoe.sora.lang.completion.SimpleCompletionItem
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.eclipse.lsp4j.services.LanguageServer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LSPAutoCompleteProvider(
    private val server: LanguageServer,
    private val editor: Editor,
    private val language:LSPLanguage
) : AutoCompleteProvider {


    override suspend fun requireAutoComplete(
        content: ContentReference,
        position: CharPosition,
        publisher: CompletionPublisher
    ): Unit = withContext(Dispatchers.IO) {


        suspendCoroutine<Unit> {
            server.textDocumentService
                .completion(editor.getFile().toURI(), position)
                ?.handle { result, exp ->
                    (result.left ?: result.right?.items)?.forEach {
                        publisher.addItem(SimpleCompletionItem(it.label, 0, it.insertText))
                    }
                    it.resume(Unit)
                    publisher.updateList()
                }


        }
    }
}