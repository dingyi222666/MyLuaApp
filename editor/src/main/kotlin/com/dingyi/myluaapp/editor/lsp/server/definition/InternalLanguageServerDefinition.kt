package com.dingyi.myluaapp.editor.lsp.server.definition

import com.dingyi.myluaapp.editor.lsp.connect.InternalConnectionProvider
import com.dingyi.myluaapp.editor.lsp.connect.StreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.ktx.DiagnosticHandler
import org.eclipse.lsp4j.services.LanguageServer
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.CompletableFuture

open class InternalLanguageServerDefinition(
    override val id: String,
    private val block:() -> Pair<InputStream, OutputStream>
) : EmptyLanguageServerDefinition() {

    private var connectionProvider: StreamConnectionProvider? = null

    override fun createConnectionProvider(): StreamConnectionProvider {
        return InternalConnectionProvider(block).apply {
            connectionProvider = this
        }
    }

    override fun getConnectionProvider(): StreamConnectionProvider? {
        if (connectionProvider?.isStop() == true) {
            return null
        }
        return connectionProvider
    }


}