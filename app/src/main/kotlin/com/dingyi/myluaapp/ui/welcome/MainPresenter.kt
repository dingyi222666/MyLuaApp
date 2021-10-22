package com.dingyi.myluaapp.ui.welcome

import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.core.startup.ZipContainer
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2021/10/21 9:49
 * @description:
 **/
class MainPresenter(
    viewModel: MainViewModel,
) : BasePresenter<MainViewModel, WelcomeActivity>(viewModel) {


    interface WelcomeView : IView {
        fun jumpToMainActivity()
    }


    fun startup() {
        view?.get()?.let { activity ->
            activity.lifecycleScope.launch {
                ZipContainer.unFileToAssets(activity) {
                    if (it == null) {
                        view?.get()?.jumpToMainActivity()
                    } else {
                        viewModel.updateMessage.postValue("unFile:$it")
                    }
                }
            }
        }
    }


}