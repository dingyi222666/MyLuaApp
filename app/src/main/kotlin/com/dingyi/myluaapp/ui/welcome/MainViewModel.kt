package com.dingyi.myluaapp.ui.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author: dingyi
 * @date: 2021/10/21 9:48
 * @description:
 **/
class MainViewModel: ViewModel() {

    val updateMessage = MutableLiveData<String>()
}