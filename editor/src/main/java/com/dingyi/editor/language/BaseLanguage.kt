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


    private var _keywords = mutableListOf<String>()
    private var _names = mutableListOf<String>()
    private var _bases = HashMap<String, Array<String>>(0)
    private var _users = mutableListOf<String>()
    private var _operators = generateOperators(BASIC_C_OPERATORS)

    private val _userCache = ArrayList<String>()
    private var _userWords = arrayOf<String>()
    private lateinit var _keyword: Array<String>
    private lateinit var _name: Array<String>

    fun updateUserWord() {
        val uw = Array(_userCache.size){""}
        _userWords = _userCache.toArray(uw)
    }

    fun getUserWord(): Array<String> {
        return _userWords
    }

    fun getNames(): Array<String> {
        return _name
    }

    fun getBasePackage(name: String): Array<String>? {
        return _bases[name]
    }

    fun getKeywords(): Array<String> {
        return _keyword
    }

    fun setKeywords(keywords: Array<String>) {
        _keyword = keywords
        _keywords = ArrayList(keywords.size)
        _keywords.addAll(_keyword)
    }

    fun setNames(names: Array<String>) {
        _name = names
        val buf = ArrayList<String>()
        _names =ArrayList(names.size)
        for (i in names.indices) {
            if (!buf.contains(names[i]))
                buf.add(names[i])
            _names.add(names[i])
        }
        _name= Array(buf.size) {""}
        buf.toArray(_name)
    }

    fun addBasePackage(name: String, names: Array<String>) {
        _bases[name] = names
    }

    fun removeBasePackage(name: String) {
        _bases.remove(name)
    }

    fun clearUserWord() {
        _userCache.clear()
        _users.clear()
    }

    fun addUserWord(name: String) {
        if (!_userCache.contains(name) && !_names.contains(name))
            _userCache.add(name)
        _users.add(name)
    }

    protected fun setOperators(operators: CharArray) {
        _operators = generateOperators(operators)
    }

    private fun generateOperators(operators: CharArray): Array<Char> {
        val operatorsMap = Array(operators.size){ Char(0)}
        for (i in operators.indices) {
            operatorsMap[i]=operators[i]
        }
        return operatorsMap
    }

    fun isOperator(c: Char): Boolean {
        return _operators.contains(Character.valueOf(c))
    }

    fun isKeyword(s: String): Boolean {
        return _keywords.contains(s)
    }

    fun isName(s: String?): Boolean {
        return _names.contains(s)
    }

    fun isBasePackage(s: String): Boolean {
        return _bases.containsKey(s)
    }

    fun isBaseWord(p: String, s: String): Boolean {
        val pkg = _bases[p]!!
        for (n in pkg) {
            if (n == s) return true
        }
        return false
    }

    fun isUserWord(s: String): Boolean {
        return _users.contains(s)
    }


}