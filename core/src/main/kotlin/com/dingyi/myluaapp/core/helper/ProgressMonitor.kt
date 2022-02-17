package com.dingyi.myluaapp.core.helper

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ProgressMonitor(
    private val scope: CoroutineScope,
) {

    private var progressRunningState = MutableLiveData(false)

    private var allJob = mutableListOf<Job>()

    fun changeProgressState(boolean: Boolean) {
        progressRunningState.value = boolean
    }

    fun getProgressState(): LiveData<Boolean> = progressRunningState

    fun postAsyncTask(block: suspend (ProgressMonitor) -> Unit) {
        allJob.add(
            scope.launch {
                block(this@ProgressMonitor)
            }
        )
    }

    fun runAfterTaskRunning(block: () -> Unit) {
        scope.launch {

            withContext(Dispatchers.IO) {
                while (true) {
                    if (allJob.isEmpty()) {
                        delay(10)
                    } else {
                        break
                    }
                }
            }

            withContext(Dispatchers.IO) {
                while (true) {
                    allJob = allJob.mapNotNull { if (it.isCompleted) null else it }
                        .toMutableList()

                    if (allJob.isEmpty()) {
                        break
                    } else {
                        delay(100)
                    }

                }

            }
            block()
        }
    }


}