package com.dingyi.myluaapp.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/8/4 15:58
 * @description:
 **/
abstract class BasePresenter<T : ViewModel>(
    viewModel: ViewModel,
    activity: LifecycleOwner
)