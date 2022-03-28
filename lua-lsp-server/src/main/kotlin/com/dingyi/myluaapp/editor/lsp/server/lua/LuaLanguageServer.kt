package com.dingyi.myluaapp.editor.lsp.server.lua

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WorkspaceService
import java.io.File
import java.net.URI
import java.util.concurrent.CompletableFuture
import kotlin.io.path.toPath

/**
 * @author: dingyi
 * @date: 2021/10/5 22:46
 * @description:
 **/
class LuaLanguageServer : LanguageServer, TextDocumentService,WorkspaceService {

    private lateinit var rootPath: File

    override fun initialize(params: InitializeParams): CompletableFuture<InitializeResult> {
        rootPath = URI(params.workspaceFolders[0].uri).toPath().toFile()
        return CompletableFuture.completedFuture(InitializeResult(ServerCapabilities()))
    }

    override fun shutdown(): CompletableFuture<Any> {
        return CompletableFuture.completedFuture(Any())
    }

    override fun exit() {

    }

    override fun getTextDocumentService(): TextDocumentService {
       return this
    }

    override fun getWorkspaceService(): WorkspaceService? {
        return this
    }

    override fun completion(position: CompletionParams): CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>> {
       return CompletableFuture.supplyAsync {
           val list = mutableListOf<CompletionItem>()

           list.add(CompletionItem("Test").apply {
               kind = CompletionItemKind.Text
               detail = "Test"
               insertText = "test"
               insertTextMode = InsertTextMode.AsIs
               insertTextFormat = InsertTextFormat.PlainText
           })

           Either.forLeft(list)
       }
    }

    override fun didOpen(params: DidOpenTextDocumentParams?) {

    }

    override fun didChange(params: DidChangeTextDocumentParams?) {

    }

    override fun didClose(params: DidCloseTextDocumentParams?) {

    }

    override fun didSave(params: DidSaveTextDocumentParams?) {

    }

    override fun didChangeConfiguration(params: DidChangeConfigurationParams?) {
        TODO("Not yet implemented")
    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams?) {
        TODO("Not yet implemented")
    }
}