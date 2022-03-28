package com.dingyi.myluaapp.editor.lsp.server.definition

import com.dingyi.myluaapp.editor.lsp.connect.InternalConnectionProvider
import com.dingyi.myluaapp.editor.lsp.connect.SocketStreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.connect.StreamConnectionProvider
import org.eclipse.lsp4j.services.LanguageServer

open class SocketLanguageServerDefinition(
    override val id: String,
    private val port: Int,
) : EmptyLanguageServerDefinition() {

    private var connectionProvider: StreamConnectionProvider? = null

    override fun createConnectionProvider(): StreamConnectionProvider {
        return SocketStreamConnectionProvider(port).apply {
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