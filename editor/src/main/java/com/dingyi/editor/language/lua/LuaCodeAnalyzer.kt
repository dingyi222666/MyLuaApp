package com.dingyi.editor.language.lua

import com.dingyi.editor.language.lua.LuaTokenTypes.*
import io.github.rosemoe.editor.interfaces.CodeAnalyzer
import io.github.rosemoe.editor.text.TextAnalyzeResult
import io.github.rosemoe.editor.text.TextAnalyzer
import io.github.rosemoe.editor.widget.EditorColorScheme

class LuaCodeAnalyzer : CodeAnalyzer {


    override fun analyze(
        content: CharSequence,
        colors: TextAnalyzeResult,
        delegate: TextAnalyzer.AnalyzeThread.Delegate
    ) {

        LuaLanguage.clearUserWord()
        val lexer = LuaLexer(content)
        var token: LuaTokenTypes? = lexer.advance()
        while (delegate.shouldAnalyze()) {
            if (token == null) {
                break
            }

            when (token) {
                //关键字
                GOTO, FOR, FUNCTION, DO, END, IF, THEN, ELSEIF, ELSE, RETURN, REPEAT,
                    TRUE,FALSE,BREAK,LOCAL,CASE,SWITCH,LAMBDA,DEFAULT,WHEN,NIL,AND,OR,CONTINUE -> {
                        colors.addIfNeeded(lexer.yyline(),lexer.yycolumn(),EditorColorScheme.KEYWORD)
                    }

                LCURLY,RCURLY -> {
                    colors.addIfNeeded(lexer.yyline(),lexer.yycolumn(),EditorColorScheme.OPERATOR)
                }
            }

            token = lexer.advance()

        }
        lexer.yyclose()

    }

}
