package com.dingyi.myluaapp.editor.lsp.server.definition

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.editor.lsp.client.DefaultLanguageClient
import com.dingyi.myluaapp.editor.lsp.client.ServerMessageHandler
import com.dingyi.myluaapp.editor.lsp.ktx.DiagnosticHandler

abstract class EmptyLanguageServerDefinition : LanguageServerDefinition {

    protected var currentDiagnosticHandler: DiagnosticHandler? = null

    override fun getDiagnosticHandler(): DiagnosticHandler {
        currentDiagnosticHandler = currentDiagnosticHandler ?: super.getDiagnosticHandler()
        return currentDiagnosticHandler.checkNotNull()
    }

    protected var currentLanguageClient: DefaultLanguageClient? = null
    override fun createLanguageClient(): DefaultLanguageClient {
        return super.createLanguageClient().apply {
            currentLanguageClient = this
        }
    }

    override fun getLanguageClient() = currentLanguageClient

    protected var currentServerMessageHandler: ServerMessageHandler? = null
    override fun getServerMessageHandler(): ServerMessageHandler {
        currentServerMessageHandler = currentServerMessageHandler ?: super.getServerMessageHandler()
        return currentServerMessageHandler.checkNotNull()
    }
}