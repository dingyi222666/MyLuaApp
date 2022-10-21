package com.dingyi.myluaapp.ide.lexer

internal class LexerPositionImpl(private val myOffset: Int, private val myState: Int) :
    LexerPosition {
    override fun getOffset(): Int {
        return myOffset
    }

    override fun getState(): Int {
        return myState
    }
}
