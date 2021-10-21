package com.dingyi.myluaapp.ui.welcome

import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.base.BasePresenter

/**
 * @author: dingyi
 * @date: 2021/10/21 9:49
 * @description:
 **/
class MainPresenter<T>(
    private val viewModel: MainViewModel,
    private val welcomeView: WelcomeView
) : BasePresenter<MainViewModel>(viewModel,welcomeView as AppCompatActivity) {


    interface WelcomeView : IView {
        fun jumpToMainActivity()
    }

}