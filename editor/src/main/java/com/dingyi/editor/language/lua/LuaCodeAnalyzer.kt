package com.dingyi.editor.language.lua

import com.dingyi.editor.language.lua.LuaTokenTypes.*
import com.dingyi.editor.scheme.SchemeLua
import io.github.rosemoe.editor.interfaces.CodeAnalyzer
import io.github.rosemoe.editor.struct.BlockLine
import io.github.rosemoe.editor.text.TextAnalyzeResult
import io.github.rosemoe.editor.text.TextAnalyzer
import io.github.rosemoe.editor.widget.EditorColorScheme

class LuaCodeAnalyzer(private val language: LuaLanguage) : CodeAnalyzer {


    override fun analyze(
        content: CharSequence,
        colors: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        val string = content.toString()
        val lexer = LuaLexer(content)
        val blockStack = ArrayDeque<BlockLine>()
        var token: LuaTokenTypes? = lexer.advance()
        var maxSwitch = 1
        var currSwitch = 0

        val infoTable = runCatching {
            language.codeAnalyzer
                .analysis(string)
                .copy()
        }.onFailure {
            it.printStackTrace(System.err)
        }.getOrNull()

        val removeBlock = {
            if (!blockStack.isEmpty()) {
                val block = blockStack.removeFirst()
                block.endLine = lexer.yyline()
                block.endColumn = lexer.yycolumn()
                if (block.startLine != block.endLine) {//行数不一样在添加区块
                    colors.addBlockLine(block)
                }
            }
        }
        val addBlock = {
            val block = colors.obtainNewBlock()
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

            if (token == null || !delegate.shouldAnalyze()) {
                break
            }

            val line = lexer.yyline()
            val column = lexer.yycolumn()


            when (token) {

                //空白符号
                WHITE_SPACE, NEW_LINE -> {
                    colors.addNormalIfNull()
                }

                //关键字 并且添加区块
                DO, FUNCTION, IF, WHILE, SWITCH, FOR -> {
                    addBlock()
                    colors.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                }

                END -> {
                    removeBlock()
                    colors.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                }

                //关键字
                LOCAL, LAMBDA, WHEN, NOT, FALSE, TRUE, NIL, AND, OR, THEN, ELSE, ELSEIF, RETURN, REPEAT, UNTIL,
                CASE, DEFAULT, DEFER -> {
                    colors.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                }
                //字符串
                STRING, LONG_STRING -> {
                    colors.addIfNeeded(line, column, SchemeLua.STRING)
                }
                //注释
                SHORT_COMMENT, BLOCK_COMMENT, DOC_COMMENT -> {
                    colors.addIfNeeded(line, column, EditorColorScheme.COMMENT)
                }
                //符号
                LPAREN, RPAREN, LBRACK, RBRACK, COMMA, DOT -> {
                    colors.addIfNeeded(line, column, EditorColorScheme.OPERATOR)
                }
                RCURLY -> {
                    removeBlock()
                    colors.addIfNeeded(line, column, EditorColorScheme.OPERATOR)
                }
                LCURLY -> {
                    addBlock()
                    colors.addIfNeeded(line, column, EditorColorScheme.OPERATOR)
                }
                //数字
                NUMBER -> {
                    colors.addIfNeeded(line, column, EditorColorScheme.LITERAL)
                }
                //名字
                NAME -> {
                    val text = lexer.yytext()

                    when {
                        language.isName(text) -> colors.addIfNeeded(line, column, SchemeLua.NAME)
                        infoTable?.findTokenInfo(lexer.yyline() + 1, lexer.yycolumn()) != null -> {
                            val tokenInfo =
                                infoTable.findTokenInfo(lexer.yyline() + 1, lexer.yycolumn())
                            val type =
                                if (tokenInfo.info.isLocal) SchemeLua.LOCAL else SchemeLua.GLOBAL

                            colors.addIfNeeded(line, column, type)
                        }

                        else -> colors.addIfNeeded(line, column, EditorColorScheme.TEXT_NORMAL)
                    }
                }
                else -> {
                    colors.addIfNeeded(line, column, EditorColorScheme.TEXT_NORMAL)
                }
            }

            token = lexer.advance()

        }


        colors.determine(lexer.yyline())

        if (blockStack.isEmpty()) {
            if (currSwitch > maxSwitch) {
                maxSwitch = currSwitch;
            }
        }

        colors.suppressSwitch = maxSwitch + 10;
        colors.mExtra = infoTable //scan table and content
        lexer.yyclose()

    }

}
