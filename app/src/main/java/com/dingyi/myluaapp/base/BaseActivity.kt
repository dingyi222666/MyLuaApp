package com.dingyi.myluaapp.base

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @author: dingyi
 * @date: 2021/8/4 12:36
 * @description:
 **/
abstract class BaseActivity<V : ViewBinding, T : ViewModel,R:BasePresenter<T>> : AppCompatActivity() {


    protected  var optionsMenu: Menu? = null

    protected lateinit var viewBinding: V

    protected lateinit var viewModel: T

    protected lateinit var presenter: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getViewBindingImp().root)


        viewModel = ViewModelProvider(this)[getViewModelClass()]

        presenter = getPresenterImp()

        observeViewModel()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.optionsMenu=menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun getViewBindingImp(): V {

        if (!this::viewBinding.isInitialized) {
            viewBinding = getViewBindingInstance()
        }
        return viewBinding
    }

    abstract fun getPresenterImp():R

    open fun observeViewModel() {}

    abstract fun getViewModelClass(): Class<T>

    abstract fun getViewBindingInstance(): V


}