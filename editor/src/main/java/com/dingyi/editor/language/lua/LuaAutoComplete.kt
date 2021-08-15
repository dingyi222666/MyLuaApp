package com.dingyi.editor.language.lua

import com.androlua.LuaApplication
import com.dingyi.editor.R
import com.dingyi.lua.analyzer.info.BaseInfo
import com.luajava.LuajLuaState
import io.github.rosemoe.editor.interfaces.AutoCompleteProvider
import io.github.rosemoe.editor.struct.CompletionItem
import io.github.rosemoe.editor.text.TextAnalyzeResult
import org.luaj.vm2.lib.jse.JsePlatform

/**
 * @author: dingyi
 * @date: 2021/8/15 5:47
 * @description:
 **/
class LuaAutoComplete(private val language: LuaLanguage) : AutoCompleteProvider {


    override fun getAutoCompleteItems(
        prefix: String,
        isInCodeBlock: Boolean,
        colors: TextAnalyzeResult,
        line: Int
    ): MutableList<CompletionItem> {

        val result = mutableListOf<CompletionItem>()

        var list = listOf<Pair<Pair<String, String>, Pair<Int?, BaseInfo?>>>()

        val split = prefix.split(".")



        split.forEachIndexed { index, s ->

            if (s.isNotEmpty()) {

                val isDot = split[(index + 1).coerceAtMost(split.size - 1)].isEmpty()

                list = autoComplete(
                    s, isDot,
                    isDot.or(index == split.size - 1), list
                )
            }
        }

        list.forEach { pair ->
            result.add(
                CompletionItem(
                    pair.first.first,
                    pair.first.second
                ).apply {

                    pair.second.first?.let {
                        icon = DrawablePool.loadDrawable(LuaApplication.getInstance(), it)
                    }
                })
        }

        return result
    }

    private fun autoComplete(
        name: String,
        isDot: Boolean,
        isLast: Boolean,
        lastList: List<Pair<Pair<String, String>, Pair<Int?, BaseInfo?>>>
    ): List<Pair<Pair<String, String>, Pair<Int?, BaseInfo?>>> {


        val state = LuajLuaState(JsePlatform.standardGlobals())
        state.openLibs()
        state.openBase()

        if (lastList.size == 1 && lastList[0].second.second != null) {
            TODO("多table提示")
        }

        val result = mutableListOf<Pair<Pair<String, String>, Pair<Int?, BaseInfo?>>>()



        if (isDot) {

            if (language.isBasePackage(name)) {

                language.getBasePackage(name)?.forEach { it ->
                    result.add(
                        Pair(
                            "$name.$it" to getType(state, "$name.$it").run {
                                if (this == "nil") {
                                    return@run "field"
                                }
                                this
                            },
                            R.drawable.field to null
                        )
                    )

                }
            }

            state.close()
            System.gc()

            return result

        }

        //functions

        language.getNames().forEach {
            if (it.lowercase().startsWith(name)) {
                if (language.isBasePackage(it)) {
                    result.add(Pair(it to "global table", R.drawable.variable to null))
                } else if (it == "activity" || it == "_G" || it == "_ENV") {
                    result.add(Pair(it to "global $it", R.drawable.variable to null))
                } else if (it.startsWith("__")) {
                    result.add(Pair(it to "metamethod", R.drawable.method to null))
                } else {
                    result.add(Pair(it to "global func", R.drawable.variable to null))
                }
            }
        }


        //keyword

        language.getKeywords().forEach {
            if (it.lowercase().startsWith(name)) {
                result.add(Pair(it to "keyword", null to null))
            }
        }



        state.close()
        System.gc()

        return result
    }

    private fun getType(luaJvm: LuajLuaState, s: String): String {

        val tab = runCatching {
            luaJvm.globals.load("return type($s)").call().tojstring()
        }.onFailure {
            println(it)
        }
        return tab.getOrNull() ?: "field"
    }


}