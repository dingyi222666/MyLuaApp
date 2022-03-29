package com.dingyi.myluaapp.editor.lsp.server.definition

import com.dingyi.myluaapp.editor.lsp.connect.SocketStreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.connect.StreamConnectionProvider

open class SocketLanguageServerDefinition(
    override val id: String,
    private val port: Int,
    private val block: () -> Unit
) : EmptyLanguageServerDefinition() {

    private var connectionProvider: StreamConnectionProvider? = null

    override fun createConnectionProvider(): StreamConnectionProvider {
        return SocketStreamConnectionProvider(port,block).apply {
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