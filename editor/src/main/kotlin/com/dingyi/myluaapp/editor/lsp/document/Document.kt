package com.dingyi.myluaapp.editor.lsp.document

import com.dingyi.myluaapp.editor.lsp.ktx.didClose
import com.dingyi.myluaapp.editor.lsp.ktx.didOpen
import com.dingyi.myluaapp.editor.lsp.ktx.didSave
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorListener
import io.github.rosemoe.sora.event.ContentChangeEvent
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.TextDocumentSyncKind
import java.net.URI
import java.util.concurrent.CompletableFuture


class Document(
    private val wrapper: LanguageServerWrapper,
    val uri: URI,
    private var editor: Editor?,
    syncKind: TextDocumentSyncKind? = TextDocumentSyncKind.Full
) : EditorListener {

    var version = 0
    private val changeParams: DidChangeTextDocumentParams? = null
    private val modificationStamp: Long = 0


    var didOpenFuture: CompletableFuture<Void>? = null

    init {
        editor?.addEditorListener(this)

        didOpenFuture = wrapper.getInitializedServer()
            .thenAcceptAsync { ls ->
                ls.textDocumentService.didOpen(
                    uri = uri,
                    text = editor?.getText().toString(),
                    version = version,
                    languageId  = editor?. let { it.getLanguage().getName() } ?: ""
                )
            }
    }

    override fun onEditorChange(currentEditor: Editor, event: ContentChangeEvent) {

    }

    override fun onEditorClose() {
        if (!wrapper.isActive()) {
            return
        }
        wrapper.getInitializedServer().thenAccept {
            it.textDocumentService.didClose(uri)
        }
        wrapper.disconnect(uri)
        editor = null
    }

    override fun onEditorSave() {
        if (!wrapper.isActive()) {
            return
        }
        wrapper.getInitializedServer().thenAccept {
            editor?.getText()?.let { string ->
                it.textDocumentService.didSave(uri, string.toString())
            }
        }
    }

}