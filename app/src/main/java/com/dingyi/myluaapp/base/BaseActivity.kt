package com.dingyi.myluaapp.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showSnackBar

/**
 * @author: dingyi
 * @date: 2021/8/4 12:36
 * @description:
 **/
abstract class BaseActivity<V : ViewBinding, T : ViewModel, P : BasePresenter<T>> :
    AppCompatActivity() {


    protected var lastBackTime = System.currentTimeMillis()

    protected var optionsMenu: Menu? = null

    protected lateinit var viewBinding: V

    protected lateinit var viewModel: T

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getViewBindingImp().root)


        viewModel = ViewModelProvider(this)[getViewModelClass()]

        presenter = getPresenterImp()

        observeViewModel()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.optionsMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun getViewBindingImp(): V {

        if (!this::viewBinding.isInitialized) {
            viewBinding = getViewBindingInstance()
        }
        return viewBinding
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (System.currentTimeMillis()-lastBackTime>2000) {
                    R.string.toast_exit_app
                        .getString()
                        .showSnackBar(viewBinding.root)
                    lastBackTime=System.currentTimeMillis()
                    true
                } else {
                    super.onKeyUp(keyCode, event)
                }
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    abstract fun getPresenterImp(): P

    open fun observeViewModel() {}

    abstract fun getViewModelClass(): Class<T>

    abstract fun getViewBindingInstance(): V


}