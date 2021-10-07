package com.dingyi.lsp.lua.server

import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.InitializeResult
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WorkspaceService
import java.util.concurrent.CompletableFuture

/**
 * @author: dingyi
 * @date: 2021/10/5 22:46
 * @description:
 **/
class LuaLanguageServer: LanguageServer {

    override fun initialize(params: InitializeParams?): CompletableFuture<InitializeResult> {
        TODO("not yet implemented")
    }

    override fun shutdown(): CompletableFuture<Any> {
        TODO("Not yet implemented")
    }

    override fun exit() {
        TODO("Not yet implemented")
    }

    override fun getTextDocumentService(): TextDocumentService {
        TODO("Not yet implemented")
    }

    override fun getWorkspaceService(): WorkspaceService {
        TODO("Not yet implemented")
    }
}