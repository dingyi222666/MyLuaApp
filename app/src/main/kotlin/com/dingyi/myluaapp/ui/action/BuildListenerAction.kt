package com.dingyi.myluaapp.ui.action

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.modules.default.action.CommonActionKey
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.delay

class BuildListenerAction : Action<Unit> {

    override fun callAction(argument: ActionArgument): Unit? {
        val viewModel = argument.getArgument<MainViewModel>(0)
            .checkNotNull()

        viewModel.progressMonitor
            .postAsyncTask {
                syncProject(viewModel)
            }

        argument
            .getPluginContext()
            .getActionService()
            .callAction<Unit>(
                argument
                    .getPluginContext()
                    .getActionService()
                    .createActionArgument(), CommonActionKey.OPEN_LOG_FRAGMENT
            )

        return null
    }

    private suspend fun syncProject(viewModel: MainViewModel) {
        
        while (true) {
            delay(1000)
            if (viewModel.logBroadcastReceiver.value?.isCompleted() == true) {
                break
            }
        }

    }


    override val name: String
        get() = "BuildListenerAction"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action9"
}