package com.dingyi.editor.language.lua


import com.dingyi.editor.R
import com.dingyi.lua.analyzer.info.BaseInfo
import com.dingyi.lua.analyzer.info.InfoTable
import com.dingyi.lua.analyzer.info.Type
import com.dingyi.lua.analyzer.info.VarInfo
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

        var list = listOf<AutoCompleteBean>()


        val infoTab = colors.mExtra as InfoTable?


        val split = prefix.split(".")

        split.forEachIndexed { index, s ->
            list = autoComplete(
                s, (index == split.size - 1), infoTab, line, list
            )
        }


        list.forEach { autoCompleteBean ->
            result.add(
                CompletionItem("", "").apply {
                    icon = autoCompleteBean.iconRes?.let {
                        DrawablePool.loadDrawable(it)
                    }
                    desc = autoCompleteBean.description
                    label = autoCompleteBean.label
                    commit = autoCompleteBean.commit
                })
        }

        return result
    }


    private fun autoComplete(
        name: String,
        isLast: Boolean,
        infoTab: InfoTable?,
        line: Int,
        lastList: List<AutoCompleteBean>,
    ): List<AutoCompleteBean> {


        val result = mutableListOf<AutoCompleteBean>()


        //local or var field

        if (isLast && lastList.isNotEmpty()) {
            val state = LuajLuaState(JsePlatform.standardGlobals())
            state.openLibs()
            state.openBase()


            val info = lastList[0].info as VarInfo
            val lastName = info.name
            val value = info.value
            //base package


            if (language.isBasePackage(lastName)) {
                language.getBasePackage(lastName)?.forEach { it ->
                    if (it.startsWith(name)) {
                        result.add(
                            AutoCompleteBean(
                                description = getType(state, "$lastName.$it").run {
                                    if (this == "nil") {
                                        return@run "field"
                                    }
                                    this
                                }, label = it, commit = "$lastName.$it", iconRes = R.drawable.field,
                                info = null
                            )
                        )
                    }
                }
            }


            state.close()
            System.gc()

            return result


        }


        //local or global vr

        infoTab?.let { infoTable ->
            infoTable.getVarInfoByRange(line).forEach {
                if (it.name.startsWith(name)) {

                    val buffer = StringBuilder()
                    buffer.append(
                        if (it.isLocal) "local" else "global"
                    )
                    when (it.type) {
                        Type.UNKNOWN, Type.FUNCTIONCALL -> buffer.append("")
                        Type.ARG -> {
                            buffer.clear()
                            buffer.append("arg")
                        }
                        else -> buffer.append(" ${it.type.toString().lowercase()}")
                    }

                    result.add(
                        AutoCompleteBean(
                            commit = it.name,
                            label = it.name,
                            iconRes = R.drawable.field,
                            info = it,
                            description = buffer.toString()
                        )
                    )
                }
            }
        }

        //functions

        language.getNames().forEach {
            if (it.lowercase().startsWith(name)) {
                if (language.isBasePackage(it)) {
                    result.add(
                        AutoCompleteBean(
                            description = "global table", label = it,
                            commit = it, iconRes = R.drawable.field, info = null
                        )
                    )
                } else if (it == "activity" || it == "_G" || it == "_ENV") {
                    result.add(
                        AutoCompleteBean(
                            description = "global $it", label = it,
                            commit = it, iconRes = R.drawable.variable, info = null
                        )
                    )

                } else if (it.startsWith("__")) {
                    result.add(
                        AutoCompleteBean(
                            description = "metamethod", label = it, commit = it,
                            iconRes = R.drawable.method, info = null
                        )
                    )

                } else {
                    result.add(
                        AutoCompleteBean(
                            description = "global func", label = it,
                            commit = it, iconRes = R.drawable.variable, info = null
                        )
                    )

                }
            }
        }


        //keyword
        language.getKeywords().forEach {
            if (it.lowercase().startsWith(name)) {
                result.add(
                    AutoCompleteBean(
                        description = "keyword", label = it,
                        commit = it, iconRes = null, info = null
                    )
                )
            }
        }


        //is base package

        if (language.isBasePackage(name) && !isLast) {
            result.clear()//清空全部结果
            result.add(AutoCompleteBean(null, VarInfo().apply {
                this.name = name
            }))

        }



        System.gc()

        return result
    }


    data class AutoCompleteBean(
        val iconRes: Int?,
        val info: BaseInfo?,
        val label: String = "",
        val description: String = "",
        val commit: String = ""
    )

    private fun getType(luaJvm: LuajLuaState, s: String): String {

        val tab = runCatching {
            luaJvm.globals.load("return type($s)").call().tojstring()
        }.onFailure {
            println(it)
        }
        return tab.getOrNull() ?: "field"
    }


}