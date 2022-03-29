package com.dingyi.myluaapp.editor.lsp.server.java.document

import java.io.File

class FileDocument(private val file: File) : MemoryDocument(file.toURI().toString()) {

    override fun getText(): String {
        if (lines.isEmpty()) {
            setText(file.readText())
        }
        return super.getText()
    }

    /**
     * Sync the file with the document.
     */
    fun sync() {
        setText(file.readText())
    }
}