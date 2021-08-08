package com.dingyi.MyLuaApp.ui.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.MyLuaApp.bean.ProjectInfo

/**
 * @author: dingyi
 * @date: 2021/8/8 15:25
 * @description:
 **/
class MainViewModel: ViewModel() {

    val projectInfo=MutableLiveData<ProjectInfo>()
}