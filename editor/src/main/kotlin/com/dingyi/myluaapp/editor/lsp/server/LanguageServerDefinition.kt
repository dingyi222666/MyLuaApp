package com.dingyi.myluaapp.editor.lsp.server

import com.dingyi.myluaapp.editor.lsp.server.connect.StreamConnectionProvider
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer


interface LanguageServerDefinition {

    fun createConnectionProvider(): StreamConnectionProvider

    fun createLanguageClient(): LanguageClient

    fun getServerInterface(): Class<out LanguageServer?> {
        return LanguageServer::class.java
    }

    fun <S : LanguageServer?> createLauncherBuilder(): Launcher.Builder<S>? {
        return Launcher.Builder()
    }

}