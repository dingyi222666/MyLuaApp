package com.dingyi.myluaapp.editor.lsp.client

import org.eclipse.lsp4j.MessageActionItem
import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.ShowMessageRequestParams
import org.eclipse.lsp4j.services.LanguageClient
import java.lang.UnsupportedOperationException
import java.util.concurrent.CompletableFuture

class DefaultLanguageClient(
    private val handler: ServerMessageHandler,
    private val diagnosticsHandler:(PublishDiagnosticsParams)->Unit
):LanguageClient {
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
}