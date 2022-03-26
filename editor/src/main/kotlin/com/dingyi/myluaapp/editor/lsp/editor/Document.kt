package com.dingyi.myluaapp.editor.lsp.editor

import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.editor.EditorListener
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.EventReceiver
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import org.eclipse.lsp4j.DidCloseTextDocumentParams
import org.eclipse.lsp4j.TextDocumentIdentifier
import java.net.URI

class Document(
    private val wrapper: LanguageServerWrapper,
    private val uri: URI,
    editor: Editor
) : EditorListener {

    init {
        editor.addEditorListener(this)
    }

    override fun onEditorChange(currentEditor: Editor, event: ContentChangeEvent) {
        TODO("Not yet implemented")
    }

    override fun onEditorClose() {
        wrapper.getInitializedServer().thenAccept {
            it.textDocumentService.didClose(
                DidCloseTextDocumentParams(
                    TextDocumentIdentifier(
                        uri.toString()
                    )
                )
            )
        }
    }

    override fun onEditorSave() {
        TODO("Not yet implemented")
    }

}