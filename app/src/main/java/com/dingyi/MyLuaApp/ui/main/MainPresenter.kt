package com.dingyi.MyLuaApp.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BasePresenter
import com.dingyi.MyLuaApp.common.kts.getStringArray
import com.dingyi.MyLuaApp.network.service.MainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

/**
 * @author: dingyi
 * @date: 2021/8/4 16:00
 * @description:
 **/
class MainPresenter(
    private val viewModel: MainViewModel,
    private val activity: AppCompatActivity
) : BasePresenter<MainViewModel>(viewModel, activity) {


    fun requestPoetry() {
        activity.lifecycleScope.launchWhenResumed {
            MainService.getPoetry(activity)
                .flowOn(Dispatchers.IO)
                .catch {
                    val array = activity.getStringArray(R.array.main_poetry_array)

                    viewModel.title.postValue(array.random())
                }
                .collect {
                    viewModel.title.postValue(it)
                }
        }
    }

}