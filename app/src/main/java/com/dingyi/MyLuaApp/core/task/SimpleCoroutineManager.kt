package com.dingyi.MyLuaApp.core.task

import com.dingyi.MyLuaApp.base.BaseActivity
import kotlinx.coroutines.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class SimpleCoroutineManager(mActivity:BaseActivity<*>) {
    private val job= Job()
    private val coroutineScope= CoroutineScope(Dispatchers.Main + job)

    fun postTask(block:suspend CoroutineScope.()->Unit) {
        coroutineScope.launch(coroutineScope.coroutineContext,CoroutineStart.DEFAULT,block)
    }

    fun cancelAllTask() {
        job.cancel()
    }


}