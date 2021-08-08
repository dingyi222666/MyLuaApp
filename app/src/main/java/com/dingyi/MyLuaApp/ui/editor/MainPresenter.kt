package com.dingyi.MyLuaApp.ui.editor

import androidx.appcompat.app.AppCompatActivity
import com.dingyi.MyLuaApp.base.BasePresenter

/**
 * @author: dingyi
 * @date: 2021/8/8 15:24
 * @description:
 **/
class MainPresenter(
    private val viewModel: MainViewModel,
    private val activity: AppCompatActivity
) : BasePresenter<MainViewModel>(viewModel, activity) {

}