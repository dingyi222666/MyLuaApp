package com.dingyi.myluaapp.editor.lsp.client

import org.eclipse.lsp4j.MessageParams

/**
 * Server Message Handler for Language Client
 */
interface ServerMessageHandler {

    fun logMessage(message: MessageParams)

    fun showMessage(params:MessageParams)


}