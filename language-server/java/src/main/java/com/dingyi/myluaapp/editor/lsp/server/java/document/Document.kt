package com.dingyi.myluaapp.editor.lsp.server.java.document

import org.eclipse.lsp4j.Range

interface Document {

    val uri:String

    fun getText():String


    fun getText(range: Range):String


    fun readText() {
        throw UnsupportedOperationException("not implemented")
    }

    fun setText(text: String)
}