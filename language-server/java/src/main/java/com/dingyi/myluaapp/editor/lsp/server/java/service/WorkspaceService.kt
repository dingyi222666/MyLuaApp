package com.dingyi.myluaapp.editor.lsp.server.java.service

import org.eclipse.lsp4j.DidChangeConfigurationParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.services.WorkspaceService

/**
 * The server done not support workspace service.
 * So give a empty implementation.
 * @author dingyi
 */
class WorkspaceService:WorkspaceService {
    override fun didChangeConfiguration(params: DidChangeConfigurationParams?) {

    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams?) {

    }
}