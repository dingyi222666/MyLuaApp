package com.dingyi.myluaapp.ide.lexer

import com.dingyi.myluaapp.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil

abstract class Lexer {

    /**
     * Prepare for lexing character data from `buffer` passed. Internal lexer state is supposed to be `initialState`. It is guaranteed
     * that the value of initialState is the same as returned by [.getState] method of this lexer at condition `startOffset=getTokenStart()`.
     * This method is used to incrementally re-lex changed characters using lexing data acquired from this particular lexer sometime in the past.
     *
     * @param buffer       character data for lexing.
     * @param startOffset  offset to start lexing from
     * @param endOffset    offset to stop lexing at
     */
    abstract fun start(
        buffer: CharSequence,
        startOffset: Int,
        endOffset: Int,
        initialState: Int
    )

    private fun startMeasured(
        buffer: CharSequence,
        startOffset: Int,
        endOffset: Int,
        initialState: Int
    ) {
        if (!LOG.isDebugEnabled) {
            start(buffer, startOffset, endOffset, initialState)
            return
        }
        val start = System.currentTimeMillis()
        start(buffer, startOffset, endOffset, initialState)
        val startDuration = System.currentTimeMillis() - start
        if (startDuration > LEXER_START_THRESHOLD) {
            LOG.debug(
                "Starting lexer took: ", startDuration,
                "; at ", startOffset, " - ", endOffset,
                "; text: ", StringUtil.shortenTextWithEllipsis(buffer.toString(), 1024, 500)
            )
        }
    }

    fun start(buf: CharSequence, start: Int, end: Int) {
        startMeasured(buf, start, end, 0)
    }

    fun start(buf: CharSequence) {
        startMeasured(buf, 0, buf.length, 0)
    }


    open fun getTokenSequence(): CharSequence {
        return getBufferSequence().subSequence(getTokenStart(), getTokenEnd())
    }


    /**
     * Returns the current position and state of the lexer.
     *
     * @return the lexer position and state.
     */

    abstract fun getCurrentPosition(): LexerPosition

    open fun getTokenText(): String {
        return getTokenSequence().toString()
    }

    /**
     * Returns the current state of the lexer.
     *
     * @return the lexer state.
     */
    abstract fun getState(): Int

    /**
     * Returns the token at the current position of the lexer or `null` if lexing is finished.
     *
     * @return the current token.
     */

    abstract fun getTokenType(): TokenType

    /**
     * Returns the start offset of the current token.
     *
     * @return the current token start offset.
     */
    abstract fun getTokenStart(): Int

    /**
     * Returns the end offset of the current token.
     *
     * @return the current token end offset.
     */
    abstract fun getTokenEnd(): Int

    /**
     * Advances the lexer to the next token.
     */
    abstract fun advance()


    /**
     * Restores the lexer to the specified state and position.
     *
     * @param position the state and position to restore to.
     */
    abstract fun restore(position: LexerPosition)


    /**
     * Returns the buffer sequence over which the lexer is running. This method should return the
     * same buffer instance which was passed to the `start()` method.
     *
     * @return the lexer buffer.
     */

    abstract fun getBufferSequence(): CharSequence


    /**
     * Returns the offset at which the lexer will stop lexing. This method should return
     * the length of the buffer or the value passed in the `endOffset` parameter
     * to the `start()` method.
     *
     * @return the lexing end offset
     */
    abstract fun getBufferEnd(): Int

    companion object {

        private val LOG = Logger.getInstance(Lexer::class.java)
        private const val LEXER_START_THRESHOLD: Long = 500
    }

}