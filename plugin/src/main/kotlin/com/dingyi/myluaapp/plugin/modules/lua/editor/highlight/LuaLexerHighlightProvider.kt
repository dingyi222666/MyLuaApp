package com.dingyi.myluaapp.plugin.modules.lua.editor.highlight


import android.util.Log
import com.dingyi.lsp.lua.common.lexer.LuaLexer
import com.dingyi.lsp.lua.common.lexer.LuaTokenTypes
import com.dingyi.lsp.lua.common.lexer.LuaTokenTypes.*
import com.dingyi.myluaapp.editor.ktx.addIfNeeded
import com.dingyi.myluaapp.editor.language.highlight.LexerHighlightProvider
import io.github.rosemoe.sora.lang.styling.CodeBlock
import io.github.rosemoe.sora.lang.styling.MappedSpans
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LuaLexerHighlightProvider : LexerHighlightProvider() {

    override suspend fun highlighting(
        text: CharSequence,
        builder: MappedSpans.Builder,
        styles: Styles,
        delegate: Delegate
    ): Unit = withContext(Dispatchers.IO) {

        kotlin.runCatching {


            val lexer = LuaLexer(text)
            val blockStack = ArrayDeque<CodeBlock>()
            var token: LuaTokenTypes? = lexer.advance()
            var maxSwitch = 1
            var currSwitch = 0


            val removeBlock = {
                if (!blockStack.isEmpty()) {
                    val block = blockStack.removeFirst()
                    block.endLine = lexer.yyline()
                    block.endColumn = lexer.yycolumn()
                    if (block.startLine != block.endLine) {//行数不一样在添加区块
                        styles.addCodeBlock(block)
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

                val line = lexer.yyline()
                val column = lexer.yycolumn()


                when (token) {

                    //空白符号
                    WHITE_SPACE, NEW_LINE -> {
                        builder.addNormalIfNull()
                    }

                    //关键字 并且添加区块
                    DO, FUNCTION, IF, WHILE, SWITCH, FOR -> {
                        addBlock()
                        builder.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                    }

                    END -> {
                        removeBlock()
                        builder.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                    }

                    //关键字
                    LOCAL, LAMBDA, WHEN, NOT, FALSE, TRUE, NIL, AND, OR, THEN, ELSE, ELSEIF, RETURN, REPEAT, UNTIL,
                    CASE, DEFAULT, DEFER -> {
                        builder.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                    }
                    //字符串
                    STRING, LONG_STRING -> {
                        builder.addIfNeeded(line, column, EditorColorScheme.ATTRIBUTE_VALUE)
                    }
                    //注释
                    SHORT_COMMENT, BLOCK_COMMENT, DOC_COMMENT -> {

                        builder.addIfNeeded(line, column, EditorColorScheme.COMMENT)

                    }
                    //符号
                    LPAREN, RPAREN, LBRACK, RBRACK, COMMA, DOT -> {
                        builder.addIfNeeded(line, column, EditorColorScheme.OPERATOR)
                    }
                    RCURLY -> {
                        removeBlock()
                        builder.addIfNeeded(line, column, EditorColorScheme.OPERATOR)
                    }
                    LCURLY -> {
                        addBlock()
                        builder.addIfNeeded(line, column, EditorColorScheme.OPERATOR)
                    }
                    //数字
                    NUMBER -> {
                        builder.addIfNeeded(line, column, EditorColorScheme.LITERAL)
                    }
                    //名字
                    NAME -> {
                        builder.addIfNeeded(line, column, EditorColorScheme.TEXT_NORMAL)
                    }
                    else -> {
                        builder.addIfNeeded(line, column, EditorColorScheme.TEXT_NORMAL)
                    }
                }

                token = lexer.advance()

            }


            builder.determine(lexer.yyline())

            if (blockStack.isEmpty()) {
                if (currSwitch > maxSwitch) {
                    maxSwitch = currSwitch;
                }
            }

            styles.suppressSwitch = maxSwitch + 10;

            lexer.yyclose()


        }.onFailure {

            Log.v("Editor", "highlight error", it)

            it.printStackTrace()
        }

    }
}