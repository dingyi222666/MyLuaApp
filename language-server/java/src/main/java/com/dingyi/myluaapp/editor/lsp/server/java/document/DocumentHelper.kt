package com.dingyi.myluaapp.editor.lsp.server.java.document

import org.eclipse.lsp4j.*
import java.net.URI
import java.util.*

class DocumentHelper {

    private val allDocument = TreeMap<String, Document>()


    operator fun get(uri: String): Document? {
        return allDocument[uri]
    }

    operator fun set(uri: String, document: Document) {
        allDocument[uri] = document
    }

    fun close(uri: String) {
        allDocument.remove(uri)
    }

    fun open(textDocument: TextDocumentItem) {
       set(textDocument.uri, MemoryDocument(textDocument.text))
    }

    fun change(
        textDocument: VersionedTextDocumentIdentifier,
        contentChanges: List<TextDocumentContentChangeEvent>
    ) {
        val document = allDocument[textDocument.uri]
        document?.setText(contentChanges[0].text)
    }

    fun save(params: DidSaveTextDocumentParams) {
        val document = allDocument[params.textDocument.uri]
        document?.setText(params.text)
    }

}