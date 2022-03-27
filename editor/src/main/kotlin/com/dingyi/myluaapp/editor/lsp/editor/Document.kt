package com.dingyi.myluaapp.editor.lsp.editor

import com.dingyi.myluaapp.editor.lsp.ktx.didClose
import com.dingyi.myluaapp.editor.lsp.ktx.didOpen
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorListener
import io.github.rosemoe.sora.event.ContentChangeEvent
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DidOpenTextDocumentParams
import org.eclipse.lsp4j.TextDocumentItem
import org.eclipse.lsp4j.TextDocumentSyncKind
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage


class Document(
    private val wrapper: LanguageServerWrapper,
    val uri: URI,
    private var editor: Editor?,
    syncKind: TextDocumentSyncKind? = TextDocumentSyncKind.Full
) : EditorListener {

    private var version = 0
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
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

}