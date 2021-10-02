package com.dingyi.editor.language.lua

import android.graphics.Color
import com.dingyi.editor.data.ColumnNavigationItem
import com.dingyi.editor.language.lua.LuaTokenTypes.*
import com.dingyi.editor.scheme.SchemeLua
import com.dingyi.lua.analysis.symbol.SymbolTable
import io.github.rosemoe.sora.data.BlockLine
import io.github.rosemoe.sora.data.NavigationItem
import io.github.rosemoe.sora.data.Span
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.text.TextAnalyzeResult
import io.github.rosemoe.sora.text.TextAnalyzer
import io.github.rosemoe.sora.widget.EditorColorScheme

class LuaCodeAnalyzer(private val language: LuaLanguage) : CodeAnalyzer {


    override fun analyze(
        content: CharSequence,
        colors: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {


        val lexer = LuaLexer(content)
        val blockStack = ArrayDeque<BlockLine>()
        var token: LuaTokenTypes? = lexer.advance()
        var maxSwitch = 1
        var currSwitch = 0
        val navigationList = mutableListOf<NavigationItem>()


        val luaSymbolTable = runCatching {
            language.analyzerThread.getOrNull() as SymbolTable
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
                CASE, DEFAULT, DEFER, CONTINUE, TRY, CATCH -> {
                    colors.addIfNeeded(line, column, EditorColorScheme.KEYWORD)
                }
                //字符串
                STRING, LONG_STRING -> {
                    colors.addIfNeeded(line, column, SchemeLua.STRING)
                }
                //注释
                SHORT_COMMENT, BLOCK_COMMENT, DOC_COMMENT -> {

                    if (token == SHORT_COMMENT && lexer.yytext().indexOf("@") != -1) {
                        colors.addIfNeeded(line, column, EditorColorScheme.COMMENT)
                        findCommentType(lexer.yytext())?.let {
                            navigationList.add(
                                ColumnNavigationItem(
                                    column = it.column,
                                    _line = line,
                                    _label = it.content,
                                    type = "@" + it.type.toString().lowercase()
                                )
                            )
                            colors.addIfNeeded(line, it.column, EditorColorScheme.ANNOTATION)
                        }
                    } else {
                        colors.addIfNeeded(line, column, EditorColorScheme.COMMENT)
                    }

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
                    Span.obtain(column, EditorColorScheme.LITERAL).apply {
                        val text = lexer.yytext()
                        if (text.startsWith("0x") && text.length > 6) {
                            runCatching {
                                Color.parseColor(text.replace("0x", "#"))
                            }.onSuccess {
                                setUnderlineColor(it)
                            }
                        }
                    }.let {
                        colors.add(line, it)
                    }
                }
                //名字
                NAME -> {
                    val text = lexer.yytext()

                    when {
                        language.isName(text) -> colors.addIfNeeded(line, column, SchemeLua.NAME)
                        else -> {
                            var flag = EditorColorScheme.TEXT_NORMAL
                            luaSymbolTable?.getOrNewSymbol(text)?.let {
                                it.tokenLocations[(line + 1).toString() + column]
                            }?.let {
                                flag = if (it.isLocal) SchemeLua.LOCAL else SchemeLua.GLOBAL
                                colors.addIfNeeded(line, column, flag)
                            }
                            if (flag == EditorColorScheme.TEXT_NORMAL)
                                colors.addIfNeeded(line, column, EditorColorScheme.TEXT_NORMAL)
                        }
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

        colors.navigation = navigationList



        lexer.yyclose()
    }

}

private fun findCommentType(text: String): CommentData? {
    val typeArray = arrayOf("todo", "tag", "type", "parameter")
    var result: CommentData? = null

    typeArray.forEach {
        val targetString = "@$it "
        val index = text.lowercase().indexOf(targetString)
        if (index != -1) {

            result = CommentData(
                column = index,
                content = text.substring(index + targetString.length),
                type = CommentData.Type::class.java.run {
                    val targetField = it.uppercase()
                    getField(targetField).get(null) as CommentData.Type
                }
            )
            return result
        }
    }

    return result
}

data class CommentData(
    val column: Int,
    val type: Type, var content: String
) {
    enum class Type {
        TODO, TYPE, PARAMETER, TAG
    }
}


