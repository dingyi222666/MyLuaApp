package com.dingyi.myluaapp.editor.lsp.server.definition

import com.dingyi.myluaapp.editor.lsp.connect.InternalConnectionProvider
import com.dingyi.myluaapp.editor.lsp.connect.StreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.ktx.DiagnosticHandler
import org.eclipse.lsp4j.services.LanguageServer

open class InternalLanguageServerDefinition(
    override val id: String,
    private val server: LanguageServer
) : EmptyLanguageServerDefinition() {

    private var connectionProvider: StreamConnectionProvider? = null

    override fun createConnectionProvider(): StreamConnectionProvider {
        return InternalConnectionProvider(server).apply {
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