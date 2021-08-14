package com.dingyi.editor.language.lua

import java.io.IOException
import java.lang.StringBuilder

/**
 * @author: dingyi
 * @date: 2021/8/14 21:03
 * @description:
 **/
object LuaFormat {



    fun createAutoIndent(text: CharSequence?): Int {
        val lexer = LuaLexer(text)
        var idt = 0
        try {
            while (true) {
                val type = lexer.advance() ?: break
                idt += if (lexer.yytext() == "switch") 1 else indent(type)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return idt
    }


    fun indent(t: LuaTokenTypes): Int {
        return when (t) {
            LuaTokenTypes.FOR, LuaTokenTypes.WHILE, LuaTokenTypes.FUNCTION, LuaTokenTypes.IF, LuaTokenTypes.REPEAT, LuaTokenTypes.LCURLY, LuaTokenTypes.SWITCH -> 1
            LuaTokenTypes.UNTIL, LuaTokenTypes.END, LuaTokenTypes.RCURLY -> -1
            else -> 0
        }
    }

    fun format(text: CharSequence?, width: Int): CharSequence {
        val builder = StringBuilder()
        var isNewLine = true
        val lexer = LuaLexer(text)
        try {
            var idt = 0
            while (true) {
                val type = lexer.advance() ?: break
                if (type == LuaTokenTypes.NEW_LINE) {
                    if (builder.isNotEmpty() && builder[builder.length - 1] == ' ') builder.deleteCharAt(
                        builder.length - 1
                    )
                    isNewLine = true
                    builder.append('\n')
                    idt = 0.coerceAtLeast(idt)
                } else if (isNewLine) {
                    when (type) {
                        LuaTokenTypes.WHITE_SPACE -> {
                        }
                        LuaTokenTypes.ELSE, LuaTokenTypes.ELSEIF, LuaTokenTypes.CASE, LuaTokenTypes.DEFAULT -> {
                            //idt--;
                            builder.append(createIndent(idt * width - width / 2))
                            builder.append(lexer.yytext())
                            //idt++;
                            isNewLine = false
                        }
                        LuaTokenTypes.DOUBLE_COLON, LuaTokenTypes.AT -> {
                            builder.append(lexer.yytext())
                            isNewLine = false
                        }
                        LuaTokenTypes.END, LuaTokenTypes.UNTIL, LuaTokenTypes.RCURLY -> {
                            idt--
                            builder.append(createIndent(idt * width))
                            builder.append(lexer.yytext())
                            isNewLine = false
                        }
                        else -> {
                            builder.append(createIndent(idt * width))
                            builder.append(lexer.yytext())
                            idt += indent(type)
                            isNewLine = false
                        }
                    }
                } else if (type == LuaTokenTypes.WHITE_SPACE) {
                    builder.append(' ')
                } else {
                    builder.append(lexer.yytext())
                    idt += indent(type)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return builder
    }

    private fun createIndent(n: Int): CharArray? {
        if (n < 0) return CharArray(0)
        val ids = CharArray(n)
        for (i in 0 until n) ids[i] = ' '
        return ids
    }
}