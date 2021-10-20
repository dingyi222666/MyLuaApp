package com.dingyi.myluaapp.base

/**
 * @author: dingyi
 * @date: 2021/10/20 20:22
 * @description:
 **/
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


    interface IView {

    }

}