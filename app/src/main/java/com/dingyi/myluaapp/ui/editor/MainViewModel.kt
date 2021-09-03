package com.dingyi.myluaapp.ui.editor


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.editor.struct.ColumnNavigationItem
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.database.bean.ProjectConfig


/**
 * @author: dingyi
 * @date: 2021/8/8 15:25
 * @description:
 **/
class MainViewModel : ViewModel() {

    val projectInfo = MutableLiveData<ProjectInfo>()
    val projectConfig = MutableLiveData<ProjectConfig>()
    val editPagerTabText = mutableListOf<MutableLiveData<String>?>()

    val fileList = MutableLiveData<List<String>>()
    val fileListTitle = MutableLiveData<String>()
    val realPosition = MutableLiveData(0)

    val navigationList= MutableLiveData<List<ColumnNavigationItem>>()
}