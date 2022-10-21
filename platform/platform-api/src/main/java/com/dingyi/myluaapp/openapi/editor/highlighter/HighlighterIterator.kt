package com.dingyi.myluaapp.openapi.editor.highlighter

interface HighlighterIterator {

    fun getTokenStart(): Int

    fun getTokenEnd(): Int

    /**
     * Move iterator to the next token
     */
    fun advance()

    fun getTokenColorType(): Int
}