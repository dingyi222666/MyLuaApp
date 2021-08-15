package com.dingyi.editor.language

/**
 * @author: dingyi
 * @date: 2021/8/14 22:25
 * @description:
 **/
abstract class BaseLanguage {

    private val BASIC_C_OPERATORS = charArrayOf(
        '(', ')', '{', '}', '.', ',', ';', '=', '+', '-',
        '/', '*', '&', '!', '|', ':', '[', ']', '<', '>',
        '?', '~', '%', '^'
    )


    private val names=mutableListOf<String>()
    private val basePackages= mutableMapOf<String,Array<String>>()
    private val keywords=mutableListOf<String>()
    private var operators= arrayOf<Char>()

    fun getNames(): Array<String> {
        return names.toTypedArray()
    }

    fun getBasePackage(name: String): Array<String>? {
        return basePackages[name]
    }

    fun getKeywords(): Array<String> {
        return keywords.toTypedArray()
    }

    fun setKeywords(keywords: Array<String>) {
        this.keywords.clear()
        this.keywords.addAll(keywords)
    }

    fun setNames(names: Array<String>) {
        this.names.clear()
        this.names.addAll(names)
    }

    fun addBasePackage(name: String, names: Array<String>) {
        basePackages[name] = names
    }

    fun removeBasePackage(name: String) {
        basePackages.remove(name)
    }


    protected fun setOperators(operators: CharArray) {
        this.operators = generateOperators(operators)
    }

    private fun generateOperators(operators: CharArray): Array<Char> {
        val operatorsMap = Array(operators.size){ Char(0)}
        for (i in operators.indices) {
            operatorsMap[i]=operators[i]
        }
        return operatorsMap
    }

    fun isOperator(c: Char): Boolean {
        return operators.contains(Character.valueOf(c))
    }

    fun isKeyword(s: String): Boolean {
        return keywords.contains(s)
    }

    fun isName(s: String): Boolean {
        return names.contains(s)
    }

    fun isBasePackage(s: String): Boolean {
        return basePackages.contains(s)
    }


}