package com.dingyi.myluaapp.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dingyi.myluaapp.core.startup.ZipContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

/**
 * @author: dingyi
 * @date: 2021/10/21 9:48
 * @description:
 **/
class MainViewModel : ViewModel() {

    @ExperimentalCoroutinesApi
    val updateMessage by lazy(LazyThreadSafetyMode.NONE) {
        liveData {
            delay(200)
            ZipContainer.unFileToAssets()
                .collect {
                    emit(it)
                }
        }
    }

    val jumpFlag = liveData {
        delay(1000)
        emit(ZipContainer.checkVersion())
    }


}