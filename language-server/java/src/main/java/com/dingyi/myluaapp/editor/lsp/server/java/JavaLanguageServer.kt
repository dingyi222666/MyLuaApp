package com.dingyi.myluaapp.editor.lsp.server.java

import android.util.Log
import com.dingyi.myluaapp.editor.lsp.server.java.document.DocumentHelper
import com.dingyi.myluaapp.editor.lsp.server.java.parser.ParserHelper

import com.dingyi.myluaapp.editor.lsp.server.java.service.WorkspaceService
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.TextDocumentService
import java.io.File
import java.net.URI

import java.util.concurrent.CompletableFuture

class JavaLanguageServer : LanguageServer, LanguageClientAware, TextDocumentService {

    private lateinit var currentClient: LanguageClient
    private var isClose = false;

    private val documentHelper  = DocumentHelper()

    private val parserHelper = ParserHelper()



    override fun initialize(params: InitializeParams): CompletableFuture<InitializeResult> {
        return CompletableFuture.supplyAsync {

            parserHelper.setRootProject(
                File(URI(params.workspaceFolders[0].uri).path)
            )

            InitializeResult(
                ServerCapabilities().apply {
                    textDocumentSync = Either.forRight(TextDocumentSyncOptions().apply {
                        change = TextDocumentSyncKind.Full
                    })
                    completionProvider = CompletionOptions().apply {
                        resolveProvider = true
                        triggerCharacters = listOf(".")
                    }
                    hoverProvider = Either.forLeft(true)
                    documentHighlightProvider = Either.forLeft(true)
                    documentSymbolProvider = Either.forLeft(true)
                    definitionProvider = Either.forLeft(true)
                    referencesProvider = Either.forLeft(true)
                    documentFormattingProvider = Either.forLeft(true)
                    documentRangeFormattingProvider = Either.forLeft(true)
                    renameProvider = Either.forLeft(true)
                }
            )
        }

    }

    override fun shutdown(): CompletableFuture<Any> {
        return CompletableFuture.completedFuture(null)
    }

    override fun exit() {
        isClose = true
    }

    override fun getTextDocumentService(): TextDocumentService {
        return this
    }

    override fun getWorkspaceService(): WorkspaceService {
        return WorkspaceService()
    }

    override fun connect(client: LanguageClient) {
        this.currentClient = client
    }

    fun isClose() = isClose


    override fun didOpen(params: DidOpenTextDocumentParams) {
        documentHelper.open(params.textDocument)
        Log.e("JavaLanguageServer","didOpen $params")
    }

    override fun didChange(params: DidChangeTextDocumentParams) {
        documentHelper.change(params.textDocument,params.contentChanges)
        Log.e("JavaLanguageServer","didChange $params")
    }

    override fun didClose(params: DidCloseTextDocumentParams) {
        documentHelper.close(params.textDocument.uri)
        Log.e("JavaLanguageServer","didClose $params")
    }

    override fun didSave(params: DidSaveTextDocumentParams) {
        documentHelper.save(params)
        Log.e("JavaLanguageServer","didSave $params")
    }

}