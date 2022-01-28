package com.dingyi.myluaapp.core.broadcast

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

class LogBroadcastReceiver(
    private var context: AppCompatActivity?
) : BroadcastReceiver(), LifecycleEventObserver {

    private val callbackList = mutableListOf<(Intent) -> Unit>()


    init {
        context?.lifecycle?.addObserver(this)
        context?.registerReceiver(this, IntentFilter(javaClass.name))
    }

    fun addCallback(block: (Intent) -> Unit) {
        callbackList.add(block)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        callbackList.forEach {
            intent?.let { intent -> it.invoke(intent) }
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


}