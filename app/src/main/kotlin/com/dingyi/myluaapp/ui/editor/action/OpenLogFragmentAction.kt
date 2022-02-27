package com.dingyi.myluaapp.ui.editor.action

import android.os.SystemClock
import androidx.core.view.GravityCompat
import androidx.core.view.postDelayed
import com.dingyi.myluaapp.databinding.ActivityEditorBinding
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument

class OpenLogFragmentAction : Action<Unit> {
    override fun callAction(argument: ActionArgument): Unit? {
        val viewBinding = argument
            .getArgument<ActivityEditorBinding>(0)


        viewBinding?.let {

            it.drawer.post {
                it.drawer.openDrawer(GravityCompat.START)
                it.drawerPage.postDelayed(SystemClock.uptimeMillis()+500) {
                    it.drawerPage.setCurrentItem(1, true)
                }
            }
        }

        return null
    }

    override val name: String
        get() = "OpenLogFragment"
    override val id: String
        get() = "com.dingyi.myluapp.plugin.default.action4"

}