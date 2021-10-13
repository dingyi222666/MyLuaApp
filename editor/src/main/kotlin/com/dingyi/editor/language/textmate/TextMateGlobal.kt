package com.dingyi.editor.language.textmate

import android.util.ArrayMap
import com.dingyi.editor.language.textmate.bean.Settings
import com.dingyi.editor.language.textmate.theme.ITheme
import com.dingyi.editor.language.textmate.theme.VSCodeTheme
import com.google.gson.Gson
import org.eclipse.tm4e.core.registry.Registry
import java.io.FileInputStream
import java.io.FileReader
import kotlin.concurrent.thread

/**
 * @author: dingyi
 * @date: 2021/10/10 17:20
 * @description:
 **/
object TextMateGlobal {


    private val themes = ArrayMap<String, ITheme>()

    val settings: Settings = Gson()
        .fromJson(FileReader("/data/data/com.dingyi.MyLuaApp/files/res/textmate/settings.json"), Settings::class.java)

    init {
        //先加载主题
        thread {
            loadTheme("light") {
                VSCodeTheme("light.json") {
                    FileInputStream("/data/data/com.dingyi.MyLuaApp/files/res/textmate/theme/light.json")
                }
            }.theme.init()
        }
    }

    fun <T : ITheme> loadTheme(
        name: String,
        block: () -> T
    ): TextMateScheme {
        val theme = if (themes.containsKey(name)) {
            themes.getValue(name) as T
        } else {
            block().apply {
                themes[name] = this
            }
        }
        return TextMateScheme {
            theme
        }
    }

}