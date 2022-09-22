package com.dingyi.myluaapp.openapi.editor


import java.util.*

interface DocumentListener : EventListener {
    fun beforeContentChanged(event: DocumentEvent) {}
    fun contentChanged(event: DocumentEvent) {}

    companion object {
        val EMPTY_ARRAY = arrayOfNulls<DocumentEvent>(0)
    }
}