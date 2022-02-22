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


    data class Log(
        val level: String,
        val message: String
    )

    init {
        lifecycleOwner.addObserver(this)
        context?.registerReceiver(this, IntentFilter(javaClass.name))
    }

    fun addCallback(block: (Log) -> Unit) {
        callbackList.add(block)
        logList.forEach {
            block(it)
        }
    }

    fun clear() {
        logList.clear()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        getLog(intent)?.let { log ->
            callbackList.forEach {
                it(log)
            }
            logList.add(log)
        }
    }

    private fun getLog(intent: Intent?): Log? {
        return intent?.let {
            Log(it.getStringExtra("tag").toString(), it.getStringExtra("message").toString())
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