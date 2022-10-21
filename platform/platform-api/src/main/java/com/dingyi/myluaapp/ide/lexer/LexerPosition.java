package com.dingyi.myluaapp.ide.lexer;

/**
 * Specifies the state and position of a lexer.
 */

public interface LexerPosition {
    /**
     * Returns the current offset of the lexer.
     * @return the lexer offset
     */
    int getOffset();
    int getState();
}
