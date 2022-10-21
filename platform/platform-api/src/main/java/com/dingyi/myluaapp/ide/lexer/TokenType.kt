package com.dingyi.myluaapp.ide.lexer

open class TokenType(
    val originObject: Any
) {


}

enum class DefaultTokenType(tokenName: String) {

    KEYWORD("KEYWORD"), COMMENT("COMMENT"),
    FUNCTION_NAME("FUNCTION_NAME"), IDENTIFIER_NAME("IDENTIFIER_NAME"),
    ANNOTATION("ANNOTATION"), LITERAL("LITERAL"),
    OPERATOR("OPERATOR"), IDENTIFIER_VAR("IDENTIFIER_VAR");

    val tokenType = TokenType(tokenName)

}
