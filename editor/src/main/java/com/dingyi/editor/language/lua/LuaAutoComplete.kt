package com.dingyi.editor.language.lua


import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.dingyi.editor.ColorCompletionItem
import com.dingyi.editor.R
import com.dingyi.editor.language.java.api.AndroidApi
import com.dingyi.editor.language.java.api.SystemApiHelper
import com.dingyi.lua.analyze.info.*
import com.luajava.LuajLuaState
import io.github.rosemoe.editor.interfaces.AutoCompleteProvider
import io.github.rosemoe.editor.text.TextAnalyzeResult
import org.luaj.vm2.lib.jse.JsePlatform
import java.lang.reflect.AccessibleObject
import java.util.*

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
    ): MutableList<ColorCompletionItem> {

        val result = mutableListOf<ColorCompletionItem>()

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
                ColorCompletionItem("").apply {
                    icon = autoCompleteBean.iconRes?.let {
                        DrawablePool.loadDrawable(it)
                    }
                    desc = autoCompleteBean.description
                    colorLabel = autoCompleteBean.label
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

        var isLocked = false

        //not empty
        if (lastList.isNotEmpty()) {


            val info = lastList[0].info as VarInfo
            val lastName = info.name
            var value = info.value
            val lastCommit = lastList[0].commit

            //base package
            if (language.isBasePackage(lastName)) {
                val state = LuajLuaState(JsePlatform.standardGlobals())
                state.openLibs()
                state.openBase()

                language.getBasePackage(lastName)
                    ?.filter { it.startsWith(name) }
                    ?.forEach { it ->
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
                state.close()
            }


            if (info is PackageInfo) {

                AndroidApi
                    .findClasses(allText)
                    .map {
                        it.split(".")[index]
                    }
                    .distinct()
                    .forEach {
                        val className = it
                        val allClass = "$lastCommit.$className"
                        if (!isLast && className == name) {
                            result.clear()
                            isLocked = true
                        }
                        when {
                            SystemApiHelper.findClass(allClass) -> {
                                result.add(
                                    AutoCompleteBean(
                                        description = "class",
                                        label = className,
                                        commit = allClass,
                                        iconRes = R.drawable.java_class,
                                        info = VarInfo().apply {
                                            value = FunctionCallInfo().apply {
                                                setName(allClass)
                                            }
                                        },
                                    )
                                )
                            }
                            else -> {
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
                        if (isLocked) {
                            return result
                        }
                    }
            }


            //java class
            if (value is FunctionCallInfo) {
                println(value.name)
                SystemApiHelper
                    .analyzeCode(value.name)
                    .let { data ->
                        data.classList.forEach { clazz ->
                            if (data.isNewInstance) {
                                SystemApiHelper.getPublicFieldData(
                                    clazz, name
                                )
                            } else {
                                SystemApiHelper.getPublicStaticFieldData(
                                    clazz, name
                                )
                            }.filter {
                                it.name.lowercase(Locale.getDefault())
                                    .startsWith(name.lowercase(Locale.getDefault()))
                            }.distinctBy {
                                SystemApiHelper.getAccessibleObjectText(it.base)
                            }.sortedBy {
                                if (data.isNewInstance) {
                                    it.type == Type.METHOD
                                } else {
                                    it.type == Type.FIELD
                                }
                            }.forEach { fieldData ->
                                if (!isLast && fieldData.name == name) {
                                    result.clear()
                                    isLocked = true
                                }
                                result.add(
                                    AutoCompleteBean(
                                        description = fieldData.typeClass?.simpleName.toString(),
                                        label = getColorText(fieldData.name, fieldData.base),
                                        commit = "$lastCommit.${
                                            if (fieldData.type == Type.METHOD) {
                                                "${fieldData.name}()"
                                            } else {
                                                fieldData.name
                                            }
                                        }", iconRes =
                                        when (fieldData.type) {
                                            Type.METHOD -> R.drawable.method
                                            else -> R.drawable.field
                                        },
                                        info = VarInfo().apply {
                                            setValue(
                                                FunctionCallInfo()
                                                    .also {
                                                        it.name = "$lastCommit.${fieldData.name}()"
                                                    }
                                            )
                                        }
                                    )
                                )
                                if (isLocked) {
                                    println(result)
                                    return result
                                }
                            }
                        }
                    }
            }


            //table info
            if (value is TableInfo) {
                value.members
                    .filter { it.name.startsWith(name) }
                    .forEach {
                        if (!isLast) {
                            result.clear()
                        }

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


            System.gc()

            return result


        }


        //local or global var
        infoTab?.let { infoTable ->
            infoTable.getVarInfoByRange(line)
                .sortedBy { it.name }
                .filter { it.name.startsWith(name) }
                .forEach { info ->
                    result.add(
                        AutoCompleteBean(
                            commit = info.name,
                            label = info.name,
                            iconRes = when {
                                info.isArg -> R.drawable.parameter
                                info.type == Type.FUNC -> {
                                    if (info.isLocal) R.drawable.function else R.drawable.method
                                }
                                info.isLocal -> R.drawable.field
                                else -> R.drawable.variable
                            },
                            info = info.apply {
                                AndroidApi
                                    .findClassesByEnd(info.name)
                                    .map { it.split(".").run { get(lastIndex) } }
                                    .distinct()
                                    .any { it == info.name }.let {
                                        if (it) {
                                            value = FunctionCallInfo().apply {
                                                setName(info.code)
                                            }
                                        }
                                    }
                            },
                            description = getType(info)
                        )
                    )


                    if (!isLast && info.value != null) {
                        val lastIndex = result[result.lastIndex]
                        result.clear()
                        result.add(lastIndex)

                        return result
                    }
                }
        }


        //functions
        language.getNames()
            .filter {
                it.lowercase().startsWith(name)
            }
            .forEach {
                when {
                    language.isBasePackage(it) -> {
                        result.add(
                            AutoCompleteBean(
                                description = "global table", label = it,
                                commit = it, iconRes = R.drawable.field, info = null
                            )
                        )
                    }
                    (it == "activity" || it == "_G" || it == "_ENV" || it == "self") -> {
                        result.add(
                            AutoCompleteBean(
                                description = "global $it", label = it,
                                commit = it, iconRes = R.drawable.variable, info = null
                            )
                        )
                    }
                    it.startsWith("__") -> {
                        result.add(
                            AutoCompleteBean(
                                description = "metamethod", label = it, commit = it,
                                iconRes = R.drawable.method, info = null
                            )
                        )

                    }
                    else -> {
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
        language.getKeywords()
            .filter {
                it.lowercase().startsWith(name)
            }
            .forEach {
                result.add(
                    AutoCompleteBean(
                        description = "keyword", label = it,
                        commit = it, iconRes = null, info = null
                    )
                )

            }


        //java


        AndroidApi
            .findClassesByEnd(name)
            .forEach {
                val className = it.split(".").run { get(lastIndex) }
                if (!isLast && className == name) {
                    result.clear()
                    isLocked = true
                }


                result.add(
                    AutoCompleteBean(
                        description = "class", label = className,
                        commit = className, iconRes = R.drawable.java_class,
                        info = VarInfo().apply {
                            value = FunctionCallInfo()
                                .apply {
                                    setName(className)
                                }
                        }
                    )
                )
                if (isLocked) {
                    return result
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
                    isLocked = true
                }

                result.add(
                    AutoCompleteBean(
                        description = "package", label = className,
                        commit = className, iconRes = R.drawable.java_package,
                        info = PackageInfo()
                    )
                )
                if (isLocked) {
                    return result
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

    private fun getColorText(name: String, base: AccessibleObject): CharSequence {
        val baseString = SystemApiHelper.getAccessibleObjectText(base)
        val span = SpannableStringBuilder(name)
            .append(baseString)
        span.setSpan(
            ForegroundColorSpan(
                0xff808080.toInt()
            ),
            name.length,
            span.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return span
    }


    data class AutoCompleteBean(
        var iconRes: Int?,
        val info: BaseInfo?,
        val label: CharSequence = "",
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