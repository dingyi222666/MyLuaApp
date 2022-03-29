package com.dingyi.myluaapp.editor.lsp.server

import android.os.Process
import android.util.Log
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.editor.lsp.connect.StreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.document.Document
import com.dingyi.myluaapp.editor.lsp.server.definition.LanguageServerDefinition
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.project.Project
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.jsonrpc.MessageConsumer
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.LanguageServer
import java.io.IOException
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class LanguageServerWrapper(
    private val project: Project,
    val serverDefinition: LanguageServerDefinition,
) {


    protected var connectedDocuments: MutableMap<URI, Document>? = null

    private val serverMessageHandler = serverDefinition.getServerMessageHandler()
    protected val initParams = InitializeParams()

    protected var lspStreamProvider: StreamConnectionProvider? = null
    private var launcherFuture: Future<*>? = null
    private var initializeFuture: CompletableFuture<Void>? = null
    private var languageServer: LanguageServer? = null
    private var serverCapabilities: ServerCapabilities? = null


    private fun requireLanguageServer(): LanguageServer {
        checkNotNull(lspStreamProvider) { "Language server is not connected" }
        checkNotNull(languageServer) { "Language server is not initialized" }
        return languageServer.checkNotNull()
    }

    private fun requireInitializeFuture(): CompletableFuture<Void> {
        checkNotNull(initializeFuture) { "Language server is not initialized" }
        return initializeFuture.checkNotNull()
    }

    /**
     * Starts a language server and triggers initialization. If language server is
     * started and active, does nothing. If language server is inactive, restart it.
     *
     * @throws IOException
     */
    @Synchronized
    fun start() {

        val filesToReconnect = mutableMapOf<URI, Document>()

        // If language server is active, do nothing.
        // If language server is inactive, restart it and reconnect all documents.
        if (languageServer != null) {
            if (isActive()) {
                return
            } else {
                for ((key, value) in connectedDocuments ?: mutableMapOf()) {
                    filesToReconnect[key] = value
                }
                stop()
            }
        }
        connectedDocuments = mutableMapOf()
        initializeFuture = initializeFuture ?: CompletableFuture.supplyAsync {
            kotlin.runCatching {
                lspStreamProvider = serverDefinition.createConnectionProvider()
                lspStreamProvider?.start()
                return@supplyAsync null
            }.onFailure {
                Log.e("LanguageServerWrapper", "Failed to start language server", it)
                stop();
                initializeFuture?.completeExceptionally(it);
                return@supplyAsync null
            }
        }.thenApply {
            kotlin.runCatching {
                val client = serverDefinition.createLanguageClient()
                val executorService = Executors.newCachedThreadPool()

                initParams.processId = Process.myPid()


                val rootURI = project.path.toURI()

                initParams.setRootUri(rootURI.toString())
                initParams.setRootPath(rootURI.path)
                initParams.workspaceFolders = listOf(WorkspaceFolder(rootURI.toString()))
                val provider = lspStreamProvider.checkNotNull()
                val launcher: Launcher<LanguageServer> =
                    serverDefinition.createLauncherBuilder<LanguageServer>()
                        .setLocalService(client) //
                        .setRemoteInterface(serverDefinition.getServerInterface()) //
                        .setInput(provider.getInputStream()) //
                        .setOutput(provider.getOutputStream()) //
                        .setExecutorService(executorService) //
                        .wrapMessages { consumer ->
                            MessageConsumer {
                                thread {
                                    consumer.consume(it)
                                    val currentConnectionProvider = lspStreamProvider
                                    if (currentConnectionProvider != null && isActive()) {
                                        currentConnectionProvider.handleMessage(
                                            it,
                                            languageServer,
                                            initParams.rootUri
                                        )
                                    }
                                }
                            }
                        }
                        .create()


                this.languageServer = launcher.remoteProxy;
                client.connect(requireLanguageServer(), this);
                launcherFuture = launcher.startListening();
            }.onFailure {
                Log.e("LanguageServerWrapper", "Failed to start language server", it)
                stop();
                initializeFuture?.completeExceptionally(it);

            }
            return@thenApply null
        }.thenCompose {
            val workspaceClientCapabilities = WorkspaceClientCapabilities()
            workspaceClientCapabilities.applyEdit = false
            workspaceClientCapabilities.executeCommand =
                ExecuteCommandCapabilities(false)
            workspaceClientCapabilities.symbol = SymbolCapabilities(false)
            workspaceClientCapabilities.workspaceFolders = false
            val editCapabilities = WorkspaceEditCapabilities()
            editCapabilities.documentChanges = true
            editCapabilities.resourceOperations = listOf(
                ResourceOperationKind.Create,
                ResourceOperationKind.Delete, ResourceOperationKind.Rename
            )
            editCapabilities.failureHandling = FailureHandlingKind.Undo
            workspaceClientCapabilities.workspaceEdit = editCapabilities
            val textDocumentClientCapabilities = TextDocumentClientCapabilities()
            textDocumentClientCapabilities.codeAction = CodeActionCapabilities(
                CodeActionLiteralSupportCapabilities(
                    CodeActionKindCapabilities(
                        listOf(
                            CodeActionKind.QuickFix,
                            CodeActionKind.Refactor, CodeActionKind.RefactorExtract,
                            CodeActionKind.RefactorInline, CodeActionKind.RefactorRewrite,
                            CodeActionKind.Source, CodeActionKind.SourceOrganizeImports
                        )
                    )
                ),
                true
            )
            textDocumentClientCapabilities.codeLens = CodeLensCapabilities()
            textDocumentClientCapabilities.colorProvider = ColorProviderCapabilities()
            val completionItemCapabilities = CompletionItemCapabilities(true)
            completionItemCapabilities.documentationFormat =
                listOf(MarkupKind.MARKDOWN, MarkupKind.PLAINTEXT)
            textDocumentClientCapabilities.completion =
                CompletionCapabilities(completionItemCapabilities)
            val definitionCapabilities = DefinitionCapabilities()
            definitionCapabilities.linkSupport = true
            textDocumentClientCapabilities.definition = definitionCapabilities
            val typeDefinitionCapabilities = TypeDefinitionCapabilities()
            typeDefinitionCapabilities.linkSupport = true
            textDocumentClientCapabilities.typeDefinition = typeDefinitionCapabilities
            textDocumentClientCapabilities.documentHighlight = DocumentHighlightCapabilities()
            textDocumentClientCapabilities.documentLink = DocumentLinkCapabilities()
            val documentSymbol = DocumentSymbolCapabilities()
            documentSymbol.hierarchicalDocumentSymbolSupport = true
            documentSymbol.symbolKind =
                SymbolKindCapabilities(
                    listOf(
                        SymbolKind.Array,
                        SymbolKind.Boolean,
                        SymbolKind.Class,
                        SymbolKind.Constant,
                        SymbolKind.Constructor,
                        SymbolKind.Enum,
                        SymbolKind.EnumMember,
                        SymbolKind.Event,
                        SymbolKind.Field,
                        SymbolKind.File,
                        SymbolKind.Function,
                        SymbolKind.Interface,
                        SymbolKind.Key,
                        SymbolKind.Method,
                        SymbolKind.Module,
                        SymbolKind.Namespace,
                        SymbolKind.Null,
                        SymbolKind.Number,
                        SymbolKind.Object,
                        SymbolKind.Operator,
                        SymbolKind.Package,
                        SymbolKind.Property,
                        SymbolKind.String,
                        SymbolKind.Struct,
                        SymbolKind.TypeParameter,
                        SymbolKind.Variable
                    )
                )
            textDocumentClientCapabilities.documentSymbol = documentSymbol
            textDocumentClientCapabilities.foldingRange = FoldingRangeCapabilities()
            textDocumentClientCapabilities.formatting =
                FormattingCapabilities(true)
            val hoverCapabilities = HoverCapabilities()
            hoverCapabilities.contentFormat =
                listOf(MarkupKind.MARKDOWN, MarkupKind.PLAINTEXT)
            textDocumentClientCapabilities.hover = hoverCapabilities
            textDocumentClientCapabilities.onTypeFormatting = null
            textDocumentClientCapabilities.rangeFormatting = RangeFormattingCapabilities()
            textDocumentClientCapabilities.references = ReferencesCapabilities()
            textDocumentClientCapabilities.rename = RenameCapabilities()
            textDocumentClientCapabilities.signatureHelp = SignatureHelpCapabilities()
            textDocumentClientCapabilities.synchronization =
                SynchronizationCapabilities(
                    true, true, true
                )
            initParams.capabilities = ClientCapabilities(
                workspaceClientCapabilities,
                textDocumentClientCapabilities,
                null
            )
            initParams.clientName = "MyLuaApp"


            // no then...Async future here as we want this chain of operation to be sequential and
            // "atomic"-ish
            return@thenCompose requireLanguageServer().initialize(initParams)
        }.thenAccept { res ->
            serverCapabilities = res.capabilities
        }.thenAccept {
            requireLanguageServer().initialized(InitializedParams())
        }.thenAccept {
            Log.d("LanguageServer", "Initialized")
        }
    }


    /**
     *
     * @param editor
     * @return null if not connection has happened, a future tracking the connection state otherwise
     * @throws IOException
     */
    fun connect(editor: Editor): CompletableFuture<LanguageServer?>? {
        return connect(editor.getFile().toURI(), editor)
    }

    /**
     * Warning: this is a long running operation
     *
     * @return the server capabilities, or null if initialization job didn't
     * complete
     */

    fun getServerCapabilities(): ServerCapabilities? {
        try {
            getInitializedServer()[10, TimeUnit.SECONDS]
        } catch (e: java.lang.Exception) {
            Log.e("LanguageServerWrapper", "Fail to get server ", e)
        }
        return serverCapabilities
    }

    /**
     * Check whether this LS is suitable for provided project. Starts the LS if not
     * already started.
     *
     * @return whether this language server can operate on the given project
     * @since 0.5
     */
    fun canOperate(project: Project): Boolean {
        return project == this.project
    }

    fun canOperate(editor: Editor): Boolean {
        val documentUri = editor.getFile().toURI()
        if (isConnectedTo(documentUri)) {
            return true
        }
        if (connectedDocuments?.isEmpty() == true) {
            return true
        }
        val file = editor.getFile()
        return (file.exists() && canOperate(editor.getProject()))
    }

    /**
     * checks if the wrapper is already connected to the document at the given uri
     *
     * @noreference test only
     */
    fun isConnectedTo(uri: URI): Boolean {
        return connectedDocuments?.containsKey(uri) ?: false
    }

    /**
     * To make public when we support non IFiles
     *
     * @return null if not connection has happened, a future that completes when file is initialized otherwise
     * @noreference internal so far
     */
    private fun connect(
        uri: URI,
        editor: Editor
    ): CompletableFuture<LanguageServer?>? {
        val connectedDocuments = connectedDocuments.checkNotNull().toMutableMap()
        if (connectedDocuments.containsKey(uri)) {
            return CompletableFuture.completedFuture(languageServer)
        }
        start()

        if (initializeFuture == null) {
            return null
        }

        return requireInitializeFuture().thenComposeAsync {
            synchronized(
                connectedDocuments
            ) {
                if (connectedDocuments.containsKey(uri)) {
                    return@thenComposeAsync CompletableFuture.completedFuture<Any?>(
                        null
                    )
                }
                val syncOptions: Either<TextDocumentSyncKind, TextDocumentSyncOptions>? =
                    if (initializeFuture == null) null else serverCapabilities?.textDocumentSync
                var syncKind: TextDocumentSyncKind? = null
                if (syncOptions != null) {
                    if (syncOptions.isRight) {
                        syncKind = syncOptions.right.change
                    } else if (syncOptions.isLeft) {
                        syncKind = syncOptions.left
                    }
                }

                val document = Document(
                    this,
                    uri,
                    editor,
                    syncKind
                )

                connectedDocuments[uri] = document
                return@thenComposeAsync  CompletableFuture.completedFuture<Any>(null)
            }
            CompletableFuture.completedFuture<Any>(null)
        }.thenApply { languageServer }
    }

    fun disconnect(uri: URI?) {
        val document = connectedDocuments?.remove(uri) ?: return
        document.onEditorClose()
        if (connectedDocuments?.isEmpty() == true) {
            stop()
        }
    }

    fun disconnect(editor: Editor) {
        disconnect(editor.getFile().toURI())
    }


    /**
     * Stops a language server. If language server is stopped, does nothing.
     */
    @Synchronized
    fun stop() {

        if (initializeFuture != null) {
            initializeFuture?.cancel(true);
            initializeFuture = null;
        }

        serverCapabilities = null;


        val serverFuture = launcherFuture
        val provider = lspStreamProvider
        val languageServerInstance = languageServer

        val shutdownKillAndStopFutureAndProvider = Runnable {
            if (languageServerInstance != null) {
                val shutdown =
                    languageServerInstance.shutdown()
                try {
                    shutdown[5, TimeUnit.SECONDS]
                }  catch (ex: Exception) {
                    Log.e("LanguageServerWrapper", "shutdown failed", ex)
                }
            }
            serverFuture?.cancel(true)
            languageServerInstance?.exit()
            provider?.stop()
        }

        CompletableFuture.runAsync(shutdownKillAndStopFutureAndProvider)

        launcherFuture = null
        lspStreamProvider = null

        while (connectedDocuments?.isEmpty() == false) {
            disconnect(connectedDocuments?.keys?.iterator()?.next())
        }
        languageServer = null

    }


    /**
     * @return whether the underlying connection to language server is still active
     */
    fun isActive(): Boolean {
        return launcherFuture?.let {
            it.isDone.not() and it.isCancelled.not()
        } ?: false
    }

    /**
     * Starts the language server and returns a CompletableFuture waiting for the
     * server to be initialized
     */
    fun getInitializedServer(): CompletableFuture<LanguageServer> {
        kotlin.runCatching {
            start()
        }.onFailure {
            serverMessageHandler.logMessage(
                MessageParams(MessageType.Error, it.message)
            )
            //use log.e to println error
            Log.e("LanguageServerWrapper", "Start language server failed", it)
        }
        return initializeFuture?.let {
            if (!it.isDone) {
                it.thenApply { languageServer }
            } else null
        } ?: CompletableFuture.completedFuture(languageServer)

    }

    fun crashed(e: Exception) {
        Log.e("LanguageServerWrapper", "Language server crashed", e)
    }

    fun getVersion(fileUri: URI): Int {
        return connectedDocuments?.get(fileUri)?.version ?: 0
    }


}