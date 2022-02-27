package com.dingyi.myluaapp.core.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class ProgressMonitor(

) {

    private var progressRunningState = MutableLiveData(true)

    private val count = AtomicInteger(0)
    private suspend fun changeProgressState(boolean: Boolean) = withContext(Dispatchers.Main) {
        progressRunningState.value = boolean
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var isRunning = false

    private val afterTask = mutableListOf<suspend () -> Unit>()

    fun getProgressState(): LiveData<Boolean> = progressRunningState

    fun postAsyncTask(block: suspend () -> Unit) {
        scope.launch {
            count.getAndIncrement()
            changeProgressState(true)
            block.invoke()
            count.getAndDecrement()

            if (!isRunning && count.get() == 0) {
                runAfterTask()
                isRunning = true
            }


        }
    }

    fun close() {
        scope.cancel()
        afterTask.clear()
    }

    private fun runAfterTask() {
        scope.launch {
            while (count.get() > 0) {
                delay(50)
            }
            for (it in afterTask) {
                it.invoke()
                afterTask.remove(it)
            }
            changeProgressState(false)
            isRunning = false
        }
    }

    fun runAfterTaskRunning(block: suspend () -> Unit) {
        afterTask.add(block)
        if (count.get() == 0 && !isRunning) {
            runAfterTask()
        }
    }


}