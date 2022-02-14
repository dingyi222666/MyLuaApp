package com.dingyi.myluaapp.plugin.api.editor.language

interface Language {

    fun getName():String

    fun getHighlightProvider(): HighlightProvider

}