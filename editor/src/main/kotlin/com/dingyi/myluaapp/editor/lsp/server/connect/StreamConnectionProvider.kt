package com.dingyi.myluaapp.editor.lsp.server.connect

import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.lsp4j.services.LanguageServer
import java.io.InputStream
import java.io.OutputStream
import java.net.URI

interface StreamConnectionProvider {

    fun start()

    fun getInputStream(): InputStream

    fun getOutputStream(): OutputStream

    fun stop()
    fun handleMessage(it: Message, languageServer: LanguageServer?, root: String?) {

    }
}