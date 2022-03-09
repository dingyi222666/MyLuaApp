package com.dingyi.myluaapp.plugin.modules.lua.editor.highlight

import com.dingyi.lsp.lua.common.lexer.LuaLexer
import com.dingyi.lsp.lua.common.lexer.LuaTokenTypes
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.editor.highlight.IncrementStateHighlightProvider
import io.github.rosemoe.sora.lang.styling.CodeBlock
import io.github.rosemoe.sora.lang.styling.Span
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

class LuaIncrementHighlightProvider : IncrementStateHighlightProvider<LuaIncrementHighlightProvider.LuaLexerState>() {

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

    override fun tokenizeLine(
        lineString: CharSequence,
        line: Int,
        tokenizeResult: LineTokenizeResult<LuaLexerState>?
    ): LineTokenizeResult<LuaLexerState> {

        val lexer = LuaLexer(lineString)

        val result = mutableListOf<Span>()

        //generate span (for last)

        val currentLexerState = LuaLexerState(
            contentHashCode = lineString.toString().hashCode(),
        line = line)


        val lastLexerState =
            tokenizeResult?.data


        currentLexerState.apply {
            isLongComment = lastLexerState?.isLongComment ?: currentLexerState.isLongComment
            isLongString = lastLexerState?.isLongString ?: currentLexerState.isLongString
            size = lastLexerState?.size ?: currentLexerState.size
        }


        val lexerTextList = mutableListOf<Triple<LuaTokenTypes, Int, CharSequence>>()

        var token: LuaTokenTypes? = lexer.advance()
        var lastTokenTypes: LuaTokenTypes? = null

        val advance = {

            when (token) {
                LuaTokenTypes.WHILE, LuaTokenTypes.WHITE_SPACE -> {}
                else -> lastTokenTypes = token
            }


            token = lexer.advance()
        }

        while (token != null) {

            lexerTextList.add(Triple(token.checkNotNull(), lexer.yycolumn(), lexer.yytext()))

            val column = lexer.yycolumn()


            // ]==] match index 3
            // ]] match index 2
            if ((currentLexerState.isLongComment || currentLexerState.isLongString)
                && token == LuaTokenTypes.RBRACK && lexer.yycolumn() + 1 >= currentLexerState.size
                && lexer.yycolumn() - currentLexerState.size >= 1
            ) {


                val targetChar = lineString.substring(
                    lexer.yycolumn() - currentLexerState.size - 1,
                    lexer.yycolumn() - currentLexerState.size
                )

                if (targetChar == "]") {

                    val matchString =
                        matchCommentEnd(
                            lineString.substring(
                                lexer.yycolumn() - currentLexerState.size - 1,
                                lexer.yycolumn() + 1
                            )
                        )

                    if (matchString.isNotEmpty() && matchString.length - 2 == currentLexerState.size) {

                        if (currentLexerState.isLongComment) {
                            result.add(Span.obtain(column, EditorColorScheme.COMMENT.toLong()))
                        } else if (currentLexerState.isLongString) {
                            result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))
                        }

                        currentLexerState.apply {
                            isLongComment = false
                            isLongString = false
                            size = 0
                        }

                        advance()

                        continue
                    }
                }

            }

            if (currentLexerState.isLongComment) {
                result.add(Span.obtain(column, EditorColorScheme.COMMENT.toLong()))
            } else if (currentLexerState.isLongString) {
                result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))
            }


            if (currentLexerState.isLongString || currentLexerState.isLongComment) {
                advance()
                continue
            }

            when (token) {
                LuaTokenTypes.BAD_CHARACTER -> {

                    if (lastTokenTypes == LuaTokenTypes.BAD_CHARACTER && lexer.yytext() == "[") {
                        val lexerList = lexerTextList
                            .filter { it.first == LuaTokenTypes.BAD_CHARACTER && (it.third == "[" || it.third == "=") }
                            .sortedBy { it.second }

                        val matchString =
                            lexerList.joinToString(separator = "") { it.third }
                        val value = matchCommentStart(matchString)

                        val first = lexerList[0].second
                        val last = lexerList[lexerList.lastIndex].second
                        if (value.isNotEmpty() && first + lexerList.size - 1 == last && last == lexer.yycolumn()) {

                            val deleteContent = lexerList.size
                            val targetSize = result.size - deleteContent
                            for (i in targetSize until result.size) {
                                val originColumn = result[i].column
                                result[i] =
                                    Span.obtain(originColumn, EditorColorScheme.LITERAL.toLong())

                            }
                            currentLexerState.isLongString = true
                            currentLexerState.size = value.length - 2

                            result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))

                        } else {
                            result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                        }

                    } else {
                        result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                    }
                }
                //空白符号
                LuaTokenTypes.WHITE_SPACE, LuaTokenTypes.NEW_LINE -> {
                    result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                }

                //关键字 并且添加区块
                LuaTokenTypes.END, LuaTokenTypes.DO, LuaTokenTypes.CONTINUE, LuaTokenTypes.FUNCTION,
                LuaTokenTypes.IF, LuaTokenTypes.WHILE, LuaTokenTypes.SWITCH, LuaTokenTypes.FOR,
                LuaTokenTypes.LOCAL, LuaTokenTypes.LAMBDA, LuaTokenTypes.WHEN, LuaTokenTypes.NOT,
                LuaTokenTypes.FALSE, LuaTokenTypes.TRUE, LuaTokenTypes.NIL, LuaTokenTypes.AND,
                LuaTokenTypes.OR, LuaTokenTypes.THEN, LuaTokenTypes.ELSE, LuaTokenTypes.ELSEIF,
                LuaTokenTypes.RETURN, LuaTokenTypes.REPEAT, LuaTokenTypes.UNTIL, LuaTokenTypes.CASE,
                LuaTokenTypes.DEFAULT, LuaTokenTypes.DEFER -> {
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
                        // match (]])
                        val matchString = matchCommentEnd(lexer.yytext())
                        if (matchString.isEmpty()) {
                            currentLexerState.isLongComment = true
                        }
                        val value = matchCommentStart(lexer.yytext())
                        currentLexerState.size = value.length - 2
                    }

                }
                //符号
                LuaTokenTypes.LPAREN, LuaTokenTypes.RPAREN, LuaTokenTypes.LBRACK, LuaTokenTypes.RBRACK,
                LuaTokenTypes.COMMA, LuaTokenTypes.DOT, LuaTokenTypes.RCURLY, LuaTokenTypes.LCURLY -> {
                    result.add(Span.obtain(column, EditorColorScheme.OPERATOR.toLong()))
                }
                //数字
                LuaTokenTypes.NUMBER -> {
                    result.add(Span.obtain(column, EditorColorScheme.LITERAL.toLong()))

                }
                //名字
                LuaTokenTypes.NAME -> {
                    if (lastTokenTypes == LuaTokenTypes.FUNCTION) {
                        result.add(Span.obtain(column, EditorColorScheme.FUNCTION_NAME.toLong()))
                    } else {
                        result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                    }
                }
                else -> {
                    result.add(Span.obtain(column, EditorColorScheme.TEXT_NORMAL.toLong()))
                }
            }

            advance()

        }


        //update lexer data

        lexerTextList.clear()

        lexer.yyclose()


        return LineTokenizeResult(currentLexerState, result)
    }

    //e.g: [===[ / [[ / --[===[
    private fun matchCommentStart(string: CharSequence): CharSequence {
        // first char,must be '-' or '['
        val first = string.first()

        var startIndex = 0
        if (first == '-') {
            startIndex += 2
        }
        //e.g: --
        if (string.lastIndex < startIndex || string.isEmpty()) {
            return ""
        }
        //e.g: -[
        if (startIndex == 2 && string.substring(0, startIndex) != "--") {
            return ""
        }
        var index = startIndex

        //increment to get '['

        index++


        // -- ([) (==) ([)
        var target = string.substring(index - 1, index)
        if (target != "[") {
            return ""
        }

        // 加一来获取 =

        index++

        // -- (==) ([)
        while (true) {
            val char = string.substring(index - 1, index)
            if (char == "=") {
                index++
                continue
            } else {
                break
            }
        }

        //increment to get '['


        target = string.substring(index - 1, index)
        // - ([)
        if (target != "[") {
            return ""
        }

        return string.substring(startIndex, index)
    }

    //e.g: ]===] / ]]
    private fun matchCommentEnd(string: CharSequence): CharSequence {
        val size = string.length

        //If last index char not equals ']',will return empty string
        if (string.last() != ']' || string.isEmpty()) {
            return ""
        }

        //index
        var index = size - 1

        while (true) {
            val char = string.substring(index - 1, index)
            if (char == "=") {
                index--
                continue
            } else {
                break
            }
        }


        if (string.substring(index - 1, index) != "]") {
            return ""
        }

        return string.substring(index - 1, size)
    }


    class LuaLexerState(
        val contentHashCode: Int = 0,
        val line: Int,
    ) {
        var isLongComment = false
        var isLongString = false
        var size = 0
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LuaLexerState

            if (contentHashCode != other.contentHashCode) return false
            if (line != other.line) return false
            if (isLongComment != other.isLongComment) return false
            if (isLongString != other.isLongString) return false
            if (size != other.size) return false

            return true
        }

        override fun hashCode(): Int {
            var result = contentHashCode
            result = 31 * result + line
            result = 31 * result + isLongComment.hashCode()
            result = 31 * result + isLongString.hashCode()
            result = 31 * result + size
            return result
        }


    }


}