package com.dingyi.myluaapp.base

/**
 * @author: dingyi
 * @date: 2021/10/20 20:22
 * @description:
 **/
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * @author: dingyi
 * @date: 2021/8/4 15:58
 * @description:
 **/
abstract class BasePresenter<T : ViewModel,V: BasePresenter.IView>(
    protected val viewModel: T,
) {


    protected var view : WeakReference<V>? = null

    interface IView {

    }

    fun attachView(view:V) {
        this.view=WeakReference(view)
    }

    fun detachView() {
        view?.clear()
    }

}