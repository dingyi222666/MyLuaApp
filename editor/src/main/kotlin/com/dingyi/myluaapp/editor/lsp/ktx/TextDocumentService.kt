package com.dingyi.myluaapp.editor.lsp.ktx

import org.eclipse.lsp4j.DidCloseTextDocumentParams
import org.eclipse.lsp4j.DidOpenTextDocumentParams
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentItem
import org.eclipse.lsp4j.services.TextDocumentService
import java.net.URI

fun TextDocumentService.didClose(uri: URI) {
    didClose(DidCloseTextDocumentParams(TextDocumentIdentifier(uri.toString())))
}

fun TextDocumentService.didOpen(uri: URI,version:Int,languageId:String,text:String) {
    didOpen(DidOpenTextDocumentParams(TextDocumentItem(uri.toString(),languageId,version,text)))
}