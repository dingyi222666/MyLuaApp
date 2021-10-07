package com.dingyi.editor.language.lua

import androidx.appcompat.app.AppCompatActivity
import com.dingyi.editor.language.BaseLanguage
import com.dingyi.lua.analysis.LuaAnalyzer
import io.github.rosemoe.sora.interfaces.AutoCompleteProvider
import io.github.rosemoe.sora.interfaces.CodeAnalyzer
import io.github.rosemoe.sora.interfaces.EditorLanguage
import io.github.rosemoe.sora.interfaces.NewlineHandler
import io.github.rosemoe.sora.interfaces.NewlineHandler.HandleResult
import io.github.rosemoe.sora.langs.internal.MyCharacter
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import io.github.rosemoe.sora.text.TextAnalyzer
import io.github.rosemoe.sora.text.TextUtils
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.SymbolPairMatch


/**
 * @author: dingyi
 * @date: 2021/8/14 20:57
 * @description:
 * @param codeEditor The language must binding editor to get color scheme and analyze code
 **/
class LuaLanguage(val codeEditor: CodeEditor, private val activity: AppCompatActivity) :
    BaseLanguage(), EditorLanguage, ContentListener {

    private val keywordTarget =
        "and|break|case|catch|continue|default|defer|do|else|elseif|end|false|finally|for|function|goto|if|in|lambda|local|nil|not|or|repeat|return|switch|then|true|try|until|when|while"
    private val globalTarget =
        "self|__add|__band|__bnot|__bor|__bxor|__call|__close|__concat|__div|__eq|__gc|__idiv|__index|__le|__len|__lt|__mod|__mul|__newindex|__pow|__shl|__shr|__sub|__tostring|__unm|_ENV|_G|assert|collectgarbage|dofile|error|getfenv|getmetatable|ipairs|load|loadfile|loadstring|module|next|pairs|pcall|print|rawequal|rawget|rawlen|rawset|require|select|setfenv|setmetatable|tointeger|tonumber|tostring|type|unpack|xpcall"

    private val packageName = "coroutine|debug|io|luajava|math|os|package|string|table|utf8"
    private val package_coroutine = "create|isyieldable|resume|running|status|wrap|yield"
    private val package_debug =
        "debug|gethook|getinfo|getlocal|getmetatable|getregistry|getupvalue|getuservalue|sethook|setlocal|setmetatable|setupvalue|setuservalue|traceback|upvalueid|upvaluejoin"
    private val package_io =
        "close|flush|info|input|isdir|lines|ls|mkdir|open|output|popen|read|readall|stderr|stdin|stdout|tmpfile|type|write"
    private val package_luajava =
        "astable|bindClass|clear|coding|createArray|createProxy|getContext|instanceof|loadLib|loaded|luapath|new|newArray|newInstance|override|package|tostring"
    private val package_math =
        "abs|acos|asin|atan|atan2|ceil|cos|cosh|deg|exp|floor|fmod|frexp|huge|ldexp|log|log10|max|maxinteger|min|mininteger|modf|pi|pow|rad|random|randomseed|sin|sinh|sqrt|tan|tanh|tointeger|type|ult"
    private val package_os =
        "clock|date|difftime|execute|exit|getenv|remove|rename|setlocale|time|tmpname"
    private val package_package =
        "config|cpath|loaded|loaders|loadlib|path|preload|searchers|searchpath|seeall"
    private val package_string =
        "byte|char|dump|find|format|gfind|gmatch|gsub|len|lower|match|rep|reverse|sub|upper"
    private val package_table =
        "clear|clone|concat|const|find|foreach|foreachi|gfind|insert|maxn|move|pack|remove|size|sort|unpack"
    private val package_utf8 =
        "byte|char|charpattern|charpos|codepoint|codes|escape|find|fold|gfind|gmatch|gsub|insert|len|lower|match|ncasecmp|next|offset|remove|reverse|sub|title|upper|width|widthindex"
    private val extFunctionTarget =
        "activity|call|compile|dump|each|enum|import|loadbitmap|loadlayout|loadmenu|service|set|task|thread|timer"
    private val functionTarget = "$globalTarget|activity|import|task|$packageName"

    private val __keywords = keywordTarget.split("|").toTypedArray()

    private val __names = functionTarget.split("|").toTypedArray()

    private val LUA_OPERATORS = charArrayOf(
        '(', ')', '{', '}', ',', ';', '=', '+', '-',
        '/', '*', '&', '!', '|', ':', '[', ']', '<', '>',
        '?', '~', '%', '^'
    )

    val codeAnalyzer = LuaAnalyzer()


    val analyzerThread = LuaAnalyzerThread()

    private var mContent: Content? = null


    init {

        super.setOperators(LUA_OPERATORS)
        super.setKeywords(__keywords)
        super.setNames(__names)
        super.addBasePackage("io", package_io.split("|").toTypedArray());
        super.addBasePackage("string", package_string.split("|").toTypedArray());
        super.addBasePackage("luajava", package_luajava.split("|").toTypedArray());
        super.addBasePackage("os", package_os.split("|").toTypedArray());
        super.addBasePackage("table", package_table.split("|").toTypedArray());
        super.addBasePackage("math", package_math.split("|").toTypedArray());
        super.addBasePackage("utf8", package_utf8.split("|").toTypedArray());
        super.addBasePackage("coroutine", package_coroutine.split("|").toTypedArray());
        super.addBasePackage("package", package_package.split("|").toTypedArray());
        super.addBasePackage("debug", package_debug.split("|").toTypedArray());


        val mSpanner = CodeEditor::class.java.getDeclaredField("mSpanner")
            .apply {
                isAccessible = true
            }.get(codeEditor) as TextAnalyzer


        analyzerThread.waitRefreshCallback = {
            mSpanner.analyze(mContent)
            println("analyzer done")
        }


        val mListenerList = Content::class.java.getDeclaredField("mListeners")
            .apply {
                isAccessible = true
            }.get(codeEditor.text) as MutableList<ContentListener>

        codeEditor.text.addContentListener(this)

    }


    override fun beforeReplace(content: Content?) {
        println(content)
    }

    override fun afterInsert(
        content: Content?,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
        insertedContent: CharSequence?
    ) {
        mContent = content
        println("push object $content")
        content?.toString()?.let { analyzerThread.pushObject(it) }
    }

    override fun afterDelete(
        content: Content?,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
        deletedContent: CharSequence?
    ) {
        mContent = content
        println("push object $content")
        content?.toString()?.let { analyzerThread.pushObject(it) }
    }

    override fun getAnalyzer(): CodeAnalyzer {
        return LuaCodeAnalyzer(this)
    }


    override fun getAutoCompleteProvider(): AutoCompleteProvider {
        return LuaAutoComplete(this)
    }

    fun getSchemeColor(type: Int): Int {
        return codeEditor.colorScheme.getColor(type)
    }

    override fun isAutoCompleteChar(ch: Char): Boolean {
        return if (ch == ',')
            false;
        else
            ch == '.' || ch == ':'
                    || ch == '_' || MyCharacter.isJavaIdentifierPart(ch.code);
    }

    override fun getIndentAdvance(content: String): Int {
        return LuaFormat.createAutoIndent(content)
    }

    override fun useTab(): Boolean {
        return true;
    }

    override fun format(text: CharSequence?): CharSequence {
        return LuaFormat.format(text, 4)
    }

    override fun getSymbolPairs(): SymbolPairMatch {
        return SymbolPairMatch.DefaultSymbolPairs();
    }

    private val newlineHandlers = arrayOf(BraceHandler())

    @Override
    override fun getNewlineHandlers(): Array<out NewlineHandler> {
        return newlineHandlers;
    }

    inner class BraceHandler : NewlineHandler {
        override fun matchesRequirement(beforeText: String, afterText: String): Boolean {
            return afterText.endsWith("end") || afterText.endsWith("}")
        }

        override fun handleNewline(
            beforeText: String,
            afterText: String,
            tabSize: Int
        ): HandleResult {
            val count: Int = TextUtils.countLeadingSpaceCount(beforeText, tabSize)
            val advanceBefore: Int = getIndentAdvance(beforeText)
            val advanceAfter: Int = getIndentAdvance(afterText)
            var text: String
            val sb: StringBuilder = StringBuilder("\n")
                .append(TextUtils.createIndent(count + advanceBefore, tabSize, useTab()))
                .append('\n')
                .append(
                    TextUtils.createIndent(count + advanceAfter, tabSize, useTab())
                        .also { text = it })
            val shiftLeft = text.length + 1
            return HandleResult(sb, shiftLeft)
        }
    }


}