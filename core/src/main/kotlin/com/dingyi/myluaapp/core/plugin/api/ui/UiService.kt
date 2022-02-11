package com.dingyi.myluaapp.core.plugin.api.ui

interface UiService {

    fun <T> getUiView(key:UiKey<T>):T

}