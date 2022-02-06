package com.dingyi.myluaapp.build.modules.android.symbol

import java.io.File

class SymbolLoader(
    private val loadFile:File
) {



    fun load():SymbolLoader {
        return this
    }


    companion object {
        fun load(file: File): SymbolLoader {
            return SymbolLoader(file).load()
        }
    }
}