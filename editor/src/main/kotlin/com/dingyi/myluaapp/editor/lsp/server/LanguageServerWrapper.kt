package com.dingyi.myluaapp.editor.lsp.server

import android.util.Log
import com.dingyi.myluaapp.editor.lsp.client.ServerMessageHandler
import com.dingyi.myluaapp.editor.lsp.editor.Document
import com.dingyi.myluaapp.editor.lsp.server.connect.StreamConnectionProvider
import kotlinx.coroutines.Job
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.MessageType
import org.eclipse.lsp4j.ServerCapabilities
import org.eclipse.lsp4j.services.LanguageServer
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import java.util.function.Function


class LanguageServerWrapper(
    private val projectPath: String,
    private val definition: LanguageServerDefinition,
    private val serverMessageHandler: ServerMessageHandler
) {



    protected var connectedDocuments: Map<URI, Document>? = null


    protected val initParams = InitializeParams()

    protected var lspStreamProvider: StreamConnectionProvider? = null
    private val launcherFuture: Future<*>? = null
    private val initializeFuture: CompletableFuture<Void>? = null
    private val languageServer: LanguageServer? = null
    private val serverCapabilities: ServerCapabilities? = null

    fun start() {}

    /**
     * Starts the language server and returns a CompletableFuture waiting for the
     * server to be initialized. If done in the UI stream, a job will be created
     * displaying that the server is being initialized
     */

    fun getInitializedServer(): CompletableFuture<LanguageServer> {
        kotlin.runCatching {
            start()
        }.onFailure {
            serverMessageHandler.logMessage(
                MessageParams(MessageType.Error,it.message)
            )
            //use log.e to println error
            Log.e("LanguageServerWrapper","Start language server failed",it)
        }
        if (initializeFuture != null && !initializeFuture.isDone) {
            return initializeFuture.thenApply { languageServer }
        }
        return CompletableFuture.completedFuture(languageServer)
    }

    fun crashed(e: Exception) {

    }

    var status: ServerStatus = ServerStatus.STOPPED
        private set
}