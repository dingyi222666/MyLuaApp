package com.dingyi.myluaapp.plugin.runtime.ui

import android.widget.Toast
import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.plugin.api.ui.UiService
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule

class UiService(private val pluginModule: PluginModule) : UiService {
    override fun showToast(string: String, duration: Int) {
        Toast.makeText(MainApplication.instance, string, duration)
            .show()
    }
}