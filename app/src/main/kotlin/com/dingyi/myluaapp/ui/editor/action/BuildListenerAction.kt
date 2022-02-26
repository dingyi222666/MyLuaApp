package com.dingyi.myluaapp.ui.editor.action

import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.core.broadcast.LogBroadcastReceiver
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean

class BuildListenerAction : Action<Unit> {

    override fun callAction(argument: ActionArgument): Unit? {
        val viewModel = argument.getArgument<MainViewModel>(0)
            .checkNotNull()

        viewModel.progressMonitor
            .postAsyncTask {
                syncProject(viewModel)
            }
        return null
    }

    private suspend fun syncProject(viewModel: MainViewModel) {

        val status = AtomicBoolean(true)

        val callBack = { log: LogBroadcastReceiver.Log ->
            if (log.message == "BUILD END FLAG") {
                status.set(false)
            }

        }
        viewModel.logBroadcastReceiver.value?.addCallback(callBack)


        while (status.get()) {
            delay(300)
        }

        viewModel.logBroadcastReceiver.value?.removeCallback(callBack)


    }


    override val name: String
        get() = "BuildListenerAction"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action9"
}