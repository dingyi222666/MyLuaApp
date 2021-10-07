package com.dingyi.myluaapp.network.service

import androidx.lifecycle.LifecycleOwner
import com.dingyi.myluaapp.bean.Poetry
import com.dingyi.myluaapp.network.api.PoetryApi
import com.hjq.http.EasyHttp
import com.hjq.http.model.ResponseClass
import kotlinx.coroutines.flow.flow

/**
 * @author: dingyi
 * @date: 2021/8/4 15:49
 * @description:
 **/
object MainService {

    fun getPoetry(lifecycle: LifecycleOwner) = flow {
        EasyHttp.get(lifecycle)
            .api(PoetryApi())
            .execute(object : ResponseClass<Poetry>() {}).apply {
                emit(this.content)
            }
    }

}