package com.dingyi.myluaapp.plugin.modules.default.action

import com.dingyi.myluaapp.plugin.api.action.ActionKey

data class ActionKey(
    private val actionId: Int,
    private val repeat: Boolean
) : ActionKey {
    override fun getId(): Int {
        return actionId
    }

    override fun isRepeat(): Boolean {
        return repeat
    }
}
