package com.dingyi.myluaapp.editor.lsp.ktx

import io.github.rosemoe.sora.text.CharPosition
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.services.TextDocumentService
import org.w3c.dom.Text
import java.net.URI

fun TextDocumentService.didClose(uri: URI) {
    didClose(DidCloseTextDocumentParams(TextDocumentIdentifier(uri.toString())))
}

fun TextDocumentService.didOpen(uri: URI, version: Int, languageId: String, text: String) {
    didOpen(DidOpenTextDocumentParams(TextDocumentItem(uri.toString(), languageId, version, text)))
}

fun TextDocumentService.completion(uri: URI, position: CharPosition) {
    completion(
        CompletionParams(
            TextDocumentIdentifier(uri.toString()),
            Position(position.line, position.column)
        )
    )
}