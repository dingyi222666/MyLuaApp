package com.dingyi.myluaapp.ui.editor.presenter

import androidx.fragment.app.Fragment
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.ui.editor.MainViewModel

/**
 * @author: dingyi
 * @date: 2021/8/11 4:22
 * @description:
 **/
class FileListPresenter(
    private val fragment: Fragment,
    private val viewModel: MainViewModel
) : BasePresenter<MainViewModel>(
    viewModel,
    fragment.viewLifecycleOwner
) {





}