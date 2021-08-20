package com.dingyi.editor.language.lua


import com.dingyi.editor.R
import com.dingyi.editor.language.java.api.AndroidApi
import com.dingyi.editor.language.java.api.SystemApiHelper
import com.dingyi.lua.analyzer.info.*
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
                s, index, (index == split.size - 1), infoTab,
                line, prefix, list
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


    private fun getType(
        it: VarInfo,
        showLocal: Boolean = true,
        showField: Boolean = false
    ): String {
        val buffer = StringBuilder()
        if (showLocal)
            buffer.append(
                if (it.isLocal) "local" else "global"
            )

        when (it.type) {
            Type.UNKNOWN, Type.FUNCTIONCALL -> {
                if (showField) buffer.append("field") else buffer.append("")
            }
            Type.ARG -> {
                buffer.clear()
                buffer.append("arg")
            }

            else -> buffer.append(" ${it.type.toString().lowercase()}")
        }
        return buffer.toString()
    }

    private fun autoComplete(
        name: String,
        index: Int,
        isLast: Boolean,
        infoTab: InfoTable?,
        line: Int,
        allText: String,
        lastList: List<AutoCompleteBean>,
    ): List<AutoCompleteBean> {


        val result = mutableListOf<AutoCompleteBean>()


        //local or var field

        if (lastList.isNotEmpty()) {


            val state = LuajLuaState(JsePlatform.standardGlobals())
            state.openLibs()
            state.openBase()


            val info = lastList[0].info as VarInfo
            val lastName = info.name
            var value = info.value
            val lastCommit = lastList[0].commit


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
                            ).apply {
                                if (description == "function") {
                                    iconRes = R.drawable.function
                                }
                            }
                        )
                    }
                }
            }




            if (info is PackageInfo) {

                AndroidApi
                    .findClasses(allText)
                    .map {

                        it.split(".")[index]
                    }
                    .distinct()
                    .forEach {
                        println("forEach $it")
                        val className = it
                        val allClass = "$lastCommit.$className"
                        if (!isLast && className == name) {
                            result.clear()
                        }

                        if (SystemApiHelper.findClass(allClass)) {
                            result.add(
                                AutoCompleteBean(
                                    description = "class",
                                    label = className,
                                    commit = allClass,
                                    iconRes = R.drawable.java_class,
                                    info = VarInfo().apply {
                                        value=FunctionCallInfo().apply {
                                           setName(allClass)
                                        }
                                    },
                                )
                            )

                        } else {
                            result.add(
                                AutoCompleteBean(
                                    description = "package",
                                    label = className,
                                    commit = allClass,
                                    iconRes = R.drawable.java_package,
                                    info = PackageInfo(),
                                )
                            )
                        }

                    }
            }

            //java class

            if (value is FunctionCallInfo) {

            }

            //table info
            if (value is TableInfo) {
                value.members.forEach {
                    if (it.name.startsWith(name)) {
                        result.add(
                            AutoCompleteBean(
                                description = getType(it, false, true), label = it.name,
                                commit = "$lastCommit.${it.name}", iconRes = when (it.type) {
                                    Type.FUNC -> {
                                        if (it.isLocal) R.drawable.function else R.drawable.method
                                    }
                                    else -> R.drawable.field
                                },
                                info = it
                            )
                        )
                    }
                }
            }

            state.close()
            System.gc()

            return result


        }


        //local or global var

        infoTab?.let { infoTable ->
            infoTable.getVarInfoByRange(line)
                .sortedBy { it.name }
                .forEach {
                    if (it.name.startsWith(name)) {
                        result.add(
                            AutoCompleteBean(
                                commit = it.name,
                                label = it.name,
                                iconRes = when {
                                    it.isArg -> R.drawable.parameter
                                    it.type == Type.FUNC -> {
                                        if (it.isLocal) R.drawable.function else R.drawable.method
                                    }
                                    it.isLocal -> R.drawable.field
                                    else -> R.drawable.variable
                                },
                                info = it,
                                description = getType(it)
                            )
                        )
                    }



                    if (it.name.equals(name) && !isLast && it.value != null) {
                        val lastIndex = result[result.lastIndex]
                        result.clear()
                        result.add(lastIndex)
                        return result
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
                } else if (it == "activity" || it == "_G" || it == "_ENV" || it == "self") {
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
                            commit = it, iconRes = R.drawable.method, info = null
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


        //java

        AndroidApi
            .findClassesByEnd(name)
            .forEach {
                val className = it.split(".").run { get(lastIndex) }
                if (!isLast && className == name) {

                } else {
                    result.add(
                        AutoCompleteBean(
                            description = "class", label = className,
                            commit = className, iconRes = R.drawable.java_class, info = VarInfo(),
                        )
                    )
                }
            }

        //java package (deep:1)

        AndroidApi
            .findClasses(allText)
            .map { it.split(".")[0] }
            .distinct()
            .sorted()
            .forEach {
                val className = it
                if (!isLast && className == name) {
                    result.clear()
                }

                result.add(
                    AutoCompleteBean(
                        description = "package", label = className,
                        commit = className, iconRes = R.drawable.java_package,
                        info = PackageInfo()
                    )
                )
                println(result)
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
        var iconRes: Int?,
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