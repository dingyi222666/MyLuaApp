package com.dingyi.myluaapp.base

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.getAttributeColor
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.iconColor
import com.dingyi.myluaapp.common.ktx.showSnackBar
import com.dingyi.myluaapp.common.theme.ThemeManager
import com.hjq.language.MultiLanguages

/**
 * @author: dingyi
 * @date: 2021/10/20 20:21
 * @description:
 **/
abstract class BaseActivity<V : ViewBinding, T : ViewModel> :
    AppCompatActivity() {


    protected var lastBackTime = System.currentTimeMillis()

    protected var optionsMenu: Menu? = null

    lateinit var viewBinding: V

    lateinit var viewModel: T

    val themeManager = ThemeManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeManager.apply(this)

        setContentView(getViewBindingImp().root)


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory.instance)[getViewModelClass()]


        observeViewModel()

        viewModelStore
    }


    override fun attachBaseContext(base: Context) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.optionsMenu = menu
        //call iconColor when call support method
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return true
    }

    private fun getViewBindingImp(): V {
        if (!this::viewBinding.isInitialized) {
            viewBinding = getViewBindingInstance()
        }
        return viewBinding
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                if (System.currentTimeMillis() - lastBackTime > 2000) {
                    R.string.toast_exit_app
                        .getString()
                        .showSnackBar(viewBinding.root)
                    lastBackTime = System.currentTimeMillis()
                    true
                } else {
                    finishAfterTransition()
                    super.onKeyUp(keyCode, event)
                }
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }


    open fun observeViewModel() {}

    abstract fun getViewModelClass(): Class<T>

    abstract fun getViewBindingInstance(): V


}