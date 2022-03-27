package com.dingyi.myluaapp.editor.lsp.server

import android.os.Process
import android.util.Log
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.editor.lsp.editor.Document
import com.dingyi.myluaapp.editor.lsp.ktx.toURI
import com.dingyi.myluaapp.editor.lsp.server.connect.StreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.server.definition.LanguageServerDefinition
import com.dingyi.myluaapp.plugin.api.editor.Editor
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.jsonrpc.MessageConsumer
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.LanguageServer
import java.io.IOException
import java.net.URI
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future


class LanguageServerWrapper(
    private val projectPath: String,
    private val definition: LanguageServerDefinition,

) {


    protected var connectedDocuments: MutableMap<URI, Document>? = null

    private val serverMessageHandler = definition.getServerMessageHandler()
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


        initializeFuture = initializeFuture ?: CompletableFuture.supplyAsync {
            kotlin.runCatching {
                lspStreamProvider = definition.createConnectionProvider()
                lspStreamProvider?.start()
                return@supplyAsync null
            }.onFailure {
                stop();
                initializeFuture?.completeExceptionally(it);
                return@supplyAsync null
            }
        }.thenApply {
            kotlin.runCatching {
                val client = definition.createLanguageClient()
                val executorService = Executors.newCachedThreadPool()

                initParams.processId = Process.myPid()


                val rootURI = projectPath.toURI()

                initParams.setRootUri(rootURI.toString())
                initParams.setRootPath(rootURI.path)
                initParams.setWorkspaceFolders(listOf(WorkspaceFolder(rootURI.toString())))
                val provider = lspStreamProvider.checkNotNull()
                val launcher: Launcher<LanguageServer> =
                    definition.createLauncherBuilder<LanguageServer>()
                        .setLocalService(client) //
                        .setRemoteInterface(definition.getServerInterface()) //
                        .setInput(provider.getInputStream()) //
                        .setOutput(provider.getOutputStream()) //
                        .setExecutorService(executorService) //
                        .wrapMessages { consumer ->
                            MessageConsumer {
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
                        .create()


                this.languageServer = launcher.remoteProxy;
                client.connect(requireLanguageServer(), this);
                launcherFuture = launcher.startListening();
            }.onFailure {
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
            textDocumentClientCapabilities.onTypeFormatting = null // TODO

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
        }.thenRun {
            requireLanguageServer().initialized(InitializedParams())
        }.thenRun {


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
                    if (initializeFuture == null) null else serverCapabilities!!.textDocumentSync
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
                return@thenComposeAsync document.didOpenFuture?.thenApply {}
            }
            CompletableFuture.completedFuture<Any>(null)
        }.thenApply { languageServer }
    }

    fun disconnect(uri: URI?) {
        val document = connectedDocuments?.remove(uri)
        document?.onEditorClose()
        if (connectedDocuments?.isEmpty() == true) {
            stop()
        }
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
    }


    /**
     * @return whether the underlying connection to language server is still active
     */
    fun isActive(): Boolean {
        return launcherFuture?.let {
            it.isDone && it.isCancelled
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

    }

    var status: ServerStatus = ServerStatus.STOPPED
        private set
}