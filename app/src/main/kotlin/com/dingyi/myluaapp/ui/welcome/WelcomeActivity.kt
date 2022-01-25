package com.dingyi.myluaapp.ui.welcome

import android.os.Bundle
import android.util.Log
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.databinding.ActivityWelcomeBinding
import com.dingyi.myluaapp.ui.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @author: dingyi
 * @date: 2021/10/21 9:47
 * @description:
 **/
class WelcomeActivity :
    BaseActivity<ActivityWelcomeBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }


    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.jumpFlag.observe(this) {
            if (it) {
                jumpToMainActivity()
            } else {
                observeUnZip()
            }
        }

    }


    private fun observeUnZip() {
        viewModel.updateMessage.observe(this) {
            if (it == null) {
                jumpToMainActivity()
            } else {
                viewBinding.title.text = "unFile:$it"
            }
        }
    }

    private fun jumpToMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}