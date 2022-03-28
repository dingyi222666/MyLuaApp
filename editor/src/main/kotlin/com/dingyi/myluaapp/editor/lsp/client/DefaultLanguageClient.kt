package com.dingyi.myluaapp.editor.lsp.client

import com.dingyi.myluaapp.editor.lsp.ktx.DiagnosticHandler
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.editor.lsp.server.definition.LanguageServerDefinition
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer
import java.lang.UnsupportedOperationException
import java.util.concurrent.CompletableFuture

open class DefaultLanguageClient(
    definition: LanguageServerDefinition,
):LanguageClient {

    private lateinit var wrapper: LanguageServerWrapper
    private lateinit var server: LanguageServer
    private val diagnosticsHandler = definition.getDiagnosticHandler()

    private val handler = definition.getServerMessageHandler()

    override fun telemetryEvent(`object`: Any?) {
        throw UnsupportedOperationException("not supported")
    }

    override fun publishDiagnostics(diagnostics: PublishDiagnosticsParams) {
        diagnosticsHandler.invoke(diagnostics)
    }

    override fun showMessage(messageParams: MessageParams) {
        handler.showMessage(messageParams)
    }

    override fun showMessageRequest(requestParams: ShowMessageRequestParams): CompletableFuture<MessageActionItem> {
        throw UnsupportedOperationException("not supported")
    }

    override fun logMessage(message: MessageParams) {
        handler.logMessage(message)
    }


    fun connect(languageServer: LanguageServer, languageServerWrapper: LanguageServerWrapper) {
        this.server = languageServer
        this.wrapper = languageServerWrapper
    }
}