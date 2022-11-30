package com.dingyi.myluaapp.openapi.actions


import com.dingyi.myluaapp.openapi.editor.Caret
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.language.Language
import com.dingyi.myluaapp.openapi.vfs.VirtualFile



/** Internal API. Do not use directly.  */

object InjectedDataKeys {
    private const val ourInjectedPrefix = "\$injected$."
    private val ourInjectableIds: MutableMap<String, String> = HashMap()
    val EDITOR: DataKey<Editor> = injectedKey(CommonDataKeys.EDITOR)
    val CARET: DataKey<Caret> = injectedKey(CommonDataKeys.CARET)
    val VIRTUAL_FILE: DataKey<VirtualFile> = injectedKey(CommonDataKeys.VIRTUAL_FILE)

    val LANGUAGE: DataKey<Language> = injectedKey(CommonDataKeys.LANGUAGE)


    fun injectedId(dataId: String): String? {
        return ourInjectableIds[dataId]
    }


    fun uninjectedId(dataId: String): String? {
        return if (dataId.startsWith(ourInjectedPrefix)) dataId.substring(ourInjectedPrefix.length) else null
    }

    private fun <T> injectedKey(key: DataKey<T>): DataKey<T> {
        val injectedId = ourInjectedPrefix + key.name
        ourInjectableIds[key.name] = injectedId
        return DataKey.create(injectedId)
    }
}