package com.dingyi.myluaapp.editor.lsp.server.definition

import android.util.Log
import com.dingyi.myluaapp.editor.lsp.client.DefaultLanguageClient
import com.dingyi.myluaapp.editor.lsp.client.ServerMessageHandler
import com.dingyi.myluaapp.editor.lsp.ktx.DiagnosticHandler
import com.dingyi.myluaapp.editor.lsp.server.connect.StreamConnectionProvider
import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.MessageType
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer


interface LanguageServerDefinition {

    val id: String

    fun createConnectionProvider(): StreamConnectionProvider

    fun createLanguageClient(): DefaultLanguageClient {
        return DefaultLanguageClient(this)
    }

    fun getServerInterface(): Class<out LanguageServer?> {
        return LanguageServer::class.java
    }

    fun getServerMessageHandler(): ServerMessageHandler {
        return object : ServerMessageHandler {
            override fun logMessage(message: MessageParams) {
                when (message.type) {
                    MessageType.Error -> {
                        Log.e("LanguageServer", message.toString())
                    }
                    MessageType.Warning -> {
                        Log.w("LanguageServer", message.toString())
                    }
                    MessageType.Info -> {
                        Log.i("LanguageServer", message.toString())
                    }
                    MessageType.Log -> {
                        Log.d("LanguageServer", message.toString())
                    }
                    null -> {}
                }
            }

            override fun showMessage(message: MessageParams) {
                when (message.type) {
                    MessageType.Error -> {
                        Log.e("LanguageServer", message.toString())
                    }
                    MessageType.Warning -> {
                        Log.w("LanguageServer", message.toString())
                    }
                    MessageType.Info -> {
                        Log.i("LanguageServer", message.toString())
                    }
                    MessageType.Log -> {
                        Log.d("LanguageServer", message.toString())
                    }
                    null -> {}
                }
            }
        }
    }

    fun getDiagnosticHandler(): DiagnosticHandler {
        return {}
    }

    fun <S : LanguageServer?> createLauncherBuilder(): Launcher.Builder<S> {
        return Launcher.Builder()
    }

}