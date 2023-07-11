package com.dingyi.MyLuaApp.ui.page.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _isExpandedMenu = MutableLiveData<Boolean>()

    val isExpandedMenu: LiveData<Boolean> = _isExpandedMenu

    fun toggleMenu() {
        _isExpandedMenu.value = _isExpandedMenu.value?.not() ?: true
    }
}