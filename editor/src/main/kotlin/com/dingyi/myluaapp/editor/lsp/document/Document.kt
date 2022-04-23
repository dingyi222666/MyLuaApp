package com.dingyi.myluaapp.editor.lsp.document

import com.dingyi.myluaapp.editor.lsp.ktx.didClose
import com.dingyi.myluaapp.editor.lsp.ktx.didOpen
import com.dingyi.myluaapp.editor.lsp.ktx.didSave
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorListener
import io.github.rosemoe.sora.event.ContentChangeEvent
import org.eclipse.lsp4j.*
import java.net.URI
import java.util.concurrent.CompletableFuture


class Document(
    private val wrapper: LanguageServerWrapper,
    val uri: URI,
    private var editor: Editor?,
    private val syncKind: TextDocumentSyncKind = TextDocumentSyncKind.Full
) : EditorListener {

    var version = 0
    private val changeParams: DidChangeTextDocumentParams? = null
    private val modificationStamp: Long = 0


    init {
        editor?.addEditorListener(this)


    }


    private fun makeFullDidChangeParams(
        currentEditor: Editor,
        event: ContentChangeEvent
    ): DidChangeTextDocumentParams {
        return DidChangeTextDocumentParams(
            VersionedTextDocumentIdentifier(
                uri.toString(),
                ++version
            ),
            mutableListOf(
                TextDocumentContentChangeEvent(
                    currentEditor.getText().toString()
                )
            )
        )
    }

    override fun onEditorChange(currentEditor: Editor, event: ContentChangeEvent) {
        wrapper.getInitializedServer()
            .thenAcceptAsync { ls ->
                when (syncKind) {
                    TextDocumentSyncKind.Full, TextDocumentSyncKind.None -> {
                        ls.textDocumentService.didChange(
                            makeFullDidChangeParams(currentEditor, event)
                        )
                    }
                    TextDocumentSyncKind.Incremental -> {

                        val params = when (event.action) {
                            ContentChangeEvent.ACTION_SET_NEW_TEXT -> makeFullDidChangeParams(
                                currentEditor,
                                event
                            )
                            else -> makeIncrementDidChangeParams(event)
                        }

                        ls.textDocumentService.didChange(
                            params
                        )
                    }
                }
            }
    }

    private fun makeIncrementDidChangeParams(
        event: ContentChangeEvent
    ): DidChangeTextDocumentParams {
        val text = event.changedText.toString()
        return DidChangeTextDocumentParams(
            VersionedTextDocumentIdentifier(
                uri.toString(),
                ++version
            ),
            mutableListOf(
                TextDocumentContentChangeEvent(
                    Range(
                        Position(event.changeStart.line, event.changeStart.column),
                        Position(event.changeEnd.line, event.changeEnd.column)
                    ),
                    if (event.action == ContentChangeEvent.ACTION_INSERT) text.length else -text.length,
                    event.changedText.toString()
                )
            )
        )
    }

    override fun onEditorOpen() {
        wrapper.getInitializedServer()
            .thenAcceptAsync { ls ->
                ls.textDocumentService.didOpen(
                    uri = uri,
                    text = editor?.getText().toString(),
                    version = version,
                    languageId = editor?.let { it.getLanguage().getName() } ?: ""
                )
            }
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