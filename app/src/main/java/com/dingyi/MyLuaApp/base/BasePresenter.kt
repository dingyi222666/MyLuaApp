package com.dingyi.MyLuaApp.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

/**
 * @author: dingyi
 * @date: 2021/8/4 15:58
 * @description:
 **/
abstract class BasePresenter<T : ViewModel>(
   viewModel: ViewModel,
   activity: LifecycleOwner
) {

}