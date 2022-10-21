package com.dingyi.myluaapp.ide.lexer


abstract class LexerBase : Lexer() {
    override fun getCurrentPosition(): LexerPosition {
        val offset = getTokenStart()
        val intState = getState()
        return LexerPositionImpl(offset, intState)
    }

    override fun restore(position: LexerPosition) {
        start(getBufferSequence(), position.offset, getBufferEnd(), position.state)
    }
}
