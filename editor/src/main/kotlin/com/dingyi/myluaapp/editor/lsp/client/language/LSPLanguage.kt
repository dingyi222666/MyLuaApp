package com.dingyi.myluaapp.editor.lsp.client.language

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.editor.complete.AutoCompleteProvider
import com.dingyi.myluaapp.editor.highlight.HighlightProvider
import com.dingyi.myluaapp.editor.language.Language
import com.dingyi.myluaapp.editor.lsp.client.complete.LSPAutoCompleteProvider
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.editor.lsp.service.LanguageServiceAccessor
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.runtime.editor.EmptyLanguage
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer

open class LSPLanguage(
    private val wrapper: LanguageServerWrapper,
    private var server: LanguageServer?,
    private var client: LanguageClient?,
    private val wrapperLanguage: Language,
    private var editor: Editor?
) : Language() {
    override fun getName() = "LSPLanguage"

    override fun getHighlightProvider(): HighlightProvider {
        return wrapperLanguage.getHighlightProvider()
    }

    override fun getAutoCompleteProvider(): AutoCompleteProvider {
        return LSPAutoCompleteProvider(server.checkNotNull(), editor.checkNotNull())
    }

    override fun destroy() {

        server?.shutdown()?.complete {
            server?.exit()
            wrapper.disconnect(editor.checkNotNull())
            server = null
        }

        editor = null

    }
}