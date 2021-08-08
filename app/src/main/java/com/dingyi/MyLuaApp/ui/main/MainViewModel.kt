package com.dingyi.MyLuaApp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.MyLuaApp.bean.ProjectInfo

/**
 * @author: dingyi
 * @date: 2021/8/4 15:08
 * @description:
 **/
class MainViewModel : ViewModel() {

    val title = MutableLiveData<String>()


    val mainProjectList = MutableLiveData<List<ProjectInfo>>()

}