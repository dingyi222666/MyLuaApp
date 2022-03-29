package com.dingyi.myluaapp.plugin.modules.android.action

import android.util.Log
import com.androlua.LuaServer
import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.endsWith
import com.dingyi.myluaapp.editor.language.textmate.TextMateLanguage
import com.dingyi.myluaapp.editor.lsp.client.language.LSPLanguage
import com.dingyi.myluaapp.editor.lsp.connect.InternalConnectionProvider
import com.dingyi.myluaapp.editor.lsp.connect.SocketStreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.server.definition.InternalLanguageServerDefinition
import com.dingyi.myluaapp.editor.lsp.server.definition.LanguageServerDefinition
import com.dingyi.myluaapp.editor.lsp.server.definition.SocketLanguageServerDefinition
import com.dingyi.myluaapp.editor.lsp.server.lua.LuaLanguageServer
import com.dingyi.myluaapp.editor.lsp.server.lua.Main
import com.dingyi.myluaapp.editor.lsp.service.LanguageServiceAccessor
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.modules.lua.editor.LuaLanguage
import java.io.File

class CreateJavaEditorAction : Action<Unit> {
    override fun callAction(argument: ActionArgument): Unit? {
        argument.getArgument<Editor>(0)?.let { editor ->


            if (editor.getFile().extension.contains("java")) {

                val definition = argument.getArgument<LanguageServerDefinition>(1).checkNotNull()
                LanguageServiceAccessor.getInitializedLanguageServer(
                    editor,
                    definition,
                    null
                ).handle { it, _ ->

                    if (it == null) {
                        throw RuntimeException("language server is null")
                    }


                    editor.setLanguage(
                        LSPLanguage(
                            wrapper = LanguageServiceAccessor.getLSWrapper(
                                editor.getProject(),
                                definition,
                            ),
                            server = it,
                            client = definition.getLanguageClient().checkNotNull(),
                            editor = editor,
                            wrapperLanguage = editor.getLanguage()
                        )
                    )
                    Log.e("CreateEditorAction", "create java language server success")

                }.exceptionally {
                    Log.e("CreateEditorAction", "create java language server failed",it)
                    null
                }

            }
        }

        return null

    }


    override val name: String
        get() = "CreateEditorAction"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.java.action1"
}