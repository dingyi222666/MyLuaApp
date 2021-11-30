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
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.iconColor
import com.dingyi.myluaapp.common.kts.showSnackBar
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

    protected lateinit var viewBinding: V

    protected lateinit var viewModel: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getViewBindingImp().root)


        viewModel = ViewModelProvider(this)[getViewModelClass()]

        observeViewModel()

    }



    override fun attachBaseContext(base: Context) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.optionsMenu = menu
        //call iconColor when call support method
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
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
                if (System.currentTimeMillis() - lastBackTime > 2000) {
                    R.string.toast_exit_app
                        .getString()
                        .showSnackBar(viewBinding.root)
                    lastBackTime = System.currentTimeMillis()
                    true
                } else {
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