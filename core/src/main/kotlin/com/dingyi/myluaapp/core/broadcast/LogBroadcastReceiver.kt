package com.dingyi.myluaapp.core.broadcast

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

class LogBroadcastReceiver(
    private val lifecycleOwner: Lifecycle,
    private var context: ComponentActivity?
) : BroadcastReceiver(), LifecycleEventObserver {

    private val callbackList = mutableListOf<(Log) -> Unit>()

    private val logList = mutableListOf<Log>()


    private var isCompleted = false

    data class Log(
        val level: String,
        val message: String,
        val extra: String
    )

    init {
        lifecycleOwner.addObserver(this)
    }

    fun addCallback(block: (Log) -> Unit) {
        callbackList.add(block)
        logList.forEach {
            block(it)
        }
    }

    private fun clear() {
        logList.clear()
    }

    fun isCompleted(): Boolean {
        return isCompleted
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        getLog(intent)?.let { log ->
            if (isCompleted) {
                isCompleted = false
            }
            logList.add(log)
            if (log.message == "BUILD COMPLETED FLAG") {
                isCompleted = true
                clear()
            }
            callbackList.forEach {
                it(log)
            }

        }
    }

    private fun getLog(intent: Intent?): Log? {
        return intent?.let {
            Log(
                it.getStringExtra("tag").toString(),
                it.getStringExtra("message").toString(),
                it.getStringExtra("extra").toString()
            )
        }
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                context?.unregisterReceiver(this)
            }
            Lifecycle.Event.ON_RESUME -> {
                context?.registerReceiver(this, IntentFilter(javaClass.name))
            }
            Lifecycle.Event.ON_DESTROY -> {
                source.lifecycle.removeObserver(this)
                context = null
            }
            else -> {}
        }
    }

    fun removeCallback(callback: (Log) -> Unit) {
        callbackList.remove(callback)
    }


}