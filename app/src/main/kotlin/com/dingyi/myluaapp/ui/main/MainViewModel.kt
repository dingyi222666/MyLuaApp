package com.dingyi.myluaapp.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.bean.Poetry
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.getStringArray
import com.dingyi.myluaapp.common.ktx.showSnackBar

import com.dingyi.myluaapp.databinding.ActivityMainBinding
import com.dingyi.myluaapp.network.Apis
import com.drake.net.Get
import com.drake.net.utils.scopeNetLife
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/10/22 23:37
 * @description:
 **/
class MainViewModel : ViewModel() {




    val poetry = MutableLiveData<String>()

    private fun showErrorMessage(binding: ActivityMainBinding) {
        R.string.main_project_list_config_error.getString()
            .showSnackBar(binding.root)
    }

    fun refreshPoetry(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.scopeNetLife {
            runCatching {
                Get<Poetry>(Apis.poetryApi).await()

            }.getOrNull()?.content ?: R.array.main_poetry_array.getStringArray().run {
                get(IntRange(0, lastIndex).random())
            }.let {
                poetry.postValue(it)
            }
        }
    }

    suspend fun refreshProjectList(binding: ActivityMainBinding) = withContext(Dispatchers.Main) {
        runCatching {

        }.onFailure {
            System.err.println(it)
            showErrorMessage(binding)
        }

    }


}