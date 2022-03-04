package com.dingyi.myluaapp.plugin.modules.lua.editor.highlight

import com.dingyi.lsp.lua.common.lexer.LuaLexer
import com.dingyi.lsp.lua.common.lexer.LuaTokenTypes
import com.dingyi.myluaapp.editor.language.highlight.IncrementLexerHighlightProvider
import io.github.rosemoe.sora.lang.styling.CodeBlock
import io.github.rosemoe.sora.lang.styling.Span
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

class LuaIncrementHighlightProvider : IncrementLexerHighlightProvider() {
    override fun computeBlocks(
        text: CharSequence,
        styles: Styles,
        delegate: Delegate
    ): List<CodeBlock?>? {
        return kotlin.runCatching {

            val lexer = LuaLexer(text)
            val blockStack = ArrayDeque<CodeBlock>()
            val resultBlock = mutableListOf<CodeBlock>()
            var token: LuaTokenTypes? = lexer.advance()
            var maxSwitch = 1
            var currSwitch = 0


            val removeBlock = {
                if (!blockStack.isEmpty()) {
                    val block = blockStack.removeFirst()
                    block.endLine = lexer.yyline()
                    block.endColumn = lexer.yycolumn()
                    if (block.startLine != block.endLine) {//行数不一样在添加区块
                        resultBlock.add(block)
                    }
                }
            }

            val addBlock = {
                val block = styles.obtainNewBlock()
                block.startLine = lexer.yyline()
                block.startColumn = lexer.yycolumn()

                //反正有用 加了吧
                if (blockStack.isEmpty()) {
                    if (currSwitch > maxSwitch) {
                        maxSwitch = currSwitch;
                    }
                    currSwitch = 0;
                }
                currSwitch++;

                blockStack.addFirst(block)
            }

            while (true) {

                if (token == null || delegate.isCancelled()) {
                    break
                }

                when (token) {
                    //关键字 并且添加区块
                    LuaTokenTypes.DO, LuaTokenTypes.FUNCTION, LuaTokenTypes.IF, LuaTokenTypes.WHILE, LuaTokenTypes.SWITCH, LuaTokenTypes.FOR -> {
                        addBlock()
                    }

                    LuaTokenTypes.END -> {
                        removeBlock()
                    }
                    LuaTokenTypes.RCURLY -> {
                        removeBlock()
                    }
                    LuaTokenTypes.LCURLY -> {
                        addBlock()
                    }

                    else -> {}
                }
                token = lexer.advance()

            }

            if (blockStack.isEmpty()) {
                if (currSwitch > maxSwitch) {
                    maxSwitch = currSwitch;
                }
            }

            styles.suppressSwitch = maxSwitch + 10

            lexer.yyclose()

            resultBlock
        }.getOrNull()
    }

    override fun lexerForLine(line: Int, lineString: CharSequence): List<Span> {

        val lexer = LuaLexer(lineString)

        val result = mutableListOf<Span>()

        //generate span (for last)

        val currentLexerState = requireLexerState<LuaLexerState>()
            .getStateForLine(line) ?: LuaLexerState()

        val lastLexerState = requireLexerState<LuaLexerState>()
            .getStateForLine(if (line - 1 < 0) 0 else line - 1)


        currentLexerState.apply {
            isLongComment = lastLexerState?.isLongComment ?: currentLexerState.isLongComment
            isLongString = lastLexerState?.isLongString ?: currentLexerState.isLongString
            value = lastLexerState?.value ?: currentLexerState.value
        }

        var token: LuaTokenTypes? = lexer.advance()


        while (token != null) {

            val column = lexer.yycolumn()

            if (currentLexerState.isLongComment) {
                result.add(Span.obtain(column, EditorColorScheme.COMMENT.toLong()))
            } else if (currentLexerState.isLongString) {
                result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))
            }

            if ((currentLexerState.isLongComment || currentLexerState.isLongString) && token == LuaTokenTypes.RBRACK) {
                val matchString = matchCommentEnd(lineString.substring(column, lineString.length))
                //TODO: compare value
                if (matchString.length >= 2) {
                    currentLexerState.apply {
                        isLongComment = false
                        isLongString = false
                        value = ""
                    }
                }
                token = lexer.advance()
                continue
            }

            if  ((lastLexerState?.isLongString == true && !currentLexerState
                    .isLongString || lastLexerState?.isLongComment == true && !currentLexerState
                    .isLongComment) && token == LuaTokenTypes.RBRACK
            ) {
                if (lastLexerState.isLongComment) {
                    result.add(Span.obtain(column, EditorColorScheme.COMMENT.toLong()))
                } else if (lastLexerState.isLongString) {
                    result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))
                }

                token = lexer.advance()
                continue
            }

            if (currentLexerState.isLongComment || currentLexerState.isLongString) {
                token = lexer.advance()
                continue
            }

            when (token) {

                //空白符号
                LuaTokenTypes.WHITE_SPACE, LuaTokenTypes.NEW_LINE -> {
                    result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                }

                //关键字 并且添加区块
                LuaTokenTypes.DO, LuaTokenTypes.FUNCTION, LuaTokenTypes.IF, LuaTokenTypes.WHILE, LuaTokenTypes.SWITCH, LuaTokenTypes.FOR -> {
                    result.add(Span.obtain(column, EditorColorScheme.KEYWORD.toLong()))
                }

                LuaTokenTypes.END -> {
                    result.add(Span.obtain(column, EditorColorScheme.KEYWORD.toLong()))
                }

                //关键字
                LuaTokenTypes.LOCAL, LuaTokenTypes.LAMBDA, LuaTokenTypes.WHEN, LuaTokenTypes.NOT, LuaTokenTypes.FALSE, LuaTokenTypes.TRUE, LuaTokenTypes.NIL, LuaTokenTypes.AND, LuaTokenTypes.OR, LuaTokenTypes.THEN, LuaTokenTypes.ELSE, LuaTokenTypes.ELSEIF, LuaTokenTypes.RETURN, LuaTokenTypes.REPEAT, LuaTokenTypes.UNTIL,
                LuaTokenTypes.CASE, LuaTokenTypes.DEFAULT, LuaTokenTypes.DEFER -> {

                    result.add(Span.obtain(column, EditorColorScheme.KEYWORD.toLong()))

                }
                //字符串
                LuaTokenTypes.STRING, LuaTokenTypes.LONG_STRING -> {
                    result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))
                }
                //注释
                LuaTokenTypes.SHORT_COMMENT, LuaTokenTypes.BLOCK_COMMENT, LuaTokenTypes.DOC_COMMENT -> {
                    result.add(Span.obtain(column, EditorColorScheme.COMMENT.toLong()))
                    if (token == LuaTokenTypes.BLOCK_COMMENT) {
                        val matchString = matchCommentEnd(lexer.yytext())
                        if (matchString.length < 2) {
                            currentLexerState.isLongComment = true
                        }
                        currentLexerState.value = matchCommentStart(lexer.yytext())
                    }

                }
                //符号
                LuaTokenTypes.LPAREN, LuaTokenTypes.RPAREN, LuaTokenTypes.LBRACK, LuaTokenTypes.RBRACK, LuaTokenTypes.COMMA, LuaTokenTypes.DOT -> {
                    result.add(Span.obtain(column, EditorColorScheme.OPERATOR.toLong()))
                }
                LuaTokenTypes.RCURLY, LuaTokenTypes.LCURLY -> {
                    result.add(Span.obtain(column, EditorColorScheme.OPERATOR.toLong()))
                }
                //数字
                LuaTokenTypes.NUMBER -> {
                    result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))

                }
                //名字
                LuaTokenTypes.NAME -> {
                    result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                }
                else -> {
                    result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                }
            }

            token = lexer.advance()

        }


        //update lexer data


        requireLexerState<LuaLexerState>()
            .updateStateForLine(line, currentLexerState)

        lexer.yyclose()


        return result
    }

    private fun matchCommentStart(string: CharSequence): CharSequence {
        var index = 2
        while (true) {
            val char = string[index]
            if (char == '=') {
                index++
                continue
            } else {
                break
            }
        }
        return string.substring(2, index + 1)
    }

    private fun matchCommentEnd(string: CharSequence): CharSequence {

        val size = string.length
        if (size <= 1 || string.substring(size - 1, size) != "]") {
            return ""
        }

        var index = size - 1
        while (true) {
            val char = string[index]
            if (char == '=') {
                index--
                continue
            } else {
                break
            }
        }
        val last = string[index - 1]
        if (last != ']') {
            return ""
        }
        return string.substring(index - 1, size)
    }


    override fun init() {
        createLexerState<LuaLexerState>()
    }


    class LuaLexerState(
        private var line: Int = 1
    ) {
        var isLongComment = false
        var isLongString = false
        var value: CharSequence = ""
    }


}