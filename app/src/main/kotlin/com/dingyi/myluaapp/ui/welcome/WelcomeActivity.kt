package com.dingyi.myluaapp.ui.welcome

import android.os.Bundle
import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.ActivityWelcomeBinding

/**
 * @author: dingyi
 * @date: 2021/10/21 9:47
 * @description:
 **/
class WelcomeActivity :
    BaseActivity<ActivityWelcomeBinding, MainViewModel, MainPresenter>(),
    MainPresenter.WelcomeView {

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun getPresenterImp(): MainPresenter {
        return MainPresenter(viewModel).apply {
            attachView(this@WelcomeActivity)
        }
    }

    override fun observeViewModel() {
        viewModel.updateMessage.observe(this) {
            viewBinding.title.text = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.startup()
    }

    override fun jumpToMainActivity() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}