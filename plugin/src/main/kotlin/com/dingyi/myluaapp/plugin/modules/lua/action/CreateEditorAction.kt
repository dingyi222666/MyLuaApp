package com.dingyi.myluaapp.plugin.modules.lua.action

import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.endsWith
import com.dingyi.myluaapp.editor.language.textmate.TextMateLanguage
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.modules.lua.editor.LuaLanguage
import java.io.File

class CreateEditorAction : Action<Unit> {
    override fun callAction(argument: ActionArgument): Unit? {
        argument.getArgument<Editor>(0)?.let { editor ->

            if (editor.getFile().name.endsWith("lua","aly")) {
                //editor.setLanguage(LuaLanguage())
                val textMateLanguage = TextMateLanguage
                    .createLanguage(
                        File(Paths.assetsDir,"res/textmate/lua/lua.tmLanguage.json"),
                        File(Paths.assetsDir,"res/textmate/theme/light_vs.json"),
                        File(Paths.assetsDir,"res/textmate/lua/language-configuration.json"),
                        "lua"
                    )

                editor.setColorScheme(textMateLanguage.getColorScheme())
                editor.setLanguage(textMateLanguage)
            }
        }

        return null

    }

    override val name: String
        get() = "CreateEditorAction"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.lua.action1"
}