package com.dingyi.myluaapp.editor.lsp.server.definition

import com.dingyi.myluaapp.editor.lsp.client.DefaultLanguageClient
import com.dingyi.myluaapp.editor.lsp.client.ServerMessageHandler
import com.dingyi.myluaapp.editor.lsp.ktx.DiagnosticHandler
import com.dingyi.myluaapp.editor.lsp.server.connect.StreamConnectionProvider
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer


interface LanguageServerDefinition {

    val id:String

    fun createConnectionProvider(): StreamConnectionProvider

    fun createLanguageClient(): DefaultLanguageClient

    fun getServerInterface(): Class<out LanguageServer?> {
        return LanguageServer::class.java
    }

    fun getServerMessageHandler():ServerMessageHandler

    fun getDiagnosticHandler(): DiagnosticHandler {
        return {}
    }
    fun <S : LanguageServer?> createLauncherBuilder(): Launcher.Builder<S> {
        return Launcher.Builder()
    }

}